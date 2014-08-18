package com.jivesoftware.addon.example.storage.file.storage.models;

/**
 * Created with IntelliJ IDEA.
 * User: bmoshe
 * Date: 05/28/13
 * Time: 16:39
 */
public class ExStorageProviderProperties
{
    private String baseURL;
    private String registerURL;

    public String getBaseURL()
    {
        return baseURL;
    }

    public void setBaseURL(String baseURL)
    {
        this.baseURL = baseURL;
    }

    public String getRegisterURL()
    {
        return registerURL;
    }

    public void setRegisterURL(String registerURL)
    {
        this.registerURL = registerURL;
    }
}
