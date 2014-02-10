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
public class TableTile extends BaseTile implements Serializable {

    public static final String TYPE = "TABLE";

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private String title;

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private List<TableRow> contents;

    private TileAction action;

    //TODO: ACCORDING TO https://community.jivesoftware.com/docs/DOC-97757, THIS TILE DOESN'T HAVE CONFIG SECTION????
    //@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private Map<String,Object> config;

    public TableTile() {
        title = null;
        contents = Lists.newArrayList();
        config = Maps.newHashMap();
    } // end constructor

    public void addTableRow(TableRow row) {
        contents.add(row);
    } // end addTableRow

    public void removeTableRow(TableRow row) {
        contents.remove(row);
    } // end removeTableRow

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TableRow> getContents() {
        return contents;
    }

    public void setContents(List<TableRow> contents) {
        this.contents = contents;
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

        TableTile tableTile = (TableTile) o;

        if (action != null ? !action.equals(tableTile.action) : tableTile.action != null) return false;
        if (config != null ? !config.equals(tableTile.config) : tableTile.config != null) return false;
        if (contents != null ? !contents.equals(tableTile.contents) : tableTile.contents != null) return false;
        if (title != null ? !title.equals(tableTile.title) : tableTile.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (contents != null ? contents.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (config != null ? config.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "TableTile{" +
                "title='" + title + '\'' +
                ", contents=" + contents +
                ", action=" + action +
                ", config=" + config +
                '}';
    }

} // end class
