package com.jivesoftware.addon.example.storage.file.storage.models;

import java.util.HashMap;
import java.util.Map;

/**
 * The entity to pass when errors are returned when communicating between Jive and ESPs.
 */
public class ExStorageErrorEntity {
    ErrorReason reason;
    String message;
    Map<String, String> params = new HashMap<>();

    public ExStorageErrorEntity() {
    }

    /**
     * <p>The type of error being thrown. can be one of the following:
     * PROVIDER_UNAVAILABLE,
     PROVIDER_UNAUTHORIZED,
     UNAUTHORIZED,
     NOT_FOUND,
     ILLEGAL_NAME,
     ILLEGAL_CONTENT_TYPE,
     ALREADY_EXISTS,
     QUOTA_EXCEEDED,
     RESOURCE_OUT_OF_SYNC,
     LOCKED,
     RESOURCE_TEMPORARILY_UNAVAILABLE,
     MISSING_CONFIGURATION,
     HEALTHCHECK_ERROR,
     PROVIDER_BUSY,
     UNKNOWN</p>
     */
    public ErrorReason getReason() {
        return reason;
    }

    public void setReason(ErrorReason reason) {
        this.reason = reason;
    }

    /**
     * <p>The error message to show.</p>
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * <p>Parameters of the error to give extra data.</p>
     */
    public Map<String, String> getParams() {
        return params;
    }
}
