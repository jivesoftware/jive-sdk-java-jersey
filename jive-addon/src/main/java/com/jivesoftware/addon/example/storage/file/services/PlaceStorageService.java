/**
 * 
 */
package com.jivesoftware.addon.example.storage.file.services;

import java.io.IOException;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jivesoftware.addon.example.storage.file.managers.FileStorage;
import com.jivesoftware.addon.example.storage.file.model.PlaceConfiguration;
import com.jivesoftware.addon.example.storage.file.services.resources.FileStorageResponseResourceWrapper;
import com.jivesoftware.addon.example.storage.file.storage.models.ExStorageContainerRegistrationRequestEntity;
import com.jivesoftware.addon.example.storage.file.storage.models.ExStorageContainerRegistrationResponseEntity;

/**
 * @author david.nicholls
 *
 */
@Path("/filestorage/workspaces")
@Singleton
public class PlaceStorageService {
	private static final Logger log = LoggerFactory.getLogger(PlaceStorageService.class);
	
	@POST
    @Path("/register")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces(MediaType.APPLICATION_JSON)
    public ExStorageContainerRegistrationResponseEntity register(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            ExStorageContainerRegistrationRequestEntity request) {
    	ExStorageContainerRegistrationResponseEntity response = new ExStorageContainerRegistrationResponseEntity();
    	
    	PlaceConfiguration config = convertPlaceConfiguration(request.getConfig());
    	
    	response.setExternalId(Long.toString(config.getWorkspaceId())); // Workspace ID that maps to the place.
    	response.setConfig(request.getConfig());
    	
    	FileStorage.createWorkspace(Long.toString(config.getWorkspaceId()));
    	
    	FileStorageResponseResourceWrapper.wrapWithResources(response);
    	
        return response;
    } // end register
    
    private PlaceConfiguration convertPlaceConfiguration(String json) {    	
		try {
			PlaceConfiguration placeConfig = new ObjectMapper().readValue(json, PlaceConfiguration.class);
			return placeConfig;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }

    @POST
    @Path("/unregister")
    @Produces(MediaType.APPLICATION_JSON)
    public Response unregister(
                @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization) {
        return Response.ok("unregister - unimplemented").build();
    } // end unregister
}
