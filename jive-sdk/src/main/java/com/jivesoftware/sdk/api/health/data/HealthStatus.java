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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by rrutan on 2/6/14.
 */

@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class HealthStatus implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(HealthStatus.class);

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private String status;

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private String lastUpdate;

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private List<HealthMessage> messages;

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private List<HealthResource> resources;

    enum Status {
        ok, fault, unknown, intermittent, maintenance;
    }

    public HealthStatus() {
        status = null;
        lastUpdate = null;
        messages = Lists.newArrayList();
        resources = Lists.newArrayList();
    } // end constructor

    public String getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status.name();
    }

    public void setStatus(String status) {
        try {
            this.status = Status.valueOf(status).name();
        } catch (IllegalArgumentException iae) {
            if (log.isWarnEnabled()) { log.warn("Invalid HealthStatus.Status["+status+"], defaulting to unknown");   }
            this.status = Status.unknown.name();
        } // end try/catch
    } // end setStatus

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

    public List<HealthResource> getResources() {
        return resources;
    }

    public void addResource(HealthResource resource) {
        this.resources.add(resource);
    }

    public void removeResource(HealthResource resource) {
        this.resources.remove(resource);
    }

    public void setResources(List<HealthResource> resources) {
        this.resources = resources;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HealthStatus that = (HealthStatus) o;

        if (lastUpdate != null ? !lastUpdate.equals(that.lastUpdate) : that.lastUpdate != null) return false;
        if (messages != null ? !messages.equals(that.messages) : that.messages != null) return false;
        if (resources != null ? !resources.equals(that.resources) : that.resources != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0);
        result = 31 * result + (messages != null ? messages.hashCode() : 0);
        result = 31 * result + (resources != null ? resources.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HealthStatus{" +
                "status='" + status + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                ", messages=" + messages +
                ", resources=" + resources +
                '}';
    }

} // end class
