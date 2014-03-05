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
import com.jivesoftware.sdk.JiveAddOnApplication;
import com.jivesoftware.sdk.api.entity.TileInstance;
import com.jivesoftware.sdk.api.entity.TileInstanceProvider;
import com.jivesoftware.sdk.api.tile.data.ActivityEntry;
import com.jivesoftware.sdk.api.tile.data.ActivityObject;
import com.jivesoftware.sdk.api.tile.data.ActivityPushTile;
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
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 *
 * NOTE:  HAD TO MOVE AWAY FROM GENERICS SO I CAN DO INJECTIONS
 * Created by rrutan on 2/3/14.
 */

@Singleton
public class JiveTileClient {
    private static final Logger log = LoggerFactory.getLogger(JiveTileClient.class);

    @Context
    Application application;

    @Inject
    private JiveAddOnApplication jiveAddOnApplication;

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
            }
            else if (response.getStatus() == Response.Status.GONE.getStatusCode()) {
                log.info("Received 410 from data push request, assuming tile deleted in Jive and removing it, instance url: " + tileInstance.getJivePushUrl());
                try {
                    jiveAddOnApplication.getTileInstanceProvider().remove(tileInstance);
                }
                catch (TileInstanceProvider.TileInstanceProviderException e) {
                    log.error("Failed to remove tile instance", e);
                }
            }
        } catch (ForbiddenException fe) {
            log.info("403 response when pushing data to tile [" + tileInstance.getJivePushUrl() + "]");
            refreshAccessTokens(tileInstance);
            pushData(tileInstance, data); // recursive call to retry
            return;
        } catch (InterruptedException ie) {
            log.error("Error Pushing Data to Tile [" + tileInstance.getJivePushUrl() + "]", ie);
            throw JiveClientException.buildException("Error Pushing Data to Tile [" + tileInstance.getJivePushUrl() + "]",ie,tileInstance,data,data.getClass());
        } catch (ExecutionException ee) {
            log.error("Error Pushing Data to Tile [" + tileInstance.getJivePushUrl() + "]", ee);
            throw JiveClientException.buildException("Error Pushing Data to Tile [" + tileInstance.getJivePushUrl() + "]",ee,tileInstance,data,data.getClass());
        } finally {
            if (response != null ) {
                response.close();
            }
            response = null;
            responseFuture = null;
        }
        asyncInvoker = null;
        target = null;
        dataBlock = null;
        client.close();
        client = null;
    }

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

    public ActivityEntry pushActivity(TileInstance tileInstance, Object data) throws JiveClientException {
        if (log.isTraceEnabled()) { log.trace("pushActivity called..."); }
        if (log.isDebugEnabled()) { log.debug("pushActivity => ["+tileInstance.getJivePushUrl()+"]"); }

        initAccessTokens(tileInstance);
        Client client = buildClient();
        WebTarget target = client.target(tileInstance.getJivePushUrl());

        AsyncInvoker asyncInvoker = target.request(MediaType.APPLICATION_JSON_TYPE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tileInstance.getCredentials().getAccessToken()).async();

        // Note that this needs to be a POST and we are not using the same DataBlock class as data push
        Future<Response> responseFuture = asyncInvoker.post(Entity.entity(data, MediaType.APPLICATION_JSON_TYPE));

        Response response = null;
        ActivityEntry created;
        try {
            response = responseFuture.get();
            if (response.getStatus() == 201) {
                if (log.isInfoEnabled()) { log.info("Successful Activity Push ["+tileInstance.getJivePushUrl()+"]"); }
            } // end if
            created = response.readEntity(ActivityEntry.class);
        } catch (BadRequestException bre) {
            //TODO:  CHECK FOR REFRESH OAUTH ... REFRESH...AND RE-EXECUTE
            log.error("Error Pushing Activity to Tile [" + tileInstance.getJivePushUrl() + "]", bre);
            throw JiveClientException.buildException("Error Pushing Activity to Tile [" + tileInstance.getJivePushUrl() + "]",bre,tileInstance,data,data.getClass());
        } catch (InterruptedException ie) {
            log.error("Error Pushing Activity to Tile [" + tileInstance.getJivePushUrl() + "]", ie);
            throw JiveClientException.buildException("Error Pushing Activity to Tile [" + tileInstance.getJivePushUrl() + "]",ie,tileInstance,data,data.getClass());
        } catch (ExecutionException ee) {
            log.error("Error Pushing Activity to Tile [" + tileInstance.getJivePushUrl() + "]", ee);
            throw JiveClientException.buildException("Error Pushing Activity to Tile [" + tileInstance.getJivePushUrl() + "]",ee,tileInstance,data,data.getClass());
        } finally {
            if (response != null ) {
                response.close();
            }
        }
        client.close();
        client = null;
        return created;
    }

    /**
     * Make http request to url, with given method and content. Tile instance is used to insert
     * auth headers into the request.
     *
     * @throws TargetGoneException Thrown when jive responds with 410, meaning the object identified by url has been removed
     */
    public ActivityEntry makeRequest(TileInstance tileInstance, String url, String methodName, Object content) throws TargetGoneException {
        initAccessTokens(tileInstance);
        Client client = buildClient();
        AsyncInvoker asyncInvoker = client
                .target(url)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tileInstance.getCredentials().getAccessToken()).async();

        Entity contentEntity = Entity.entity(content, MediaType.APPLICATION_JSON_TYPE);
        Future<Response> responseFuture = asyncInvoker.method(methodName, contentEntity);
        Response response = null;
        try {
            response = responseFuture.get();
            if (response.getStatus() == Response.Status.GONE.getStatusCode()) {
                // target (tile instance, external stream object, etc..) has been removed from Jive
                // throw a checked exception so that caller can react accordingly
                throw new TargetGoneException("", tileInstance);
            }
            // todo how can I make this generic
            //return response.readEntity(new GenericType<T>() {});   // this does not work
            return response.readEntity(ActivityEntry.class);
        }
        catch (ForbiddenException fe) {
            log.debug("403 response from a " + methodName + " to " + url);
            refreshAccessTokens(tileInstance);
            return makeRequest(tileInstance, url, methodName, content); // recursive call to retry
        }
        catch (InterruptedException e) {
            // todo useless to catch these... refactor somehow
            log.error("Error when making a " + methodName + " to " + url, e);
            throw new RuntimeException("Failed to make a request to jive");
        }
        catch (ExecutionException e) {
            log.error("Error when making a " + methodName + " to " + url, e);
            throw new RuntimeException("Failed to make a request to jive");
        }
        finally {
            if (response != null ) {
                response.close();
            }
            client.close();
        }
    }

    private void refreshAccessTokens(TileInstance tileInstance) {
        tileInstance.setCredentials(jiveOAuthClient.refreshTileInstanceAccessToken(tileInstance));
        try {
            jiveAddOnApplication.getTileInstanceProvider().update(tileInstance);
            if (log.isDebugEnabled()) { log.debug("Successfully Updated Tile Instance!"); }
        } catch (TileInstanceProvider.TileInstanceProviderException tipe) {
            log.error("Unable to save credential information",tipe);
        }
    }

    private void initAccessTokens(TileInstance tileInstance) {
        if (log.isTraceEnabled()) { log.trace("Initializing OAuth Access Tokens"); }
        if (!JiveSDKUtils.isAllExist(tileInstance.getCredentials().getRefreshToken())) {
            if (log.isDebugEnabled()) { log.debug("Refresh OAuth Token Missing ..."); }
            tileInstance.setCredentials(jiveOAuthClient.getTileInstanceAccessToken(tileInstance));

            //TODO: SHOULD I SAVE EXPIRES TIME / CALCULATE AND REFRESH PROACTIVELY?

            try {
                jiveAddOnApplication.getTileInstanceProvider().update(tileInstance);
                if (log.isDebugEnabled()) { log.debug("Successfully Updated Tile Instance!"); }
            } catch (TileInstanceProvider.TileInstanceProviderException tipe) {
                log.error("Unable to save credential information",tipe);
            } // end try/catch

        } // end if

    } // end initAccessTokens
    
    private Client buildClient() {
        Client client = ClientBuilder.newClient();

        if (log.isDebugEnabled()) {
            client.register(DebugClientResponseFilter.class);
        } // end if
        client.register(JacksonFeature.class);

        return client;
    } // end buildClient

    class DataBlock {
        @JsonProperty("data")
        private Object data;
        @JsonProperty("message")
        private Map<String,Object> message;
        @JsonProperty("status")
        private Map<String,Object> status;

        DataBlock() {
            this.message = Maps.newHashMap();
            this.status = Maps.newHashMap();
        }

        DataBlock(Object data) {
            this();
            this.data = data;
        } // end constructor

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Map<String, Object> getMessage() {
            return message;
        }

        public void setMessage(Map<String, Object> message) {
            this.message = message;
        }

        public Map<String, Object> getStatus() {
            return status;
        }

        public void setStatus(Map<String, Object> status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "DataBlock{" +
                    "data=" + data +
                    ", message=" + message +
                    ", status=" + status +
                    '}';
        }
    } // end class

} // end JiveTileClient