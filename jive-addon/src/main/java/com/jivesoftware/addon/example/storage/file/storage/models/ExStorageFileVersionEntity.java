package com.jivesoftware.addon.example.storage.file.storage.models;


public class ExStorageFileVersionEntity extends ExStorageResourcefulDTO
    implements ExternallyIdentifiable
{
    public enum Resources {
        /**
         * <p>Called when Jive needs to download the version binary from the ESP</p>
         * @returns A binary stream of the content of the requested file version
         */
        downloadVersion,

        /**
         * <p>Called when specific version metadata (name, description, etc...) has changed in Jive</p>
         * @takes the updated file metadata to be written in the ESP
         * @returns the version metadata as it got written in the ESP
         */
        editMetadata,

        /**
         * <p>Called when specific version was deleted in Jive</p>
         * @returns the file entity with updated data after the version delete
         */
        delete,
    }

    private Long id;
    private String externalId;
    private String fileName;
    private String contentType;
    private String title;
    private String description;
    private Long size;

    private ExStorageUserEntity creator;
    private Long creationDate;

    public ExStorageFileVersionEntity()
    {
    }

    public ExStorageFileVersionEntity(ExStorageFileVersionEntity toCopy) {
        super(toCopy);
        this.id = toCopy.id;
        this.externalId = toCopy.externalId;
        this.fileName = toCopy.fileName;
        this.contentType = toCopy.contentType;
        this.title = toCopy.title;
        this.description = toCopy.description;
        this.size = toCopy.size;
        this.creator = toCopy.creator == null ? null : new ExStorageUserEntity(toCopy.creator);
        this.creationDate = toCopy.creationDate;
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
     * <p>The version's file content file name</p>
     */
    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    /**
     * <p>The content file's content type</p>
     */
    public String getContentType()
    {
        return contentType;
    }

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    /**
     * <p>The version's title</p>
     */
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * <p>The version's description</p>
     */
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * <p>The version's content file size. This property must be filled by the ESP on the response to {@link ExStorageFileEntity.Resources#uploadVersion}</p>
     */
    public Long getSize()
    {
        return size;
    }

    public void setSize(Long size)
    {
        this.size = size;
    }

    /**
     * <p>The version's creator's {@link ExStorageUserEntity} object</p>
     */
    public ExStorageUserEntity getCreator()
    {
        return creator;
    }

    public void setCreator(ExStorageUserEntity creator)
    {
        this.creator = creator;
    }

    /**
     * <p>The version's creation date</p>
     */
    public Long getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(Long creationDate)
    {
        this.creationDate = creationDate;
    }
}