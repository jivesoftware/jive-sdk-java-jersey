package com.jivesoftware.addon.example.storage.file.storage.models;



public class ExStorageFileEntity extends ExStorageResourcefulDTO
    implements CoreIdentifiable, ExternallyIdentifiable
{
	public enum Resources {
        /**
         * <p>Called when file metadata changes for a document. Examples for metadata changes would include document name,
         * document description, resources, etc...</p>
         * @returns {@link ExStorageFileEntity} with the updated file metadata
         */
        editFileMetadata,
        /**
         * <p>Called when a a document is deleted in Jive. When this resource is invoked, the ESP would need to delete
         * the file copy it obtains</p>
         */
        deleteFile,

        /**
         * <p>Called when a previous version of a document is chosen to be restored as latest in Jive</p>
         *
         * @takes The file object representing the file that needs to be restored including the last version for that file
         * @returns {@link ExStorageFileEntity} with updated latest version
         */
        restoreVersion,

        /**
         * <p>Called when the document is deleted in Jive. ESP should trash the File with an ability to restore it.</p>
         * <p>This resource is optional. If not returned, DELETE self would be called when users choose to delete
         * documents in Jive. If this resource is returned you must also return a {@link Resources#untrashFile} resource</p>
         * @returns {@link ExStorageFileEntity} metadata for the trashed file
         */
        trashFile,

        /**
         * <p>Called when the document is restored from trash in Jive.</p>
         * <p>This resource is optional, depending on whether or not {@link Resources#trashFile} is present.</p>
         * @returns {@link ExStorageFileEntity} metadata for the restored file
         */
        untrashFile,

        /**
         * <p>Called when document's tags are changed in Jive. The ESP should be able to update its own tags.</p>
         * <p>This resource is optional, if not present tags will not be synchronized</p>
         */
        tags,

        /**
         * <p>Called when a new version has been uploaded for a document in Jive</p>
         * <p>This call means that a new version was uploaded on Jive. The ESP should now save the binary under a specific
         * document, and return the File entity (including all required File entity resources) for that saved version</p>
         * <p>Note that the File entity returned by the call should have its size property set to the amount of bytes
         * written by the ESP</p>
         *
         * @takes The binary stream that should be uploaded as a version to the document
         * @returns {@link ExStorageFileEntity} containing the file with the latest uploaded version
         */
        uploadVersion,

        /**
         * <p>Called when a comment has been added to a file in Jive. The ESP should create a new comment with the same
         * values to the File that's paired to the Jive document in question.</p>
         *
         * @takes {@link ExStorageCommentEntity} of the added comment data
         * @returns {@link ExStorageCommentEntity} as was written to ESP
         */
        addComment,
    }

    private Long id;
    private String externalId;
    private ExStorageFileVersionEntity version;
    private ExStorageUserEntity creator;
    private Long creationDate;
    private Integer versionCount;

    public ExStorageFileEntity()
    {
    }

    public ExStorageFileEntity(ExStorageFileEntity toCopy)
    {
        super(toCopy);
        this.id = toCopy.id;
        this.externalId = toCopy.externalId;
        this.version = toCopy.version == null ? null : new ExStorageFileVersionEntity(toCopy.version);
        this.creator = toCopy.creator == null ? null : new ExStorageUserEntity(toCopy.creator);
        this.creationDate = toCopy.creationDate;
        this.versionCount = toCopy.versionCount;
    }

    /**
     * <p>The internal Jive ID given to this object.</p>
     */
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * <p>The external ID given to this object by the ESP. This is an arbitrary string that uniquely identifies the
     * object in the provider.</p>
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
     * <p>The {@link ExStorageUserEntity} that created the {@link ExStorageFileEntity}</p>
     */
    public ExStorageUserEntity getCreator()
    {
        return creator;
    }

    /**
     * <p>The latest {@link ExStorageFileVersionEntity} for this File entity</p>
     */
    public ExStorageFileVersionEntity getVersion()
    {
        return version;
    }

    public void setVersion(ExStorageFileVersionEntity version)
    {
        this.version = version;
    }

    public void setCreator(ExStorageUserEntity creator)
    {
        this.creator = creator;
    }

    /**
     * <p>The date the File was created in</p>
     */
    public Long getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(Long creationDate)
    {
        this.creationDate = creationDate;
    }

    /**
     * <p>The number of version that currently exist for the document</p>
     */
    public Integer getVersionCount()
    {
        return versionCount;
    }

    public void setVersionCount(Integer versionCount)
    {
        this.versionCount = versionCount;
    }

    public static boolean includesOnlyFileResources(ExStorageFileEntity fileDTO) {
        return fileDTO.getVersion() == null &&
                fileDTO.getCreationDate() == null &&
                fileDTO.getCreator() == null &&
                fileDTO.getVersionCount() == null &&
                fileDTO.getResources() != null;
    }


}