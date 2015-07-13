/**
 * 
 */
package com.jivesoftware.addon.example.storage.file.services.resources;

import java.util.ArrayList;
import java.util.List;

import com.jivesoftware.addon.example.storage.file.storage.models.ExStorageContainerRegistrationResponseEntity;
import com.jivesoftware.addon.example.storage.file.storage.models.ExStorageFileEntity;
import com.jivesoftware.addon.example.storage.file.storage.models.ExStorageStaticItemEntity;
import com.jivesoftware.addon.example.storage.file.storage.models.ResourceDTO;

/**
 * @author david.nicholls
 *
 */
public class FileStorageResponseResourceWrapper {
	
	public static final String BASE_URL = "/example/filestorage";
	
	public static final String HTTP_VERB_GET = "GET";
	public static final String HTTP_VERB_PUT = "PUT";
	public static final String HTTP_VERB_DELETE = "DELETE";
	public static final String HTTP_VERB_POST = "POST";

	public static ExStorageContainerRegistrationResponseEntity wrapWithResources(ExStorageContainerRegistrationResponseEntity response) {
        String workspaceId = response.getExternalId();
        
        List<ResourceDTO> resourcesList = new ArrayList<>();
                
        resourcesList.add(new ResourceDTO("self", BASE_URL + "/workspaces/" + workspaceId + "/", HTTP_VERB_GET, HTTP_VERB_PUT, HTTP_VERB_DELETE));
        resourcesList.add(new ResourceDTO("uploadFile", BASE_URL + "/workspaces/" + workspaceId + "/files/upload", HTTP_VERB_POST));
        resourcesList.add(new ResourceDTO("uploadAttachment", BASE_URL + "/workspaces/" + workspaceId + "/attachments/upload", HTTP_VERB_POST));
        resourcesList.add(new ResourceDTO("externalUrl", getWatchDoxExternalBaseUrl() + workspaceId, HTTP_VERB_GET)); 
        
        ResourceDTO[] resources = resourcesList.toArray(new ResourceDTO[resourcesList.size()]);        
        response.setResources(resources);
        return response;
    }
	
	public static ExStorageFileEntity wrapWithResources(String workspaceId, ExStorageFileEntity entity) {
        String externalId = entity.getExternalId();
        String versionExternalId = entity.getVersion().getExternalId();
               
        entity.setResources(buildExStorageFileResources(workspaceId, externalId, versionExternalId));
        entity.getVersion().setResources(buildExStorageFileVersionResources(workspaceId, externalId, versionExternalId));
        return entity;
    }
	
	public static ResourceDTO[] buildExStorageFileResources(String workspaceId, String externalId, String versionExternalId) {
        ResourceDTO[] resources = {
            new ResourceDTO("self", BASE_URL + "/workspaces/" + workspaceId + "/files/" + externalId + "/" + versionExternalId, HTTP_VERB_GET, HTTP_VERB_DELETE),
            new ResourceDTO("downloadVersion", BASE_URL + "/workspaces/" + workspaceId + "/files/" + externalId + "/versions/" + versionExternalId + "/downloadBinary", HTTP_VERB_GET),
            new ResourceDTO("uploadVersion", BASE_URL + "/workspaces/" + workspaceId + "/files/" + externalId + "/versions", HTTP_VERB_POST),            
            new ResourceDTO("trashFile", BASE_URL + "/workspaces/" + workspaceId + "/files/" + externalId + "/trash?versionExternalId=" + versionExternalId, HTTP_VERB_POST),
            new ResourceDTO("untrashFile", BASE_URL + "/workspaces/" + workspaceId + "/files/" + externalId + "/untrash?versionExternalId=" + versionExternalId, HTTP_VERB_POST),
            new ResourceDTO("getVersions", BASE_URL + "/workspaces/" + workspaceId + "/files/" + externalId + "/versions", HTTP_VERB_GET),
            new ResourceDTO("restoreVersion", BASE_URL + "/workspaces/" + workspaceId + "/files/" + externalId + "/restoreVersion?versionExternalId=" + versionExternalId, HTTP_VERB_POST),
            new ResourceDTO("externalUrl", getWatchDoxExternalBaseUrl() + workspaceId, HTTP_VERB_GET)
        };

        return resources;
    }
	
	private static ResourceDTO[] buildExStorageFileVersionResources(String workspaceId, String externalId, String versionExternalId) {
        ResourceDTO[] resources = {
            new ResourceDTO("self", BASE_URL + "/workspaces/" + workspaceId + "/files/" + externalId + "/versions/" + versionExternalId, HTTP_VERB_GET, HTTP_VERB_DELETE),
            new ResourceDTO("fileParent", BASE_URL + "/workspaces/" + workspaceId + "/files/" + externalId, HTTP_VERB_GET),
            new ResourceDTO("downloadVersion", BASE_URL + "/workspaces/" + workspaceId + "/files/" + externalId + "/versions/" + versionExternalId + "/downloadBinary", HTTP_VERB_GET),
        };

        return resources;
    }
	
	public static ExStorageStaticItemEntity wrapWithResources(String workspaceId, ExStorageStaticItemEntity file) {
        String externalFileId = file.getExternalId();
        ResourceDTO[] fileResources = buildExStorageAttachmentsResources(workspaceId, externalFileId);
        file.setResources(fileResources);
        return file;
    }


    public static ResourceDTO[] buildExStorageAttachmentsResources(String workspaceId, String externalFileId) {
    	
        List<ResourceDTO> resourcesList = new ArrayList<>();
    	
        // set resources to support the attachments.
        resourcesList.add(new ResourceDTO("self",BASE_URL + "/workspaces/" + workspaceId + "/attachments/" + externalFileId, HTTP_VERB_GET, HTTP_VERB_DELETE));
        resourcesList.add(new ResourceDTO("downloadAttachment",BASE_URL + "/workspaces/" + workspaceId + "/attachments/" + externalFileId +"/downloadBinary", HTTP_VERB_GET));

        ResourceDTO[] resources = resourcesList.toArray(new ResourceDTO[resourcesList.size()]);        
        return resources;
    }
	
	private static String getWatchDoxExternalBaseUrl() {
		// TODO
		return "file://pathtosharedfolder/";
	}
}
