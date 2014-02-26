/*
 *
 *  * Copyright 2013 Jive Software
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 */

package com.jivesoftware.sdk.api.entity;

import com.jivesoftware.sdk.client.oauth.OAuthCredentials;
import com.jivesoftware.sdk.client.oauth.OAuthCredentialsSupport;
import com.jivesoftware.sdk.service.instance.action.InstanceRegisterAction;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Represents a Jive instance where this add-on has been installed
 * Created by rrutan on 1/30/14.
 */
@Entity
public class JiveInstance implements OAuthCredentialsSupport, Serializable {
    @Id
    @GeneratedValue
    @Column(name = "jive_instance_id")
    private Long id;

    private String tenantId = null;
    private String jiveSignatureURL = null;
    private String timestamp = null;
    private String jiveUrl = null;
    private String jiveSignature = null;
    private String clientSecret = null;
    private String clientId = null;
    private String code = null;
    private String scope = null;
    @Embedded
    private OAuthCredentials credentials = null;

    public JiveInstance() {
        credentials = new OAuthCredentials();
    } // end constructor

    public JiveInstance(InstanceRegisterAction instance) {
        this();
        this.tenantId = instance.getTenantId();
        this.jiveSignatureURL = instance.getJiveSignatureURL();
        this.timestamp = instance.getTimestamp();
        this.jiveUrl = instance.getJiveUrl();
        this.jiveSignature = instance.getJiveSignature();
        this.clientSecret = instance.getClientSecret();
        this.clientId = instance.getClientId();
        this.code = instance.getCode();
        this.scope = instance.getScope();
    } // end constructor

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getJiveSignatureURL() {
        return jiveSignatureURL;
    }

    public void setJiveSignatureURL(String jiveSignatureURL) {
        this.jiveSignatureURL = jiveSignatureURL;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getJiveUrl() {
        return jiveUrl;
    }

    public void setJiveUrl(String jiveUrl) {
        this.jiveUrl = jiveUrl;
    }

    public String getJiveSignature() {
        return jiveSignature;
    }

    public void setJiveSignature(String jiveSignature) {
        this.jiveSignature = jiveSignature;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public OAuthCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(OAuthCredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JiveInstance that = (JiveInstance) o;

        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null) return false;
        if (clientSecret != null ? !clientSecret.equals(that.clientSecret) : that.clientSecret != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (credentials != null ? !credentials.equals(that.credentials) : that.credentials != null) return false;
        if (jiveSignature != null ? !jiveSignature.equals(that.jiveSignature) : that.jiveSignature != null)
            return false;
        if (jiveSignatureURL != null ? !jiveSignatureURL.equals(that.jiveSignatureURL) : that.jiveSignatureURL != null)
            return false;
        if (jiveUrl != null ? !jiveUrl.equals(that.jiveUrl) : that.jiveUrl != null) return false;
        if (scope != null ? !scope.equals(that.scope) : that.scope != null) return false;
        if (tenantId != null ? !tenantId.equals(that.tenantId) : that.tenantId != null) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tenantId != null ? tenantId.hashCode() : 0;
        result = 31 * result + (jiveSignatureURL != null ? jiveSignatureURL.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (jiveUrl != null ? jiveUrl.hashCode() : 0);
        result = 31 * result + (jiveSignature != null ? jiveSignature.hashCode() : 0);
        result = 31 * result + (clientSecret != null ? clientSecret.hashCode() : 0);
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (scope != null ? scope.hashCode() : 0);
        result = 31 * result + (credentials != null ? credentials.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JiveInstance{" +
                "tenantId='" + tenantId + '\'' +
                ", jiveSignatureURL='" + jiveSignatureURL + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", jiveUrl='" + jiveUrl + '\'' +
                ", jiveSignature='" + jiveSignature + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", clientId='" + clientId + '\'' +
                ", code='" + code + '\'' +
                ", scope='" + scope + '\'' +
                ", credentials=" + credentials +
                '}';
    }
}
