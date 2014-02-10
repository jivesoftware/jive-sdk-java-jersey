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

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by rrutan on 2/6/14.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class HealthMessage implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(HealthMessage.class);

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private String level;

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private String summary;

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private String detail;

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private String fix;

    enum Level {
        debug, info, warn, error
    }

    public HealthMessage() {
        this.level = null;
        this.summary = null;
        this.detail = null;
        this.fix = null;
    } // end constructor

    public String getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level.name();
    } // end setLevel

    public void setLevel(String level) {
        try {
            this.level = Level.valueOf(level).name();
        } catch (IllegalArgumentException iae) {
            if (log.isWarnEnabled()) { log.warn("Invalid HealthMessage.Level["+level+"], defaulting to info");   }
            this.level = Level.info.name();
        } // end try/catch
    } // end setStatus

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getFix() {
        return fix;
    }

    public void setFix(String fix) {
        this.fix = fix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HealthMessage that = (HealthMessage) o;

        if (detail != null ? !detail.equals(that.detail) : that.detail != null) return false;
        if (fix != null ? !fix.equals(that.fix) : that.fix != null) return false;
        if (level != null ? !level.equals(that.level) : that.level != null) return false;
        if (summary != null ? !summary.equals(that.summary) : that.summary != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = level != null ? level.hashCode() : 0;
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (detail != null ? detail.hashCode() : 0);
        result = 31 * result + (fix != null ? fix.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "HealthMessage{" +
                "level='" + level + '\'' +
                ", summary='" + summary + '\'' +
                ", detail='" + detail + '\'' +
                ", fix='" + fix + '\'' +
                '}';
    }

} // end class