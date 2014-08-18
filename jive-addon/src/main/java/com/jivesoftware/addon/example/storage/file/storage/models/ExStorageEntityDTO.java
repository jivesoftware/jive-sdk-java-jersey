package com.jivesoftware.addon.example.storage.file.storage.models;

/**
 * Created with IntelliJ IDEA.
 * User: reem
 * Date: 8/22/13
 * Time: 5:15 PM
 */
public abstract class ExStorageEntityDTO extends ExStorageResourcefulDTO {
    public ExStorageEntityDTO() { }

    public ExStorageEntityDTO(ExStorageResourcefulDTO toCopy) {
        super(toCopy);
    }

    public abstract String getDisplayName();

    public abstract void setDisplayName(String displayName);
}
