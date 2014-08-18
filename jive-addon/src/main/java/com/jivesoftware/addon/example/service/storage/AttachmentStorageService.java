/**
 * 
 */
package com.jivesoftware.addon.example.service.storage;

import com.jivesoftware.addon.example.storage.file.managers.ExternalDocumentIDGenerator;
import com.jivesoftware.addon.example.storage.file.managers.FileStorage;
import com.jivesoftware.addon.example.storage.file.services.resources.FileStorageResponseResourceWrapper;
import com.jivesoftware.addon.example.storage.file.storage.models.ExStorageStaticItemEntity;
import com.jivesoftware.addon.example.storage.file.storage.models.ExStorageUserEntity;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

/**
 * @author david.nicholls
 *
 */
@Path("/storage/workspaces/{workspaceId}/attachments")
@Singleton
public class AttachmentStorageService {
	
	private static final Logger log = LoggerFactory.getLogger(AttachmentStorageService.class);
	
	@POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)    
    public ExStorageStaticItemEntity upload(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            @PathParam("workspaceId") String workspaceId,
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileData,
            @FormDataParam("metadata") ExStorageStaticItemEntity metadata) {

		
		String externalId = Long.toString(ExternalDocumentIDGenerator.getNextID());
		Long fileSize = FileStorage.uploadAttachment(workspaceId, uploadedInputStream, fileData.getFileName(), externalId);
		
		metadata.setExternalId(externalId);				
		metadata.setSize(fileSize);
		
		metadata = FileStorageResponseResourceWrapper.wrapWithResources(workspaceId, metadata);
		
        return metadata;
    }

    @GET
    @Path("/{externalId}/downloadBinary")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream download(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            @PathParam("workspaceId") String workspaceId, @PathParam("externalId") String externalId) {
        InputStream inputStream = FileStorage.downloadAttachment(workspaceId, "", externalId);
        return inputStream;
    } 
    
    @DELETE
    @Path("/{externalId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ExStorageStaticItemEntity deleteFile(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            @PathParam("workspaceId") String workspaceId, @PathParam("externalId") String externalId) {
    	FileStorage.deleteAttachment(workspaceId, "", externalId);
    	return createDummyMetadata(workspaceId, externalId);
    } 
    
    private ExStorageStaticItemEntity createDummyMetadata(String workspaceId, String externalId) {
        
    	ExStorageStaticItemEntity metadata = new ExStorageStaticItemEntity();
        metadata.setExternalId("UNKNOWN");     
        metadata.setId(-1L); 
		ExStorageUserEntity exStorageUserEntity = new ExStorageUserEntity();
		exStorageUserEntity.setDisplayName("USER DISPLAY NAME NOT SUPPORTED"); 
		metadata.setCreator(exStorageUserEntity);
		return metadata;
    }
}
