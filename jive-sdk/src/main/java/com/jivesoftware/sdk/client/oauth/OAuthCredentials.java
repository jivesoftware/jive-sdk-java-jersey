package com.jivesoftware.sdk.client.oauth;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by rrutan on 1/30/14.
 */
public class OAuthCredentials {
    private static final Logger log = LoggerFactory.getLogger(OAuthCredentials.class);

    private static final String INVALID = "invalid";
    private static final String OK = "ok";

    private String url;

    private String accessToken;

    private String refreshToken;

    public OAuthCredentials() {
        url = "";
        accessToken = "";
        refreshToken = "";
    }

    public OAuthCredentials(String url, String accessToken, String refreshToken) {
        this.url = url;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getUrl() {
        return url;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OAuthCredentials that = (OAuthCredentials) o;

        return accessToken.equals(that.accessToken) && refreshToken.equals(that.refreshToken) && url.equals(that.url);
    }

    @Override
    public int hashCode() {
        int result = url.hashCode();
        result = 31 * result + (accessToken.hashCode());
        result = 31 * result + (refreshToken.hashCode());
        return result;
    }

    public String toString() {
        return String.format("OAuthCredentials   {url=%s, accessToken? %s, refreshToken? %s}",
                url,
                StringUtils.isEmpty(accessToken) ? INVALID : OK,
                StringUtils.isEmpty(refreshToken) ? INVALID : OK);
    }

}
