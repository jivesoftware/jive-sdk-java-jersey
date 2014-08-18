package com.jivesoftware.addon.example.storage.file.storage.models;

import java.util.Date;

/**
 * <p>This entity is a base entity for single version binary objects in ESP (like attachments or previews)</p>
 */
public class ExStorageStaticItemEntity extends ExStorageResourcefulDTO
        implements CoreIdentifiable, ExternallyIdentifiable {

    private Long id;
    private String externalId;
    private ExStorageUserEntity creator;
    private Date creationDate;
    private String contentType;
    private Long size;
    private String fileName;

    /**
     * The internal Jive ID for the object
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
     * The external ID given by the ESP for the object
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
     * <p>The content's creator {@link ExStorageUserEntity} object</p>
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
     * <p>The content's creation date</p>
     */
    public Date getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(Date creationDate)
    {
        this.creationDate = creationDate;
    }

    /**
     * <p>The content's file size. This property must be filled by the ESP in the response to upload requests</p>
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
     * <p>The file's content type</p>
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
     * <p>The content's file name</p>
     */
    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

}
