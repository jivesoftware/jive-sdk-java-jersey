package com.jivesoftware.sdk.api.entity;

import java.io.Serializable;

/**
 * Created by rrutan on 2/4/14.
 */
public interface StorageInstance {

    public String getName();

    public void onRegister(Object data);
    public void onUnregister(Object data);
    public void onDelete(Object data);
    public void onFileDownload(Object data);
    public void onDeletePlace(Object data);
    public void onDeleteFile(Object data);
    public void onUpload(Object data);
    public void onUploadVersion(Object data);

} // en
