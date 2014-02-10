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

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by rrutan on 1/29/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TileUnregisterAction {

    public static final String PROPERTY_NAME_ITEM_TYPE = "name";
    public static final String PROPERTY_NAME_JIVE_INSTANCE_URL = "jiveUrl";
    public static final String PROPERTY_NAME_JIVE_PUSH_URL = "url";
    public static final String PROPERTY_GUID = "guid";
    public static final String PROPERTY_NAME_TENANT_ID = "tenantID";
    public static final String PROPERTY_NAME_TILE_INSTANCE_ID = "id";

    private String itemType;
    private String jiveUrl;
    private String url;
    private String guid;
    private String tenantID;
    private String tileInstanceID;

    @JsonCreator
    public TileUnregisterAction(@JsonProperty(PROPERTY_NAME_ITEM_TYPE) String itemType,
                               @JsonProperty(PROPERTY_NAME_JIVE_INSTANCE_URL) String jiveUrl,
                               @JsonProperty(PROPERTY_NAME_JIVE_PUSH_URL) String url,
                               @JsonProperty(PROPERTY_GUID) String guid,
                               @JsonProperty(PROPERTY_NAME_TENANT_ID) String tenantID,
                               @JsonProperty(PROPERTY_NAME_TILE_INSTANCE_ID) String tileInstanceID) {
       this.itemType = itemType;
       this.jiveUrl = jiveUrl;
       this.url = url;
       this.guid = guid;
       this.tenantID = tenantID;
       this.tileInstanceID = tileInstanceID;
    } // end constructor

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getJiveUrl() {
        return jiveUrl;
    }

    public void setJiveUrl(String jiveUrl) {
        this.jiveUrl = jiveUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTenantID() {
        return tenantID;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public String getTileInstanceID() {
        return tileInstanceID;
    }

    public void setTileInstanceID(String tileInstanceID) {
        this.tileInstanceID = tileInstanceID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TileUnregisterAction that = (TileUnregisterAction) o;

        if (guid != null ? !guid.equals(that.guid) : that.guid != null) return false;
        if (itemType != null ? !itemType.equals(that.itemType) : that.itemType != null) return false;
        if (jiveUrl != null ? !jiveUrl.equals(that.jiveUrl) : that.jiveUrl != null) return false;
        if (tenantID != null ? !tenantID.equals(that.tenantID) : that.tenantID != null) return false;
        if (tileInstanceID != null ? !tileInstanceID.equals(that.tileInstanceID) : that.tileInstanceID != null)
            return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = itemType != null ? itemType.hashCode() : 0;
        result = 31 * result + (jiveUrl != null ? jiveUrl.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (guid != null ? guid.hashCode() : 0);
        result = 31 * result + (tenantID != null ? tenantID.hashCode() : 0);
        result = 31 * result + (tileInstanceID != null ? tileInstanceID.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TileUnregisterAction{" +
                "itemType='" + itemType + '\'' +
                ", jiveUrl='" + jiveUrl + '\'' +
                ", url='" + url + '\'' +
                ", guid='" + guid + '\'' +
                ", tenantID='" + tenantID + '\'' +
                ", tileInstanceID='" + tileInstanceID + '\'' +
                '}';
    }

} // end class



