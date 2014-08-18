/**
 * 
 */
package com.jivesoftware.addon.example.service.storage;

import java.io.InputStream;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jivesoftware.addon.example.storage.file.managers.ExternalDocumentIDGenerator;
import com.jivesoftware.addon.example.storage.file.managers.ExternalDocumentVersionIDGenerator;
import com.jivesoftware.addon.example.storage.file.managers.FileStorage;
import com.jivesoftware.addon.example.storage.file.services.resources.FileStorageResponseResourceWrapper;
import com.jivesoftware.addon.example.storage.file.storage.models.ExStorageFileEntity;
import com.jivesoftware.addon.example.storage.file.storage.models.ExStorageFileVersionEntity;
import com.jivesoftware.addon.example.storage.file.storage.models.ExStorageUserEntity;

/**
 * @author david.nicholls
 *
 */
@Path("/storage/workspaces/{workspaceId}/files")
@Singleton
public class FileStorageService {
	
	private static final Logger log = LoggerFactory.getLogger(FileStorageService.class);
	
	@POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)    
    public ExStorageFileEntity upload(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("workspaceId") String workspaceId, @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileData, @FormDataParam("metadata") ExStorageFileEntity metadata) {
		
		String externalId = Long.toString(ExternalDocumentIDGenerator.getNextID());
		String externalVersionId = Long.toString(ExternalDocumentVersionIDGenerator.getNextID());
		Long fileSize = FileStorage.uploadFile(workspaceId, uploadedInputStream, fileData.getFileName(), externalId, externalVersionId);
		
		metadata.setExternalId(externalId);				
		metadata.getVersion().setExternalId(externalVersionId);
		metadata.getVersion().setSize(fileSize);
		
		metadata = FileStorageResponseResourceWrapper.wrapWithResources(workspaceId, metadata);
		throw new RuntimeException("Failed to upload file");
        //return metadata;
    }

    @POST
    @Path("/{externalId}/versions")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public ExStorageFileEntity uploadVersion(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            @PathParam("workspaceId") String workspaceId, @PathParam("externalId") String externalId, @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileData, @FormDataParam("metadata") ExStorageFileEntity metadata) {
    	
		String externalVersionId = Long.toString(ExternalDocumentVersionIDGenerator.getNextID());
		Long fileSize = FileStorage.uploadFile(workspaceId, uploadedInputStream, fileData.getFileName(), externalId, externalVersionId);
		
		metadata.setExternalId(externalId);				
		metadata.getVersion().setExternalId(externalVersionId);
		metadata.getVersion().setSize(fileSize);
		
		Integer versionCount = metadata.getVersionCount();
    	if (versionCount == null) {
    		versionCount = 0;
    	}
    	versionCount = versionCount + 1;
    	metadata.setVersionCount(versionCount);
		
		metadata = FileStorageResponseResourceWrapper.wrapWithResources(workspaceId, metadata);
		
        return metadata;
    } 

    @GET
    @Path("/{externalId}/versions/{versionExternalId}/downloadBinary")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public InputStream download(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            @PathParam("workspaceId") String workspaceId, @PathParam("externalId") String externalId, @PathParam("versionExternalId") String versionExternalId) {
        InputStream inputStream = FileStorage.downloadFile(workspaceId, "", externalId, versionExternalId);
        return inputStream;
    } 
    
    @GET
    @Path("/{externalId}/versions")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public void getFileVersions(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            @PathParam("workspaceId") String workspaceId, @PathParam("externalId") String externalId) {        
        
    } 
    
    @DELETE
    @Path("/{externalId}/{versionExternalId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ExStorageFileEntity deleteFile(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            @PathParam("workspaceId") String workspaceId, @PathParam("externalId") String externalId, @PathParam("versionExternalId") String externalVersionId) {
    	FileStorage.deleteVersion(workspaceId, "", externalId, externalVersionId);
    	return createDummyMetadata(workspaceId, externalId, externalVersionId);
    } 
    
    @DELETE
    @Path("/{externalId}/versions/{versionExternalId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ExStorageFileEntity deleteFileVersion(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            @PathParam("workspaceId") String workspaceId, @PathParam("externalId") String externalId, @PathParam("versionExternalId") String externalVersionId) {
        
    	FileStorage.deleteVersion(workspaceId, "", externalId, externalVersionId);
    	return createDummyMetadata(workspaceId, externalId, externalVersionId);
    } 
    
    @POST
    @Path("/{externalId}/trash")
    public ExStorageFileEntity trashFile(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            @PathParam("workspaceId") String workspaceId, @PathParam("externalId") String externalId, @QueryParam("versionExternalId") String externalVersionId) {
    	FileStorage.trashFile(workspaceId, "", externalId);    	
    	return createDummyMetadata(workspaceId, externalId, externalVersionId);
    }
    
    @POST
    @Path("/{externalId}/untrash")
    public ExStorageFileEntity untrashFile(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            @PathParam("workspaceId") String workspaceId, @PathParam("externalId") String externalId, @QueryParam("versionExternalId") String externalVersionId) {
    	FileStorage.unTrashFile(workspaceId, "", externalId);
    	
    	return createDummyMetadata(workspaceId, externalId, externalVersionId);
    }
    
    @POST
    @Path("/{externalId}/restoreVersion")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ExStorageFileEntity restoreFileVersion(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("workspaceId") String workspaceId, @PathParam("externalId") String externalId,
            @QueryParam("versionExternalId") String versionExternalId, ExStorageFileEntity metadata) {
    	String restoreVersionId = metadata.getVersion().getExternalId();
    	String externalVersionId = Long.toString(ExternalDocumentVersionIDGenerator.getNextID());
    	
    	InputStream inputStream = FileStorage.downloadFile(workspaceId, "", externalId, restoreVersionId);
    	Long fileSize = FileStorage.uploadFile(workspaceId, inputStream, metadata.getVersion().getFileName(), externalId, externalVersionId);
		
		metadata.setExternalId(externalId);				
		metadata.getVersion().setExternalId(externalVersionId);
		metadata.getVersion().setSize(fileSize);
		
		Integer versionCount = metadata.getVersionCount();
    	if (versionCount == null) {
    		versionCount = 0;
    	}
    	versionCount = versionCount + 1;
    	metadata.setVersionCount(versionCount);
    	
    	metadata = FileStorageResponseResourceWrapper.wrapWithResources(workspaceId, metadata);
		
        return metadata;
    }
    
    private ExStorageFileEntity createDummyMetadata(String workspaceId, String externalId, String versionId) {
    	ExStorageFileVersionEntity version = new ExStorageFileVersionEntity();
    	version.setExternalId(versionId);
        
        ExStorageFileEntity metadata = new ExStorageFileEntity();
        metadata.setExternalId("UNKNOWN");
        metadata.setVersion(version);        
        metadata.setId(-1L); 
		ExStorageUserEntity exStorageUserEntity = new ExStorageUserEntity();
		exStorageUserEntity.setDisplayName("USER DISPLAY NAME NOT SUPPORTED"); 
		metadata.setCreator(exStorageUserEntity);
		return metadata;
    }
}
