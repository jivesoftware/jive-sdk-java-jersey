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
