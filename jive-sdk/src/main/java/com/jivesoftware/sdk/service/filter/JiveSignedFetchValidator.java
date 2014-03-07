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

import com.google.common.collect.Maps;
import com.jivesoftware.sdk.JiveAddOnApplication;
import com.jivesoftware.sdk.api.entity.JiveInstance;
import com.jivesoftware.sdk.api.entity.JiveInstanceProvider;
import com.jivesoftware.sdk.utils.JiveSDKUtils;
import org.apache.commons.codec.binary.Base64;

import javax.annotation.Nonnull;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by rrutan on 1/30/14.
 */
@Singleton
public class JiveSignedFetchValidator {
    private static final Logger logger = Logger.getLogger(JiveSignedFetchValidator.class.getName());

    public static final String JIVE_INSTANCE = JiveSignedFetchValidation.class.getSimpleName()+".JiveInstance";

    private static final String PARAM_ALGORITHM = "algorithm";
    private static final String PARAM_CLIENT_ID = "client_id";
    private static final String PARAM_JIVE_URL = "jive_url";
    private static final String PARAM_TENANT_ID = "tenant_id";
    private static final String PARAM_TIMESTAMP = "timestamp";
    private static final String PARAM_SIGNATURE = "signature";

    private static final String JIVE_EXTN = "JiveEXTN ";

    private static final String QUERY_PARAM_SIGNATURE = "&" + PARAM_SIGNATURE + "=";

    private static final WebApplicationException BAD_REQUEST =
       new WebApplicationException(
           Response.status(Response.Status.BAD_REQUEST).build());

    private static final WebApplicationException UNAUTHORIZED =
       new WebApplicationException(
           Response.status(Response.Status.UNAUTHORIZED).build());

    // ambiguous reference when multiple implementations exist
    //@Inject
    //private JiveInstanceProvider jiveInstanceProvider;

    @Inject
    private JiveAddOnApplication jiveAddOnApplication;

    public void authenticate(ContainerRequestContext request) {
        String authorization = request.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (!authorization.startsWith(JIVE_EXTN) || !authorization.contains(QUERY_PARAM_SIGNATURE)) {
            logger.log(Level.INFO,"Jive authorization isn't properly formatted: " + authorization);
            throw BAD_REQUEST;
        } // end if

        Map<String, String> paramMap = getParamsFromAuthz(authorization);
        String signature = paramMap.remove(PARAM_SIGNATURE);
        String algorithm = paramMap.get(PARAM_ALGORITHM);
        String clientId = paramMap.get(PARAM_CLIENT_ID);
        String jiveUrl = paramMap.get(PARAM_JIVE_URL);
        String tenantId = paramMap.get(PARAM_TENANT_ID);
        String timeStampStr = paramMap.get(PARAM_TIMESTAMP);

        if (!JiveSDKUtils.isAllExist(algorithm, clientId, jiveUrl, tenantId, timeStampStr)) {
            //TODO: LOG
            System.err.println("Jive authorization is partial: " + paramMap);
            throw BAD_REQUEST;
        } // end if

        long timeStamp = Long.parseLong(timeStampStr);
        long millisPassed = System.currentTimeMillis() - timeStamp;
        if (millisPassed < 0 || millisPassed > TimeUnit.MINUTES.toMillis(5)) {
            //TODO: LOG
            System.err.println("Jive authorization is rejected since it's " + millisPassed + "ms old (max. allowed is 5 minutes): " + paramMap);
            throw UNAUTHORIZED;
        } // end if

        //JiveInstance jiveInstance = jiveInstanceProvider.getInstanceByTenantId(tenantId);
        JiveInstance jiveInstance = jiveAddOnApplication.getJiveInstanceProvider().getInstanceByTenantId(tenantId);

        if (jiveInstance == null) {
            //TODO: LOG
            System.err.println("Jive authorization failed due to invalid tenant ID: " + tenantId);
            throw UNAUTHORIZED;
        } // end if

        String expectedClientId = jiveInstance.getClientId();
        if (!clientId.equals(expectedClientId)) {
            String msg = String.format("Jive authorization failed due to missing Client ID: Actual [%s], Expected [%s]", clientId, expectedClientId);
            //TODO: LOG
            System.err.println(msg);
            throw UNAUTHORIZED;
        } // end if

        String clientSecret = jiveInstance.getClientSecret();
        String paramStrWithoutSignature = authorization.substring(JIVE_EXTN.length(), authorization.indexOf(QUERY_PARAM_SIGNATURE));

        try {
            String expectedSignature = sign(paramStrWithoutSignature, clientSecret, algorithm);
            if (expectedSignature.equals(signature)) {
                //SAVING jiveInstance INSTANCE TO REQUEST
                //TODO:
                request.setProperty(JIVE_INSTANCE,jiveInstance);
            } else {
                //TODO: LOG
                System.err.println("Jive authorization failed due to tampered signature! Original authz: " + authorization);
                throw UNAUTHORIZED;
            } // end if
        } catch (Exception e) {
            //TODO: LOG
            System.err.println("Failed validating Jive auth. scheme"+e.getMessage());
            throw UNAUTHORIZED;
        } // end try/catch

    } // end authenticate

    @Nonnull
     private String sign(@Nonnull String str, @Nonnull String clientSecret, @Nonnull String algorithm) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
         byte[] secret = Base64.decodeBase64(clientSecret);
         SecretKeySpec secretKeySpec = new SecretKeySpec(secret, algorithm);
         Mac mac = Mac.getInstance(algorithm);
         mac.init(secretKeySpec);
         mac.update(str.getBytes("UTF-8"));
         return Base64.encodeBase64String(mac.doFinal()).replaceAll("\\s+", "");
     } // end sign

    @Nonnull
    private Map<String, String> getParamsFromAuthz(String authHeader) {
        if (!authHeader.startsWith(JIVE_EXTN)) {
            return Maps.newHashMap();
        } // end if

        authHeader = authHeader.substring(JIVE_EXTN.length());
        String[] params = authHeader.split("[?|&]");
        Map<String, String> paramMap = Maps.newHashMap();
        for (String param : params) {
            String[] tokens = param.split("=");
            if (tokens.length != 2) {
                return Maps.newHashMap();
            } // end if

            paramMap.put(JiveSDKUtils.decodeUrl(tokens[0]), JiveSDKUtils.decodeUrl(tokens[1]));
        } // end for param

        return paramMap;
    } // end getParamsFromAuthz

} // end class
