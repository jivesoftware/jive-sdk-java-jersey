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

package com.jivesoftware.sdk.service.oauth.facebook;

import com.jivesoftware.sdk.config.oauth.FacebookOAuth2ServiceConfig;
import com.jivesoftware.sdk.event.OAuthEvent;
import com.jivesoftware.sdk.event.OAuthEventPublisher;
import com.jivesoftware.sdk.service.filter.JiveSignatureValidation;
import com.jivesoftware.sdk.service.oauth.BaseOAuthService;
import com.jivesoftware.sdk.utils.JiveSDKUtils;
import org.glassfish.jersey.client.oauth2.ClientIdentifier;
import org.glassfish.jersey.client.oauth2.OAuth2ClientSupport;
import org.glassfish.jersey.client.oauth2.OAuth2CodeGrantFlow;
import org.glassfish.jersey.client.oauth2.TokenResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created by rrutan on 1/29/14.
 */
@Component
@Path("/oauth2/facebook")
@Singleton
public class FacebookOAuth2Service extends BaseOAuthService {
    private static final Logger log = LoggerFactory.getLogger(FacebookOAuth2Service.class);
    public static final String SERVICE_NAME = "facebook";

    @Autowired
    private FacebookOAuth2ServiceConfig serviceConfig;

    @Override
    public String getOAuthServiceName() {   return SERVICE_NAME; }

    @Override
    public int getOAuthVersion() { return 2; }

    @Autowired @Qualifier("oauthEventPublisher")
    private OAuthEventPublisher oAuthEventPublisher;

    @GET
    @Path("/authorize")
    @JiveSignatureValidation
    public Response authorize(@Context HttpServletRequest request, @Context UriInfo uriInfo) {

        String instanceID = "TODO"; //TODO: HEADER PARAM / QUERY PARAM
        String userID = "TODO";     //TODO: HEADER PARAM / QUERY PARAM

        //TODO: PARAMETERIZE
        ClientIdentifier clientIdentifier = new ClientIdentifier(serviceConfig.getClientID(),serviceConfig.getClientSecret());
        OAuth2CodeGrantFlow flow = OAuth2ClientSupport
                .facebookFlowBuilder(clientIdentifier,uriInfo.getBaseUri() + "oauth2/"+SERVICE_NAME+"/callback")
                .scope(serviceConfig.getScope()).build();

        String authorizationUrl = flow.start();

        try {
            URI authorizationUri = new URI(authorizationUrl);

            /** LOAD INTO SESSION FOR FOLLOW-UP HIT **/
            request.getSession().setAttribute(getFlowSessionKey(),flow);
            request.getSession().setAttribute(getInstanceIDSessionKey(),instanceID);
            request.getSession().setAttribute(getUserIDSessionKey(),userID);

            //*** NOTE: 303 "See Other" NEEDED FOR JERSEY FLOW TO PICK UP
            return Response.seeOther(authorizationUri).build();
        } catch (URISyntaxException use) {
            log.error("Invalid Authorization URI: "+authorizationUrl);
            return Response.serverError().entity("Unable to Process this Request").build();
        } // end try/catch

    } // end authorize

    @GET
    @Path("/callback")
    public Response callback(@Context HttpServletRequest request, @Context UriInfo uriInfo,
                             @QueryParam("code") String code, @QueryParam("state") String state,
                             @QueryParam("error") String error, @QueryParam("error_description") String description,
                             @QueryParam("error_reason") String reason) {

        if (error != null) {
            /*** RETRIEVE FROM SESSION TO CLOSE OUT THE FLOW ***/
            request.getSession().removeAttribute(getFlowSessionKey());
            request.getSession().removeAttribute(getInstanceIDSessionKey());
            request.getSession().removeAttribute(getUserIDSessionKey());

            if (log.isDebugEnabled()) { log.debug("Callback Error: error:"+error+", description:"+description+", reason:"+reason); }

            return Response.serverError().entity("error:"+error+", description:"+description+", reason:"+reason).build();
        } // end if

        if (code == null) {
            System.out.println("Code is null");
            return Response.status(Response.Status.BAD_REQUEST).entity("Authorization code required").build();
        } // end if

        if (state == null) {
            System.out.println("State is null");
            return Response.status(Response.Status.BAD_REQUEST).entity("Missing state string").build();
        } // end if

        /*** RETRIEVE FROM SESSION TO CLOSE OUT THE FLOW ***/
        OAuth2CodeGrantFlow flow = (OAuth2CodeGrantFlow)request.getSession().getAttribute(getFlowSessionKey());
        String instanceID = (String)request.getSession().getAttribute(getInstanceIDSessionKey());
        String userID =  (String)request.getSession().getAttribute(getUserIDSessionKey());

        if (JiveSDKUtils.isAllExist(instanceID, userID, flow)) {

            TokenResult tokenResult = flow.finish(code,state);

            if (log.isDebugEnabled()) { log.debug("Successfully Retrieved OAuth Tokens["+SERVICE_NAME+"]: instanceID="+instanceID+", code="+code+", result="+tokenResult); }
            fireOAuthEvent(OAuthEvent.Type.GrantSuccess,getOAuth2GrantSuccessData(instanceID, userID, code, tokenResult));

            try {
                URI uri = new URI("/oauth/"+SERVICE_NAME+"/callback-close.jsp");
                return Response.temporaryRedirect(uri).build();
            } catch (URISyntaxException use) {
                log.error("Invalid Authorization URI: /oauth/"+SERVICE_NAME+"/callback-close.jsp",use);
                return Response.serverError().entity("Invalid Close URL").build();
            } // end try/catch

        } // end if
        return Response.status(404).entity("Resource Not Found").build();
    } // end callback

    @GET
    @Path("/deauthorize")
    public Response deauthorize(@Context HttpServletRequest request, @Context UriInfo uriInfo) {
        if (log.isDebugEnabled()) { log.debug("deauthorize called"); }

        return Response.status(404).entity("unimplemented").build();
    } // end deauthorize


    /******************************************************************************************************
     * EVENT FIRING
     *
     *
     ******************************************************************************************************/

    private void fireOAuthEvent(OAuthEvent.Type type, Map<String, Object> data) {
        oAuthEventPublisher.publishEvent(new OAuthEvent(type, data));
    } // end fireOAuthEvent

} // end class