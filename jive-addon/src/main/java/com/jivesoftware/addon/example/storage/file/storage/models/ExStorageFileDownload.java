package com.jivesoftware.addon.example.storage.file.storage.models;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: bmoshe
 * Date: 05/28/13
 * Time: 17:04
 */
public class ExStorageFileDownload
{
    Long length;
    InputStream inputStream;
    Integer retryAfter;

    public ExStorageFileDownload(Integer retryAfter)
    {
        this.retryAfter = retryAfter;
    }

    public ExStorageFileDownload(InputStream inputStream, Long length) {
        this.inputStream = inputStream;
        this.length = length;
    }

    public InputStream getInputStream()
    {
        return inputStream;
    }

    public Integer getRetryAfter()
    {
        return retryAfter;
    }

    public Long getLength() {
        return length;
    }
}
