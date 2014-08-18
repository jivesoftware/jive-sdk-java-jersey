package com.jivesoftware.addon.example.storage.file.storage.models;


public class ExStorageCommentEntity extends ExStorageResourcefulDTO
    implements ExternallyIdentifiable
{
    public enum Resources {
        /**
         * <p>Called when a comment has been updated in Jive. The ESP should edit its corresponding comment to match.</p>
         *
         * @takes {@link ExStorageCommentEntity} of the updated comment data
         * @returns {@link ExStorageCommentEntity} as was written to ESP
         */
        editComment,

        /**
         * <p>Called when a comment has been deleted in Jive. The ESP should delete its corresponding comment as well.</p>
         */
        deleteComment,

        /**
         * <p>Called when a comment reply has been added to a Comment in Jive. The ESP should create a new comment with the
         * same values to the Comment that's paired to the Jive comment in question.</p>
         *
         * @takes {@link ExStorageCommentEntity} of the added comment data
         * @returns {@link ExStorageCommentEntity} as was written to ESP
         */
        reply,

    }

    private String externalId;
    private String body;
    private String bodyType;
    private ExStorageUserEntity creator;
    private Long creationDate;

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
     * <p>The comment body. HTML content must be surrounded by &lt;body&gt; tag</p>
     */
    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    /**
     * <p>The type of content passed in {@link com.jivesoftware.addon.example.storage.file.storage.models.ExStorageCommentEntity#body}.
     * Currently only HTML is supported.</p>
     */
    public String getBodyType()
    {
        return bodyType;
    }

    public void setBodyType(String bodyType)
    {
        this.bodyType = bodyType;
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
