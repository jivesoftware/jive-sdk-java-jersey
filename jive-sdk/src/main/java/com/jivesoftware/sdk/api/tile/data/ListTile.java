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
public class ListTile extends BaseTile implements Serializable {
    public static final String TYPE = "LIST";

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private String title;

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private List<ListItem> contents;

    private TileAction action;

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private Map<String,Object> config;

    public ListTile() {
      contents = Lists.newArrayList();
      config = Maps.newHashMap();
      action = null;

      //**** NEEDED FOR TILE SPEC AT-LEAST 1 LIST STYLE *****
      setContentListStyle();
    } // end class

    public void addListItem(ListItem item) {
        contents.add(item);
    } // end addListItem

    public void removeListItem(ListItem item) {
        contents.remove(item);
    } // end removeListItem

    public void setContentListStyle() {
        config.put("listStyle","contentList");
    }

    public void setPeopleListStyle() {
        config.put("listStyle","peopleList");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ListItem> getContents() {
        return contents;
    }

    public void setContents(List<ListItem> contents) {
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

        ListTile listTile = (ListTile) o;

        if (action != null ? !action.equals(listTile.action) : listTile.action != null) return false;
        if (config != null ? !config.equals(listTile.config) : listTile.config != null) return false;
        if (contents != null ? !contents.equals(listTile.contents) : listTile.contents != null) return false;
        if (title != null ? !title.equals(listTile.title) : listTile.title != null) return false;

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
        return "ListTile{" +
                "title='" + title + '\'' +
                ", contents=" + contents +
                ", action=" + action +
                ", config=" + config +
                '}';
    }

} // end class

