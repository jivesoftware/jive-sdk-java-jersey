package com.jivesoftware.addon.example.storage.file.storage.models;

import java.util.List;

/**
 * <p>The entity to recieve from the "register" container API request defined in {@link com.jivesoftware.community.extension.updater.dataobjects.StorageDefinitionDataEntity#registerPlace}</p>
 */
public class ExStorageContainerRegistrationResponseEntity
extends ExStorageResourcefulDTO
{
    public enum Resources {

        /**
         * <p>Called when place metadata (name, description, type) is modified in Jive. This should update the same properties for the container in ESP.</p>
         *
         * @takes A {@link com.jivesoftware.community.integration.storage.models.ExStorageContainerEntity} describing the metadata of the container
         */
        updateContainer,

        /**
         * <p>Called when the place is deleted in Jive. This should delete or unlink the container in ESP.</p>
         */
        deleteContainer,

        /**
         * <p>Called when an attachment is being uploaded to content of a place connected to this ESP. The file binary should be uploaded to the ESP and resources should be returned to continue the work on this object.</p>
         * <p> This is an optional resource, if not given - attachment storage will be stored internally in Jive.</p>
         *
         * @takes A multipart/data body containing one part called 'metadata' which contains an entity of {@link com.jivesoftware.community.integration.storage.models.ExStorageStaticItemEntity} and another 'file' part contains the binary data of the file being uploaded
         * @return {@link com.jivesoftware.community.integration.storage.models.ExStorageStaticItemEntity} representing the attachment uploaded. should contain the resources to work on.
         */
        uploadAttachment,

        /**
         * <p>Called when a document-preview binary is being uploaded to a place connected to this ESP. The file binary should be uploaded to the ESP and resources should be returned to continue the work on this object.</p>
         * <p> This is an optional resource, if not given - document-preview binary storage will be stored internally in Jive.</p>
         *
         * @takes A multipart/data body containing one part called 'metadata' which contains an entity of {@link com.jivesoftware.community.integration.storage.models.ExStoragePreviewEntity} and another 'file' part contains the binary data of the file being uploaded
         * @return {@link com.jivesoftware.community.integration.storage.models.ExStoragePreviewEntity} representing the document-preview binary uploaded. should contain the resources to work on.
         * @exception com.jivesoftware.api.core.v3.exceptions.NotFoundException If the container isn't exist on the ESP side.
         */
        uploadPreview,


        /**
         * <p>Called when a document is being uploaded to a place connected to this ESP. The file binary should be uploaded to the ESP and resources should be returned to continue the work on this object.</p>
         *
         * @takes A multipart/data body containing one part called 'metadata' which contains an entity of {@link com.jivesoftware.community.integration.storage.models.ExStorageFileEntity} and another 'file' part contains the binary data of the file being uploaded
         * @return {@link com.jivesoftware.community.integration.storage.models.ExStorageFileEntity} representing the document uploaded. should contain the resources to work on.
         * @exception com.jivesoftware.api.core.v3.exceptions.NotFoundException If the container isn't exist on the ESP side.
         */
        uploadFile,

        /**
         * <p>Called when a member is added to a place connected to this ESP. The member should be added to the container in the ESP and resources should be returned to continue the work on this object.</p>
         * <p> This is an optional resource, if not given - members will not be synced to ESP.</p>

         * @takes A {@link com.jivesoftware.community.integration.storage.models.ExStorageMembershipOperationEntity} representing the member being added
         * @return {@link com.jivesoftware.community.integration.storage.models.ExStorageMembershipOperationEntity} describing the added member with resources to continue the work on this object.
         * @exception com.jivesoftware.api.core.v3.exceptions.NotFoundException If the container or member are not exist on the ESP side.
         */
        addMembers,

        /**
         * <p>Called when members are being modified in a place connected to this ESP. The members should be updated in the container in the ESP and resources of each member should be returned to continue the work on this object.</p>
         * <p> This is an optional resource, if not given - members will not be synced to ESP.</p>

         * @takes A {@link com.jivesoftware.community.integration.storage.models.ExStorageBulkMemberUpdateRequestEntity} representing the edit operation of members in the place
         * @return {@link com.jivesoftware.community.integration.storage.models.ExStorageBulkMemberUpdateResponseEntity} describing the modified members with resources to continue the work on them.
         * @exception com.jivesoftware.api.core.v3.exceptions.NotFoundException If the container or one of the members are not exist on the ESP side.
         */
        bulkMembership,
    }
    private String externalId;

    private List<ExStorageMembershipOperationResultEntity> results;
    private String config;

    /**
     * <p>The external-id of the container to be used as reference in further actions.</p>
     */
    public String getExternalId()
    {
        return externalId;
    }

    public void setExternalId(String externalId)
    {
        this.externalId = externalId;
    }

    /**
     * <p>The objects with resources associated to the members sent in {@link com.jivesoftware.addon.example.storage.file.storage.models.ExStorageContainerRegistrationRequestEntity#getMemberOperations()}.
     * If no members sent in this field, nothing should be returned in response.</p>
     */
    public List<ExStorageMembershipOperationResultEntity> getResults()
    {
        return results;
    }

    public void setResults(List<ExStorageMembershipOperationResultEntity> results)
    {
        this.results = results;
    }

    /**
     * <p>JSON config to be sent to the html page in {@link com.jivesoftware.community.extension.updater.dataobjects.StorageDefinitionDataEntity#configPlace} when managing the place post-register
     * If such a page is not configured for this ESP, nothing should be returned in this field.</p>
     */
    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
