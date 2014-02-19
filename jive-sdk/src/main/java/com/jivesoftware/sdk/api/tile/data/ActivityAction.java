package com.jivesoftware.sdk.api.tile.data;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Tile activity action data
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ActivityAction {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
