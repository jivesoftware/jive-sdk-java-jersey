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

package com.jivesoftware.sdk.service.instance;

import com.jivesoftware.sdk.api.entity.JiveInstance;
import com.jivesoftware.sdk.api.entity.JiveInstanceProvider;
import com.jivesoftware.sdk.event.JiveInstanceEvent;
import com.jivesoftware.sdk.event.JiveInstanceEventPublisher;
import com.jivesoftware.sdk.service.BaseAddOnService;
import com.jivesoftware.sdk.service.filter.JiveSignatureValidation;
import com.jivesoftware.sdk.service.instance.action.InstanceRegisterAction;
import com.jivesoftware.sdk.service.instance.action.InstanceUnregisterAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by ryan.rutan on 1/15/14.
 */
@Component
@Path("/instance")
@Singleton
public class InstanceService extends BaseAddOnService {
    private static final Logger log = LoggerFactory.getLogger(InstanceService.class);

    @Autowired @Qualifier("jiveInstanceProvider")
    private JiveInstanceProvider jiveInstanceProvider;

    @Autowired @Qualifier("jiveInstanceEventPublisher")
    private JiveInstanceEventPublisher jiveInstanceEventPublisher;

    private void fireJiveInstanceEvent(JiveInstanceEvent.Type type, JiveInstance context, Throwable error) {
        if (log.isTraceEnabled()) { log.trace("fireJiveInstanceEvent called..."); }

        JiveInstanceEvent event = new JiveInstanceEvent(type);
        event.setContext(context);
        if (error != null ) {
            event.setError(error);
        } // end if

        jiveInstanceEventPublisher.publishEvent(event);
    } // end fireTileInstanceEvent

    @POST
    @Path("/register")
    @JiveSignatureValidation
    public Response register(InstanceRegisterAction instanceRegisterAction) {
        if (log.isTraceEnabled()) { log.trace("register called..."); }

        JiveInstance jiveInstance = new JiveInstance(instanceRegisterAction);

        fireJiveInstanceEvent(JiveInstanceEvent.Type.RegisterSuccess,jiveInstance,null);

        if (log.isDebugEnabled()) { log.debug("Successfully Registered Instance["+jiveInstance.getTenantId()+"]"); }

        return Response.noContent().build();
    } // end register

    @POST
    @Path("/unregister")
    @JiveSignatureValidation
    public Response unregister(@Context UriInfo uriInfo, @Context ContainerRequestContext containerRequestContext, InstanceUnregisterAction instanceUnregisterAction) {
        if (log.isTraceEnabled()) { log.trace("unregister called..."); }
        JiveInstance jiveInstance = jiveInstanceProvider.getInstanceByTenantId(instanceUnregisterAction.getTenantId());

        if (jiveInstance != null ) {
            fireJiveInstanceEvent(JiveInstanceEvent.Type.Unregister, jiveInstance,null);
        } // end if

        //TODO: HANDLE USE-CASE WHERE INSTANCE IS NOT FOUND BETTER

        return Response.noContent().build();
    } // end unregister

} // end class InstanceService
