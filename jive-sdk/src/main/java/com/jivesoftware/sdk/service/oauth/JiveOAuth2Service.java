package com.jivesoftware.sdk.service.oauth;

import com.jivesoftware.sdk.config.oauth.JiveOAuth2ServiceConfig;
import com.jivesoftware.sdk.event.OAuthEvent;
import com.jivesoftware.sdk.event.types.oauth.Jive;
import com.jivesoftware.sdk.utils.JiveSDKUtils;
import org.glassfish.jersey.client.oauth2.ClientIdentifier;
import org.glassfish.jersey.client.oauth2.OAuth2ClientSupport;
import org.glassfish.jersey.client.oauth2.OAuth2CodeGrantFlow;
import org.glassfish.jersey.client.oauth2.TokenResult;
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
@Path("/oauth2")
@Singleton
public class JiveOAuth2Service extends BaseOAuthService {
    private static final Logger log = LoggerFactory.getLogger(JiveOAuth2Service.class);
    private static final String SERVICE_NAME = "jive";

    @Inject
    private JiveOAuth2ServiceConfig serviceConfig;

    @Override
    public String getOAuthServiceName() {   return SERVICE_NAME;   }

    @Override
    public int getOAuthVersion() {  return 2; }

    @GET
    @Path("/authorize")
    public Response authorize(@Context HttpServletRequest request, @Context UriInfo uriInfo) {

        String userID = "TODO";     //TODO: HEADER/QUERY - PARAM
        String instanceID = "TODO"; //TODO: HEADER/QUERY - PARAM

        ClientIdentifier clientID = new ClientIdentifier(serviceConfig.getClientID(),serviceConfig.getClientSecret());
        OAuth2CodeGrantFlow flow = OAuth2ClientSupport.authorizationCodeGrantFlowBuilder(
                clientID,
                serviceConfig.getAuthorizeUrl(),
                serviceConfig.getTokenUrl()
            ).scope(serviceConfig.getScope())
            .redirectUri(uriInfo.getBaseUri() + "oauth2/callback")
            .build();

        String authorizationUrl = flow.start();

        try {
            URI authorizationUri = new URI(authorizationUrl);

            /** LOAD INTO SESSION FOR FOLLOW-UP HIT **/
            request.getSession().setAttribute(getFlowSessionKey(),flow);
            request.getSession().setAttribute(getInstanceIDSessionKey(),instanceID);
            request.getSession().setAttribute(getUserIDSessionKey(),userID);

            //*** NOTE: 303 "See Other" NEEDED FOR JERSEY FLOW TO PICK UP
            return Response.seeOther(new URI(authorizationUrl)).build();
        } catch (URISyntaxException use) {
            log.error("Invalid Authorization URI: "+authorizationUrl);
            return Response.serverError().entity("Unable to Process this Request").build();
        } // end try/catch

    } // end authorize

    @GET
    @Path("/callback")
    public Response callback(@Context HttpServletRequest request, @Context UriInfo uriInfo,
                             @QueryParam("code") String code, @QueryParam("state") String state) {

        if (code == null) {
            if (log.isWarnEnabled()) { log.warn("Authorization Code is null, failed oauth2 callback"); }
            return Response.status(Response.Status.BAD_REQUEST).entity("Authorization code required").build();
        } // end if

        if (state == null) {
            if (log.isWarnEnabled()) { log.warn("State is null, failed oauth2 callback"); }
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

        } else {
            if (log.isWarnEnabled()) { log.warn("Missing Required Data instanceID["+instanceID+"], userID["+userID+"]"); }
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

    @Inject @Jive
    Event<OAuthEvent> oauthEventPublisher;

    private void fireOAuthEvent(OAuthEvent.Type type, Map<String, Object> data) {
        oauthEventPublisher.fire(new OAuthEvent(type,data));
    } // end fireOAuthEvent

} // end class JiveOAuth2Service