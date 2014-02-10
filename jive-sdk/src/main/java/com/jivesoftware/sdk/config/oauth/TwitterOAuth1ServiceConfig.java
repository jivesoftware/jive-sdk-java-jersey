package com.jivesoftware.sdk.config.oauth;

import com.jivesoftware.sdk.config.BaseAddOnConfig;
import com.jivesoftware.sdk.service.oauth.twitter.TwitterOAuth1Service;
import com.jivesoftware.sdk.utils.JiveSDKUtils;

import javax.inject.Singleton;

/**
 * Created by rrutan on 2/6/14.
 */
@Singleton
public class TwitterOAuth1ServiceConfig extends BaseAddOnConfig {

    private String clientID;
    private String clientSecret;
    private String authorizeUrl;
    private String requestTokenUrl;
    private String accessTokenUrl;

    public TwitterOAuth1ServiceConfig() {
        JiveSDKUtils.initBeanFromProperties(TwitterOAuth1Service.class.getSimpleName() + ".properties",this);
    } // end constructor

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAuthorizeUrl() {
        return authorizeUrl;
    }

    public void setAuthorizeUrl(String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
    }

    public String getRequestTokenUrl() {
        return requestTokenUrl;
    }

    public void setRequestTokenUrl(String requestTokenUrl) {
        this.requestTokenUrl = requestTokenUrl;
    }

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TwitterOAuth1ServiceConfig that = (TwitterOAuth1ServiceConfig) o;

        if (accessTokenUrl != null ? !accessTokenUrl.equals(that.accessTokenUrl) : that.accessTokenUrl != null)
            return false;
        if (authorizeUrl != null ? !authorizeUrl.equals(that.authorizeUrl) : that.authorizeUrl != null) return false;
        if (clientID != null ? !clientID.equals(that.clientID) : that.clientID != null) return false;
        if (clientSecret != null ? !clientSecret.equals(that.clientSecret) : that.clientSecret != null) return false;
        if (requestTokenUrl != null ? !requestTokenUrl.equals(that.requestTokenUrl) : that.requestTokenUrl != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = clientID != null ? clientID.hashCode() : 0;
        result = 31 * result + (clientSecret != null ? clientSecret.hashCode() : 0);
        result = 31 * result + (authorizeUrl != null ? authorizeUrl.hashCode() : 0);
        result = 31 * result + (requestTokenUrl != null ? requestTokenUrl.hashCode() : 0);
        result = 31 * result + (accessTokenUrl != null ? accessTokenUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TwitterOAuth1ServiceConfig{" +
                "clientID='" + clientID + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", authorizeUrl='" + authorizeUrl + '\'' +
                ", requestTokenUrl='" + requestTokenUrl + '\'' +
                ", accessTokenUrl='" + accessTokenUrl + '\'' +
                '}';
    }

} // end class
