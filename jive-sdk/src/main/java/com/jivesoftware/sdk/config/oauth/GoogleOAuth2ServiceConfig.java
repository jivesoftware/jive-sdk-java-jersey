package com.jivesoftware.sdk.config.oauth;

import com.jivesoftware.sdk.config.BaseAddOnConfig;
import com.jivesoftware.sdk.service.oauth.google.GoogleOAuth2Service;
import com.jivesoftware.sdk.utils.JiveSDKUtils;

import javax.inject.Singleton;

/**
 * Created by rrutan on 2/6/14.
 */
    @Singleton
    public class GoogleOAuth2ServiceConfig extends BaseAddOnConfig {

        private String clientID;
        private String clientSecret;
        private String authorizeUrl;
        private String tokenUrl;
        private String scope;

        public GoogleOAuth2ServiceConfig() {
            JiveSDKUtils.initBeanFromProperties(GoogleOAuth2Service.class.getSimpleName() + ".properties",this);
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

        public String getTokenUrl() {
            return tokenUrl;
        }

        public void setTokenUrl(String tokenUrl) {
            this.tokenUrl = tokenUrl;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            GoogleOAuth2ServiceConfig that = (GoogleOAuth2ServiceConfig) o;

            if (authorizeUrl != null ? !authorizeUrl.equals(that.authorizeUrl) : that.authorizeUrl != null) return false;
            if (clientID != null ? !clientID.equals(that.clientID) : that.clientID != null) return false;
            if (clientSecret != null ? !clientSecret.equals(that.clientSecret) : that.clientSecret != null) return false;
            if (scope != null ? !scope.equals(that.scope) : that.scope != null) return false;
            if (tokenUrl != null ? !tokenUrl.equals(that.tokenUrl) : that.tokenUrl != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = clientID != null ? clientID.hashCode() : 0;
            result = 31 * result + (clientSecret != null ? clientSecret.hashCode() : 0);
            result = 31 * result + (authorizeUrl != null ? authorizeUrl.hashCode() : 0);
            result = 31 * result + (tokenUrl != null ? tokenUrl.hashCode() : 0);
            result = 31 * result + (scope != null ? scope.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "GoogleOAuth2ServiceConfig{" +
                    "clientID='" + clientID + '\'' +
                    ", clientSecret='" + clientSecret + '\'' +
                    ", authorizeUrl='" + authorizeUrl + '\'' +
                    ", tokenUrl='" + tokenUrl + '\'' +
                    ", scope='" + scope + '\'' +
                    '}';
        }


} // end class