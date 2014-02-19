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

import com.jivesoftware.sdk.client.oauth.OAuthCredentials;
import com.jivesoftware.sdk.client.oauth.OAuthCredentialsSupport;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by rrutan on 2/8/14.
 */
public class JiveAPIClient extends BaseJiveClient {
    private static final Logger log = LoggerFactory.getLogger(JiveAPIClient.class);

    class JiveBasicAuthorization implements JiveBasicAuthSupport {

        private String username;
        private String password;

        JiveBasicAuthorization(String username, String password) {
            this.username = username;
            this.password = password;
        } // end constructor

        @Override
        public String getAuthorizationHeader() {
            StringBuffer sbuf = new StringBuffer(AUTHORIZATION_BASIC_PREFIX);
            sbuf.append(Base64.encodeBase64((username + ":" + password).getBytes()));
            return sbuf.toString();
        } // end getAuthorizationHeader

    } // end class

    class JiveOAuth2Authorization implements JiveOAuth2Support {

        private String accessToken;

        JiveOAuth2Authorization(OAuthCredentials credentials) {
            if (credentials != null) {
                accessToken = credentials.getAccessToken();
            } // end if
        } // end constructor

        @Override
        public String getAuthorizationHeader() {
            StringBuffer sbuf = new StringBuffer(AUTHORIZATION_OAUTH_PREFIX);
            sbuf.append(accessToken);
            return sbuf.toString();
        } // end getAuthorizationHeader

    } // end class

    @Context
    Application application;

    public JiveBasicAuthorization getBasicAuth(String username, String password) {
        return new JiveBasicAuthorization(username,password);
    } //end getBasicAuth

    public JiveOAuth2Authorization getOAuth2Auth(OAuthCredentials credentials) {
        return new JiveOAuth2Authorization(credentials);
    } //end getBasicAuth

    public Object call (HttpMethods method, URI uri, String requestContentType, String responseContentType, Object data,
                        JiveAuthorizationSupport authorization, Class clazz) throws JiveClientException {
        return call(method,uri,requestContentType,responseContentType,data,authorization,null,clazz);
    } // end call

    public Object call (HttpMethods method, URI uri, String requestContentType, String responseContentType, Object data,
                        JiveAuthorizationSupport authorization, JiveRunAs runAs, Class clazz) throws JiveClientException {

        /**** NEED TO MAKE SURE ****/
        if (requestContentType == null) {
            requestContentType = MediaType.APPLICATION_JSON;
        } // end if

        if (clazz == null) {
            clazz = Object.class;
        } // end if

        Client client = buildClient();

        WebTarget target = client.target(uri);

        AsyncInvoker invoker = getAsyncInvoker(target, requestContentType, authorization, runAs);

        Future<Object> responseFuture = null;

        if (data != null) {
            DataBlock dataBlock = new DataBlock();
            Entity entity = Entity.entity(dataBlock, MediaType.APPLICATION_JSON_TYPE);
            responseFuture = invoker.method(method.name(),entity,clazz);
        } else {
            responseFuture = invoker.method(method.name(),clazz);
        } // end if

        Object response = null;

        try {
            response = responseFuture.get();

            //TODO: DETERMINE STATUS CODE??

            if (log.isInfoEnabled()) { log.info("Successful Push ["+uri+"] ..."); }
            return response;
        } catch (BadRequestException bre) {
            log.error("Error Pushing Data to Tile [" + uri + "]", bre);
            throw JiveClientException.buildException("Error Pushing Data to Tile [" + uri + "]",bre,null,data,data.getClass());
        } catch (InterruptedException ie) {
            log.error("Error Pushing Data to Tile [" + uri + "]", ie);
            throw JiveClientException.buildException("Error Pushing Data to Tile [" + uri + "]",ie,null,data,data.getClass());
        } catch (ExecutionException ee) {
            log.error("Error Pushing Data to Tile [" + uri + "]", ee);
            throw JiveClientException.buildException("Error Pushing Data to Tile [" + uri + "]",ee,null,data,data.getClass());
        } // end try/catch

    } // end call

} // end class
