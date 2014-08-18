package com.jivesoftware.addon.example.storage.file.storage.models;


/**
 * The entity to use when doing tag setting on a file.
 */
public class ExStorageTagsSetEntity {
    String[] tags;

    /**
     * <p>The tags to set in the receiving end of this request (Jive / ESP).</p>
     */
    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
