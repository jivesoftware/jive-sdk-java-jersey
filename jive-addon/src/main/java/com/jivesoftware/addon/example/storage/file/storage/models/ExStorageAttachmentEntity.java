package com.jivesoftware.addon.example.storage.file.storage.models;


/**
 * <p>This entity represents an attachment to a document or discussion in Jive</p>
 */
public class ExStorageAttachmentEntity extends ExStorageStaticItemEntity {
    public enum Resources {
        /**
         * <p>Called when Jive needs to download an attachment from ESP.</p>
         * @returns A binary stream
         */
        downloadAttachment,

        /**
         * <p>Called when Jive needs to delete an attachment from ESP.</p>
         */
        delete,
    }
}
