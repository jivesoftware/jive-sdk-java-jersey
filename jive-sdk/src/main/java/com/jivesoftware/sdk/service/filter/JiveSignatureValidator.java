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

package com.jivesoftware.sdk.service.filter;

import com.jivesoftware.sdk.config.JiveAddOnConfig;
import com.jivesoftware.sdk.service.instance.action.InstanceRegisterAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by rrutan on 1/30/14.
 */
@Singleton
public class JiveSignatureValidator {
    private static final Logger log = LoggerFactory.getLogger(JiveSignatureValidator.class);

    private Client client = null;

    @Inject
    private JiveAddOnConfig config;

    private JiveSignatureValidator() {
        initClient();
    } // end constructor

    private void initClient() {
        client = ClientBuilder.newClient();
        //client.register(...)
    } //end initClient

    protected boolean isValidSignature(InstanceRegisterAction request) {
        String signature = request.getJiveSignature();
        String signatureURL = request.getJiveSignatureURL();
        boolean isHttps = signatureURL.toLowerCase().startsWith("https");

        if (signature == null || signatureURL == null) {
            String msg = String.format("Invalid signature [%s] or signature URL [%s]", signature, signatureURL);
            //TODO: ADD LOGGER
            System.err.println(msg);
            return false;
        } // end if

        if (!config.isDevelopment() && !isHttps) {
            log.error("Signature URL must be over SSL: " + signatureURL);
            return false;
        } // end if

        String signatureValidation = getSignatureValidation(request);

        WebTarget target = client.target(request.getJiveSignatureURL());
        AsyncInvoker invocation = target.request(MediaType.APPLICATION_JSON_TYPE)
                                      .header("X-Jive-MAC", request.getJiveSignature())
                                      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM)
                                      .async();

        Future<String> responseFuture = invocation.post(Entity.entity(signatureValidation, MediaType.APPLICATION_OCTET_STREAM),String.class);

        try {
            String response = responseFuture.get();
            if (log.isDebugEnabled()) { log.debug("Signature Validated"); }
            return true;
        } catch (BadRequestException bre) {
            log.error("Error Validating Signature", bre);
        } catch (InterruptedException ie) {
            log.error("Error Validating Signature", ie);
        } catch (ExecutionException ee) {
            log.error("Error Validating Signature", ee);
        } // end try/catch

        return false;
    } // end isValidSignature


    private String getSignatureValidation(InstanceRegisterAction request) {

        SortedMap<String, String> sortedMap = request.toSortedMap();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key != null && value != null) {
                sb.append(key).append(':').append(value).append((char) 10);
            } // end if
        } // end for entry
        return sb.toString();

    } // end getSignatureValidation

} // end class
