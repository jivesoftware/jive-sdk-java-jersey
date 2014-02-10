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

package com.jivesoftware.sdk.service.oauth.twitter;

import com.jivesoftware.sdk.config.oauth.TwitterOAuth1ServiceConfig;
import com.jivesoftware.sdk.event.OAuthEvent;
import com.jivesoftware.sdk.event.types.oauth.ext.Twitter;
import com.jivesoftware.sdk.service.filter.JiveSignatureValidation;
import com.jivesoftware.sdk.service.oauth.BaseOAuthService;
import com.jivesoftware.sdk.utils.JiveSDKUtils;
import org.glassfish.jersey.client.oauth1.ConsumerCredentials;
import org.glassfish.jersey.client.oauth1.OAuth1AuthorizationFlow;
import org.glassfish.jersey.client.oauth1.OAuth1ClientSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Event;
import javax.inject.Inject;
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
@Path("/oauth/twitter")
@Singleton
public class TwitterOAuth1Service extends BaseOAuthService {
    private static final Logger log = LoggerFactory.getLogger(TwitterOAuth1Service.class);
    public static final String SERVICE_NAME = "twitter";

    @Inject
    private TwitterOAuth1ServiceConfig serviceConfig;

    @Override
    public String getOAuthServiceName() { return SERVICE_NAME; }

    @Override
    public int getOAuthVersion() { return 1; }

    @GET
    @Path("/authorize")
    @JiveSignatureValidation
    public Response authorize(@Context HttpServletRequest request, @Context UriInfo uriInfo) {

        String instanceID = "TODO"; //TODO: HEADER PARAM / QUERY PARAM
        String userID = "TODO";     //TODO: HEADER PARAM / QUERY PARAM

        //TODO: CAPTURE THE JIVE ID FROM SIGNED FETCH HEADERS

        ConsumerCredentials consumerCredentials = new ConsumerCredentials(serviceConfig.getClientID(),serviceConfig.getClientSecret());
        OAuth1AuthorizationFlow flow = OAuth1ClientSupport.builder(consumerCredentials)
                                        .authorizationFlow(
                                                serviceConfig.getRequestTokenUrl(),
                                                serviceConfig.getAccessTokenUrl(),
                                                serviceConfig.getAuthorizeUrl())
                                        .callbackUri(uriInfo.getBaseUri() + "oauth/"+SERVICE_NAME+"/callback")
                                        .build();

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
    public Response callback(@Context HttpServletRequest request, @Context UriInfo uriInfo, @QueryParam("oauth_token") String token, @QueryParam("oauth_verifier") String verifier) {

        if (token == null) {
            if (log.isWarnEnabled()) { log.warn("oauth_token is null, failing request"); }
            return Response.status(Response.Status.BAD_REQUEST).entity("Missing oauth_token string").build();
        } // end if

        if (verifier == null) {
            if (log.isWarnEnabled()) { log.warn("oauth_verifier is null"); }
            return Response.status(Response.Status.BAD_REQUEST).entity("Missing oauth_verifier string").build();
        } // end if

        /*** RETRIEVE FROM SESSION TO CLOSE OUT THE FLOW ***/
        OAuth1AuthorizationFlow flow = (OAuth1AuthorizationFlow)request.getSession().getAttribute(getFlowSessionKey());
        String instanceID = (String)request.getSession().getAttribute(getInstanceIDSessionKey());
        String userID =  (String)request.getSession().getAttribute(getUserIDSessionKey());

        if (JiveSDKUtils.isAllExist(instanceID, userID, flow, verifier)) {

            flow.finish(verifier);

            if (log.isDebugEnabled()) { log.debug("Successfully Retrieved OAuth Tokens["+SERVICE_NAME+"]: instanceID="+instanceID+", token="+token+", verifier="+verifier); }
            fireOAuthEvent(OAuthEvent.Type.GrantSuccess,getOAuth1GrantSuccessData(instanceID,userID,token,verifier));

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

        return Response.status(200).entity("Unimplemented").build();
    } // end deauthorize

    /******************************************************************************************************
     * EVENT FIRING
     *
     *
     ******************************************************************************************************/

    @Inject @Twitter
    Event<OAuthEvent> oauthEventPublisher;

    private void fireOAuthEvent(OAuthEvent.Type type, Map<String, Object> data) {
        oauthEventPublisher.fire(new OAuthEvent(type,data));
    } // end fireOAuthEvent

} // end class JiveOAuth2Service