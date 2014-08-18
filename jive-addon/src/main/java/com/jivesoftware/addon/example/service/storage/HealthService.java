/**
 * 
 */
package com.jivesoftware.addon.example.service.storage;

import javax.inject.Singleton;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jivesoftware.addon.example.storage.file.managers.FileStorage;
import com.jivesoftware.addon.example.storage.file.storage.models.ErrorReason;
import com.jivesoftware.addon.example.storage.file.storage.models.ExStorageErrorEntity;

/**
 * @author david.nicholls
 *
 */
@Path("/storage/health")
@Singleton
public class HealthService {
	private static final Logger log = LoggerFactory.getLogger(FileStorageService.class);
	
	@POST
    @Path("/ping")
    public Response ping(
                @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, String configuration) {

        if (log.isDebugEnabled()) {
            log.debug("configuration: "+configuration);
        } // end if
        
        if (!FileStorage.testConnectivity()) {
        
	        ExStorageErrorEntity error = new ExStorageErrorEntity();
	        error.setReason(ErrorReason.HEALTHCHECK_ERROR);
	
	    	log.error("File Storage - healthCheck test error: " + ErrorReason.HEALTHCHECK_ERROR);
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        } else {
        	return Response.status(200).entity("Alive!").build();
        }

    } 
}
