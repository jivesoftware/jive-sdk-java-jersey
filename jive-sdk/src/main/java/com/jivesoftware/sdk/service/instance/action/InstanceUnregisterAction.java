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

package com.jivesoftware.sdk.service.instance.action;

import com.google.common.collect.Maps;
import com.jivesoftware.sdk.service.filter.JiveSignatureValidatable;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.annotation.Nonnull;
import java.util.SortedMap;

/**
 * Created by rrutan on 1/30/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstanceUnregisterAction implements JiveSignatureValidatable {

    public final static String TIMESTAMP = "timestamp";
    public final static String JIVE_URL = "jiveUrl";
    public final static String JIVE_SIGNATURE_URL = "jiveSignatureURL";
    public final static String TENANT_ID = "tenantId";
    public final static String JIVE_SIGNATURE = "jiveSignature";
    public final static String UNINSTALLED = "uninstalled";
    public final static String CLIENT_ID = "clientId";

    private String timestamp;
    private String jiveUrl;
    private String jiveSignatureURL;
    private String tenantId;
    private String jiveSignature;
    private boolean uninstalled;
    private String clientId;

    public InstanceUnregisterAction( @JsonProperty(TENANT_ID) String tenantId,
                                   @JsonProperty(JIVE_SIGNATURE_URL) String jiveSignatureURL,
                                   @JsonProperty(TIMESTAMP) String timestamp,
                                   @JsonProperty(JIVE_URL) String jiveUrl,
                                   @JsonProperty(JIVE_SIGNATURE) String jiveSignature,
                                   @JsonProperty(UNINSTALLED) boolean uninstalled,
                                   @JsonProperty(CLIENT_ID) String clientId) {
            this.tenantId = tenantId;
            this.jiveSignatureURL = jiveSignatureURL;
            this.timestamp = timestamp;
            this.jiveUrl = jiveUrl;
            this.jiveSignature = jiveSignature;
            this.uninstalled = uninstalled;
            this.clientId = clientId;
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

    public String getJiveSignatureURL() {
        return jiveSignatureURL;
    }

    public void setJiveSignatureURL(String jiveSignatureURL) {
        this.jiveSignatureURL = jiveSignatureURL;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getJiveSignature() {
        return jiveSignature;
    }

    public void setJiveSignature(String jiveSignature) {
        this.jiveSignature = jiveSignature;
    }

    public boolean isUninstalled() {
        return uninstalled;
    }

    public void setUninstalled(boolean uninstalled) {
        this.uninstalled = uninstalled;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


    @Nonnull
    public SortedMap<String, String> getJiveSignatureMap() {
        // Encode the client-secret
        SortedMap<String, String> sortedMap = Maps.newTreeMap();
        sortedMap.put(CLIENT_ID, clientId);
        sortedMap.put(UNINSTALLED, String.valueOf(uninstalled));
        sortedMap.put(JIVE_SIGNATURE_URL, jiveSignatureURL);
        sortedMap.put(JIVE_URL, jiveUrl);
        sortedMap.put(TENANT_ID, tenantId);
        sortedMap.put(TIMESTAMP, timestamp);
        return sortedMap;
    } // end getJiveSignatureMap

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstanceUnregisterAction that = (InstanceUnregisterAction) o;

        if (uninstalled != that.uninstalled) return false;
        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null) return false;
        if (jiveSignature != null ? !jiveSignature.equals(that.jiveSignature) : that.jiveSignature != null)
            return false;
        if (jiveSignatureURL != null ? !jiveSignatureURL.equals(that.jiveSignatureURL) : that.jiveSignatureURL != null)
            return false;
        if (jiveUrl != null ? !jiveUrl.equals(that.jiveUrl) : that.jiveUrl != null) return false;
        if (tenantId != null ? !tenantId.equals(that.tenantId) : that.tenantId != null) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = timestamp != null ? timestamp.hashCode() : 0;
        result = 31 * result + (jiveUrl != null ? jiveUrl.hashCode() : 0);
        result = 31 * result + (jiveSignatureURL != null ? jiveSignatureURL.hashCode() : 0);
        result = 31 * result + (tenantId != null ? tenantId.hashCode() : 0);
        result = 31 * result + (jiveSignature != null ? jiveSignature.hashCode() : 0);
        result = 31 * result + (uninstalled ? 1 : 0);
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "InstanceUnregisterAction{" +
                "timestamp='" + timestamp + '\'' +
                ", jiveUrl='" + jiveUrl + '\'' +
                ", jiveSignatureURL='" + jiveSignatureURL + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", jiveSignature='" + jiveSignature + '\'' +
                ", uninstalled=" + uninstalled +
                ", clientId='" + clientId + '\'' +
                '}';
    }

} // end class
