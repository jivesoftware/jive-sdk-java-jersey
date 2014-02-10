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

package com.jivesoftware.sdk.api.health.data;

import com.google.common.collect.Lists;
import com.jivesoftware.sdk.utils.DateTimeUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by rrutan on 2/6/14.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class HealthResource implements Serializable {

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private String name;

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private String url;

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private String status;

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private String lastUpdate;

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private List<HealthMessage> messages;

    public HealthResource() {
        this.name = null;
        this.url = null;
        this.status = null;
        this.lastUpdate = null;
        this.messages = Lists.newArrayList();
    } // end constructor

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = DateTimeUtils.dateToIso(lastUpdate);
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<HealthMessage> getMessages() {
        return messages;
    }

    public void addMessage(HealthMessage message) {
        this.messages.add(message);
    }

    public void removeMessage(HealthMessage message) {
        this.messages.remove(message);
    }

    public void setMessages(List<HealthMessage> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HealthResource that = (HealthResource) o;

        if (lastUpdate != null ? !lastUpdate.equals(that.lastUpdate) : that.lastUpdate != null) return false;
        if (messages != null ? !messages.equals(that.messages) : that.messages != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        result = 31 * result + (messages != null ? messages.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HealthResource{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", status='" + status + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                ", messages=" + messages +
                '}';
    }

} // end class
