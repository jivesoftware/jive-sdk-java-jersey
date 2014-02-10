package com.jivesoftware.sdk.api.tile.data;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * Created by rrutan on 2/4/14.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class GaugeSection implements Serializable {

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private String label;

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private String color;

    public GaugeSection() {
        label = null;
        color = null;
    } // end constructor

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GaugeSection that = (GaugeSection) o;

        if (color != null ? !color.equals(that.color) : that.color != null) return false;
        if (label != null ? !label.equals(that.label) : that.label != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = label != null ? label.hashCode() : 0;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GaugeSection{" +
                "label='" + label + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

} // end class
