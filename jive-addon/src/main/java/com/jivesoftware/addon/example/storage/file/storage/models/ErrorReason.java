package com.jivesoftware.addon.example.storage.file.storage.models;

/**
 * Created with IntelliJ IDEA.
 * User: varkel
 * Date: 8/6/13
 * Time: 10:20 AM
 */
public enum ErrorReason {
    PROVIDER_UNAVAILABLE,
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
    UNKNOWN;
}
