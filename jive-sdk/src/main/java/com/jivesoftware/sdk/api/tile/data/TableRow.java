package com.jivesoftware.sdk.api.tile.data;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * Created by rrutan on 2/4/14.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class TableRow implements Serializable {

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private String name;

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private String value;

    private String url;

    public TableRow() {
        name = null;
        value = null;
        url = null;
    } // end constructor

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TableRow tableRow = (TableRow) o;

        if (!name.equals(tableRow.name)) return false;
        if (!url.equals(tableRow.url)) return false;
        if (!value.equals(tableRow.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + value.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TableRow{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

} // end class
