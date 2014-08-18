package com.jivesoftware.addon.example.storage.file.storage.models;


/**
 * <p>This entity represents a document preview in Jive</p>
 */
public class ExStoragePreviewEntity extends ExStorageStaticItemEntity {
    public enum Resources {
        /**
         * <p>Called when Jive needs to download a preview from ESP.</p>
         * @returns A binary stream
         */
        downloadPreview,

        /**
         * <p>Called when Jive needs to delete a preview from ESP.</p>
         */
        delete,
    }

    private String exStorageFileId;

    /**
     * The external storage for the {@link ExStorageFileEntity} entity that this preview belogs to
     */
    public String getExStorageFileId() {
        return exStorageFileId;
    }

    public void setExStorageFileId(String exStorageFileId) {
        this.exStorageFileId = exStorageFileId;
    }
}
