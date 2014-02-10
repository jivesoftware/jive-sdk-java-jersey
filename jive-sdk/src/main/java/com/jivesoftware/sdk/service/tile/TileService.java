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

package com.jivesoftware.sdk.service.tile;

import com.google.common.collect.Maps;
import com.jivesoftware.sdk.api.entity.JiveInstance;
import com.jivesoftware.sdk.api.entity.TileInstance;
import com.jivesoftware.sdk.api.entity.TileInstanceProvider;
import com.jivesoftware.sdk.event.JiveInstanceEvent;
import com.jivesoftware.sdk.event.TileInstanceEvent;
import com.jivesoftware.sdk.event.types.tile.TileRegisterFailed;
import com.jivesoftware.sdk.event.types.tile.TileRegisterSuccess;
import com.jivesoftware.sdk.event.types.tile.TileUnregister;
import com.jivesoftware.sdk.service.BaseAddOnService;
import com.jivesoftware.sdk.service.filter.JiveAuthorizationValidation;
import com.jivesoftware.sdk.service.filter.JiveAuthorizationValidator;
import com.jivesoftware.sdk.service.tile.action.TileRegisterAction;
import com.jivesoftware.sdk.service.tile.action.TileUnregisterAction;
import com.jivesoftware.sdk.utils.JiveSDKUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

/**
 * Created by ryan.rutan on 1/15/14.
 */

@Path("/tile")
@Singleton
public class TileService extends BaseAddOnService {
    private static final Logger log = LoggerFactory.getLogger(TileService.class);

    @Inject
    private TileInstanceProvider tileInstanceProvider;

    @Inject @TileRegisterSuccess
    Event<TileInstanceEvent> tileInstanceRegisterSuccess;

    @Inject @TileRegisterFailed
    Event<TileInstanceEvent> tileInstanceRegisterFailed;

    @Inject @TileUnregister
    Event<TileInstanceEvent> tileInstanceUnregister;

    private void fireTileInstanceEvent(TileInstanceEvent.Type type, TileInstance context, Throwable error) {
        if (log.isTraceEnabled()) { log.trace("fireTileInstanceEvent called..."); }
        if (log.isDebugEnabled()) { log.debug("Firing[TileInstanceEvent]["+type+"] ..."); }
        TileInstanceEvent event = new TileInstanceEvent(type,context.getItemType());
        event.setContext(context);
        if (error != null) {
            event.setError(error);
        } // end if
        switch (type) {
            case RegisterSuccess :   { tileInstanceRegisterSuccess.fire(event); break; }
            case RegisterFailed :    { tileInstanceRegisterFailed.fire(event);  break; }
            case Unregister :        { tileInstanceUnregister.fire(event);      break; }
        } // end switch
    } // end fireTileInstanceEvent

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @JiveAuthorizationValidation
    public Response register(
            @Context ContainerRequestContext containerRequestContext,
            TileRegisterAction tileRegisterAction
    ) {
        if (log.isTraceEnabled()) { log.trace("register called..."); }
        String jiveUrl = tileRegisterAction.getJiveUrl();
        String tenantId = tileRegisterAction.getTenantID();

        if (!JiveSDKUtils.isAllExist(jiveUrl, tenantId)) {
            String msg = String.format("Jive instance URL [%s] / Tenant ID [%s] are missing from registration request", jiveUrl, tenantId);
            if (log.isWarnEnabled()) { log.warn(msg); }
            return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
        } // end if

        JiveInstance jiveInstance = (JiveInstance)containerRequestContext.getProperty(JiveAuthorizationValidator.JIVE_INSTANCE);

        TileInstance tileInstance = new TileInstance(tileRegisterAction);

        try {
            tileInstanceProvider.update(tileInstance);
            if (log.isDebugEnabled()) { log.debug("Successfully Saved Tile Instance!"); }
        } catch (TileInstanceProvider.TileInstanceProviderException tipe) {
            log.error("Unable to save TileInstance",tipe);
        } // end try/catch

        fireTileInstanceEvent(TileInstanceEvent.Type.RegisterSuccess, tileInstance,null);

        return Response.noContent().build();
    } // end register

    @POST
    @Path("/unregister")
    @Produces(MediaType.APPLICATION_JSON)
    @JiveAuthorizationValidation
    public Response unregister(
            @Context ContainerRequestContext containerRequestContext,
            TileUnregisterAction tileUnregisterAction
    ) {
        if (log.isTraceEnabled()) { log.trace("unregister called..."); }
        String jiveUrl = tileUnregisterAction.getJiveUrl();
        String tenantId = tileUnregisterAction.getTenantID();

        if (!JiveSDKUtils.isAllExist(jiveUrl, tenantId)) {
            String msg = String.format("Jive instance URL [%s] / Tenant ID [%s] are missing from registration request", jiveUrl, tenantId);
            if (log.isDebugEnabled()) { log.debug(msg); }
            return Response.status(Response.Status.BAD_REQUEST).entity("Incomplete Tile Registration").build();
        } // end if

        JiveInstance jiveInstance = (JiveInstance)containerRequestContext.getProperty(JiveAuthorizationValidator.JIVE_INSTANCE);

        TileInstance tileInstance = tileInstanceProvider.getTileInstanceByPushURL(tileUnregisterAction.getUrl());
        if (tileInstance == null) {
            if (log.isWarnEnabled()) { log.warn("Initializing from Unregister Payload, not from provider!"); }
            tileInstance = new TileInstance(tileUnregisterAction);
        } // end if

        fireTileInstanceEvent(TileInstanceEvent.Type.Unregister, tileInstance,null);

        return Response.noContent().build();
    } // end unregister

} // end class TileService
