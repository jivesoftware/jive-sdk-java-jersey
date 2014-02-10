package com.jivesoftware.sdk.api.tile.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by rrutan on 2/4/14.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class GaugeTile extends BaseTile implements Serializable {

    public static final String TYPE = "GAUGE";

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private int activeIndex;

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private List<GaugeSection> sections;

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private String status;

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private String message;

    private TileAction action;

    //TODO: ACCORDING TO https://community.jivesoftware.com/docs/DOC-97782, THIS TILE DOESN'T HAVE CONFIG SECTION????
    //@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private Map<String,Object> config;

    public GaugeTile() {
        activeIndex = 0;
        sections = Lists.newArrayList();
        status = null;
        message = null;
        action = null;
        config = Maps.newHashMap();
    } // end constructor


    public void addGaugeSection(GaugeSection section) {
        sections.add(section);
    } // end addGaugeSection

    public void removeGaugeSection(GaugeSection section) {
        sections.remove(section);
    } // end removeGaugeSection

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public List<GaugeSection> getSections() {
        return sections;
    }

    public void setSections(List<GaugeSection> sections) {
        this.sections = sections;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TileAction getAction() {
        return action;
    }

    public void setAction(TileAction action) {
        this.action = action;
    }

    public Map<String, Object> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GaugeTile gaugeTile = (GaugeTile) o;

        if (activeIndex != gaugeTile.activeIndex) return false;
        if (action != null ? !action.equals(gaugeTile.action) : gaugeTile.action != null) return false;
        if (config != null ? !config.equals(gaugeTile.config) : gaugeTile.config != null) return false;
        if (message != null ? !message.equals(gaugeTile.message) : gaugeTile.message != null) return false;
        if (sections != null ? !sections.equals(gaugeTile.sections) : gaugeTile.sections != null) return false;
        if (status != null ? !status.equals(gaugeTile.status) : gaugeTile.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = activeIndex;
        result = 31 * result + (sections != null ? sections.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (config != null ? config.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JiveGaugeTile{" +
                "activeIndex=" + activeIndex +
                ", sections=" + sections +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", action=" + action +
                ", config=" + config +
                '}';
    }

} // end class

