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

package com.jivesoftware.sdk.service.health;

import com.jivesoftware.sdk.api.entity.HealthStatusProvider;
import com.jivesoftware.sdk.service.BaseAddOnService;
import com.jivesoftware.sdk.service.filter.JiveSignatureValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by ryan.rutan on 1/15/14.
 */

@Path("/health")
@Singleton
public class HealthService extends BaseAddOnService {
    private static final Logger log = LoggerFactory.getLogger(HealthService.class);

    @Inject
    private HealthStatusProvider healthStatusProvider;

    @GET
    @Path("/ping")
    @Produces(MediaType.APPLICATION_JSON)
//  @JiveSignatureValidation - SHOULD THIS BE OPEN?s
    public Response ping(@Context UriInfo uriInfo, @Context ContainerRequestContext containerRequestContext) {
        if (log.isDebugEnabled()) { log.debug("ping called..."); }

        return Response.ok(healthStatusProvider.getStatus()).build();
    } // end ping

} // end class InstanceService
