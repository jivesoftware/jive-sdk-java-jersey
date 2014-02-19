/*
 *
 *  * Copyright 2013 Jive Software
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 */

package com.jivesoftware.sdk.client;

import com.google.common.collect.Maps;
import com.jivesoftware.sdk.api.entity.TileInstance;
import com.jivesoftware.sdk.api.entity.TileInstanceProvider;
import com.jivesoftware.sdk.client.filter.DebugClientResponseFilter;
import com.jivesoftware.sdk.client.oauth.JiveOAuthClient;
import com.jivesoftware.sdk.utils.JiveSDKUtils;
import org.codehaus.jackson.annotate.JsonProperty;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 *
 * NOTE:  HAD TO MOVE AWAY FROM GENERICS SO I CAN DO INJECTIONS
 * Created by rrutan on 2/3/14.
 */

@Singleton
public class JiveTileClient extends BaseJiveClient {
    private static final Logger log = LoggerFactory.getLogger(JiveTileClient.class);

    @Context
    Application application;

    @Inject
    private TileInstanceProvider tileInstanceProvider;

    @Inject
    private JiveOAuthClient jiveOAuthClient;

    private static JiveTileClient instance = null;

    public JiveTileClient() {
        if (log.isTraceEnabled()) { log.trace("constructor called..."); }
    } // end constructor

//    /***************************************************************/
//    private class TileDataPusher implements Runnable {
//        private TileInstance tileInstance;
//        private Object data;
//
//        TileDataPusher(TileInstance tileInstance, Object data) {
//            this.tileInstance = tileInstance;
//            this.data = data;
//        }  // end constructor
//
//        @Override
//        public void run() {
//
//        }
//    } // end inner-class
//    /***************************************************************/

    public void pushData(TileInstance tileInstance, Object data) throws JiveClientException {
        if (log.isTraceEnabled()) { log.trace("pushData called..."); }
        if (log.isDebugEnabled()) { log.debug("pushData => ["+tileInstance.getJivePushUrl()+"]"); }

        initAccessTokens(tileInstance);

        Client client = buildClient();
        WebTarget target = client.target(tileInstance.getJivePushUrl());

        DataBlock dataBlock = new DataBlock(data);

        AsyncInvoker asyncInvoker = target.request(MediaType.APPLICATION_JSON_TYPE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tileInstance.getCredentials().getAccessToken()).async();

        Future<Response> responseFuture = asyncInvoker.put(Entity.entity(dataBlock, MediaType.APPLICATION_JSON_TYPE));

        Response response = null;
        try {
            response = responseFuture.get();
            if (response.getStatus() == 204) {
                if (log.isInfoEnabled()) { log.info("Successful Push ["+tileInstance.getJivePushUrl()+"]"); }
            } // end if
        } catch (BadRequestException bre) {
            //TODO:  CHECK FOR REFRESH OAUTH ... REFRESH...AND RE-EXECUTE
            log.error("Error Pushing Data to Tile [" + tileInstance.getJivePushUrl() + "]", bre);
            throw JiveClientException.buildException("Error Pushing Data to Tile [" + tileInstance.getJivePushUrl() + "]",bre,tileInstance,data,data.getClass());
        } catch (InterruptedException ie) {
            log.error("Error Pushing Data to Tile [" + tileInstance.getJivePushUrl() + "]", ie);
            throw JiveClientException.buildException("Error Pushing Data to Tile [" + tileInstance.getJivePushUrl() + "]",ie,tileInstance,data,data.getClass());
        } catch (ExecutionException ee) {
            log.error("Error Pushing Data to Tile [" + tileInstance.getJivePushUrl() + "]", ee);
            throw JiveClientException.buildException("Error Pushing Data to Tile [" + tileInstance.getJivePushUrl() + "]",ee,tileInstance,data,data.getClass());
        } finally {
            if (response != null ) {
                response.close();
            } // end if
            response = null;
            responseFuture = null;
        } // end try/catch
        asyncInvoker = null;
        target = null;
        dataBlock = null;
        client.close();
        client = null;

    } // end pushData

    public Object fetchData(TileInstance tileInstance, Class clazz) throws JiveClientException {

        initAccessTokens(tileInstance);

        Client client = buildClient();

        //TODO: CONFIRM THE URL NEEDED TO FETCH DATA, PRETTY SURE THIS ISNT jivePushURL
        WebTarget target = client.target(tileInstance.getJivePushUrl());

        AsyncInvoker asyncInvoker =  target.request(MediaType.APPLICATION_JSON_TYPE)
                                           .header(HttpHeaders.AUTHORIZATION, "Bearer " + tileInstance.getCredentials().getAccessToken())
                                           .async();

        Future<Response> responseFuture = asyncInvoker.get();

        Response response = null;

        try {
            response = responseFuture.get();
            //TODO: CHECK THAT THIS IS THE CORRECT RESPONSE CODE
            if (response.getStatus() == 204) {
                //TODO: CONFIRM THE URL NEEDED TO FETCH DATA, PRETTY SURE THIS ISNT jivePushURL
                if (log.isInfoEnabled()) { log.info("Successful Fetch ["+tileInstance.getJivePushUrl()+"]"); }
                //TODO: EXPORT TO UTILITY CLASS
                //TODO: NEED TO USE JACKSON AND CONVERT ENTITY TO ITS ORIGINAL OBJECT TYPE?
                return response.getEntity();
            } // end if
        } catch (BadRequestException bre) {
            log.error("Error Fetching Data From Tile [" + tileInstance.getJivePushUrl() + "]", bre);
            throw JiveClientException.buildException("Error Fetching Data to Tile [" + tileInstance.getJivePushUrl() + "]",bre,tileInstance,null,clazz);
        } catch (InterruptedException ie) {
            log.error("Error Fetching Data From Tile [" + tileInstance.getJivePushUrl() + "]", ie);
            throw JiveClientException.buildException("Error Fetching Data to Tile [" + tileInstance.getJivePushUrl() + "]",ie,tileInstance,null,clazz);
        } catch (ExecutionException ee) {
            log.error("Error Fetching Data From Tile [" + tileInstance.getJivePushUrl() + "]", ee);
            throw JiveClientException.buildException("Error Fetching Data to Tile [" + tileInstance.getJivePushUrl() + "]",ee,tileInstance,null,clazz);
        } // end try/catch
        asyncInvoker = null;
        target = null;
        client.close();
        client = null;


        return null;
    } // end fetchData

    private void initAccessTokens(TileInstance tileInstance) {
        if (log.isTraceEnabled()) { log.trace("Initializing OAuth Access Tokens"); }
        if (!JiveSDKUtils.isAllExist(tileInstance.getCredentials().getRefreshToken())) {
            if (log.isDebugEnabled()) { log.debug("Refresh OAuth Token Missing ..."); }
            tileInstance.setCredentials(jiveOAuthClient.getTileInstanceAccessToken(tileInstance));

            //TODO: SHOULD I SAVE EXPIRES TIME / CALCULATE AND REFRESH PROACTIVELY?

            try {
                tileInstanceProvider.update(tileInstance);
                if (log.isDebugEnabled()) { log.debug("Successfully Updated Tile Instance!"); }
            } catch (TileInstanceProvider.TileInstanceProviderException tipe) {
                log.error("Unable to save credential information",tipe);
            } // end try/catch

        } // end if

    } // end initAccessTokens

} // end JiveTileClient