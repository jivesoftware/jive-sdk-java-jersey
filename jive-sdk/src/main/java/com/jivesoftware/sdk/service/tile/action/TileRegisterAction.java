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

package com.jivesoftware.sdk.service.tile.action;

import com.google.common.base.Objects;
import com.jivesoftware.sdk.api.entity.JiveInstance;
import com.jivesoftware.sdk.utils.JiveSDKUtils;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rrutan on 1/29/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TileRegisterAction {
    public static final String PROPERTY_NAME_TEMP_TOKEN = "code";
    public static final String PROPERTY_NAME_CONFIG_JSON = "config";
    public static final String PROPERTY_NAME_JIVE_PUSH_URL = "url";
    public static final String PROPERTY_NAME_TENANT_ID = "tenantID";
    public static final String PROPERTY_NAME_JIVE_INSTANCE_URL = "jiveUrl";
    public static final String PROPERTY_NAME_ITEM_TYPE = "name";
    public static final String PROPERTY_NAME_TILE_INSTANCE_ID = "id";
    public static final String PROPERTY_PARENT = "parent";
    public static final String PROPERTY_PLACE_URI = "placeUri";
    public static final String PROPERTY_GUID = "guid";

    private String code;
    private Map<String, String> config = new HashMap<String, String>();
    private String jivePushUrl;
    private String jiveUrl;
    private String parent;
    private String guid;
    private String placeUri;
    private String tenantID;
    private String itemType;
    private String tileDefName;
    private String tileInstanceID;

    private String clientId;
    private String clientSecret;
    private JiveInstance instance;

    @JsonCreator
    public TileRegisterAction(@JsonProperty(PROPERTY_NAME_TEMP_TOKEN) String code,
                               @JsonProperty(PROPERTY_NAME_CONFIG_JSON) Map<String, String> config,
                               @JsonProperty(PROPERTY_NAME_JIVE_PUSH_URL) String jivePushUrl,
                               @JsonProperty(PROPERTY_NAME_JIVE_INSTANCE_URL) String jiveInstanceUrl,
                               @JsonProperty(PROPERTY_NAME_TENANT_ID) String tenantID,
                               @JsonProperty(PROPERTY_NAME_TILE_INSTANCE_ID) String tileInstanceID,
                               @JsonProperty(PROPERTY_NAME_ITEM_TYPE) String itemType,
                               @JsonProperty(PROPERTY_PARENT) String parent,
                               @JsonProperty(PROPERTY_GUID) String guid,
                               @JsonProperty(PROPERTY_PLACE_URI) String placeUri
                               ) {
        this.code = code;
        this.config = config;
        this.tileInstanceID = tileInstanceID;

        this.jivePushUrl = jivePushUrl;
        this.jiveUrl = jiveInstanceUrl;
        this.tenantID = tenantID;

        this.tileDefName = itemType;
        this.itemType = itemType;
        this.parent = parent;
        this.guid = guid;
        this.placeUri = placeUri;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPlaceUri() {
        return placeUri;
    }

    public void setPlaceUri(String placeUri) {
        this.placeUri = placeUri;
    }

    public String getGlobalTileInstanceID() {	return tenantID + "_" + this.tileInstanceID;    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }

    public String getJivePushUrl() {
        return jivePushUrl;
    }

    public void setJivePushUrl(String jivePushUrl) {
        this.jivePushUrl = jivePushUrl;
    }

    public String getJiveUrl() {
        return jiveUrl;
    }

    public void setJiveUrl(String jiveUrl) {
        this.jiveUrl = jiveUrl;
    }

    public String getTenantID() {
        return tenantID;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getTileDefName() {
        return tileDefName;
    }

    public void setTileDefName(String tileDefName) {
        this.tileDefName = tileDefName;
    }

    public String getTileInstanceID() {
        return tileInstanceID;
    }

    public void setTileInstanceID(String tileInstanceID) {
        this.tileInstanceID = tileInstanceID;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public JiveInstance getInstance() {
        return instance;
    }

    public void setInstance(JiveInstance instance) {
        this.instance = instance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TileRegisterAction that = (TileRegisterAction) o;

        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null) return false;
        if (clientSecret != null ? !clientSecret.equals(that.clientSecret) : that.clientSecret != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (config != null ? !config.equals(that.config) : that.config != null) return false;
        if (instance != null ? !instance.equals(that.instance) : that.instance != null) return false;
        if (itemType != null ? !itemType.equals(that.itemType) : that.itemType != null) return false;
        if (jivePushUrl != null ? !jivePushUrl.equals(that.jivePushUrl) : that.jivePushUrl != null) return false;
        if (jiveUrl != null ? !jiveUrl.equals(that.jiveUrl) : that.jiveUrl != null) return false;
        if (parent != null ? !parent.equals(that.parent) : that.parent != null) return false;
        if (placeUri != null ? !placeUri.equals(that.parent) : that.placeUri != null) return false;
        if (guid != null ? !guid.equals(that.guid) : that.guid != null) return false;
        if (tenantID != null ? !tenantID.equals(that.tenantID) : that.tenantID != null) return false;
        if (tileDefName != null ? !tileDefName.equals(that.tileDefName) : that.tileDefName != null) return false;
        if (tileInstanceID != null ? !tileInstanceID.equals(that.tileInstanceID) : that.tileInstanceID != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (config != null ? config.hashCode() : 0);
        result = 31 * result + (jivePushUrl != null ? jivePushUrl.hashCode() : 0);
        result = 31 * result + (jiveUrl != null ? jiveUrl.hashCode() : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + (placeUri != null ? placeUri.hashCode() : 0);
        result = 31 * result + (guid != null ? guid.hashCode() : 0);
        result = 31 * result + (tenantID != null ? tenantID.hashCode() : 0);
        result = 31 * result + (itemType != null ? itemType.hashCode() : 0);
        result = 31 * result + (tileDefName != null ? tileDefName.hashCode() : 0);
        result = 31 * result + (tileInstanceID != null ? tileInstanceID.hashCode() : 0);
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        result = 31 * result + (clientSecret != null ? clientSecret.hashCode() : 0);
        result = 31 * result + (instance != null ? instance.hashCode() : 0);
        return result;
    }

    @Override
       public String toString() {
           return Objects.toStringHelper(this)
                   .add(PROPERTY_NAME_TEMP_TOKEN, code)
                   .add(PROPERTY_NAME_CONFIG_JSON, config)
                   .add(PROPERTY_NAME_JIVE_PUSH_URL, jivePushUrl)
                   .add(PROPERTY_PARENT, parent)
                   .add(PROPERTY_PLACE_URI, placeUri)
                   .add(PROPERTY_GUID, guid)
                   .add(PROPERTY_NAME_JIVE_INSTANCE_URL, jiveUrl)
                   .add(PROPERTY_NAME_TENANT_ID, tenantID)
                   .add(PROPERTY_NAME_ITEM_TYPE, itemType)
                   .add("tileDefName", tileDefName)
                   .add("tileInstanceID", tileInstanceID)
                   .add("clientId", clientId)
                   .toString();
       }

} // end class
