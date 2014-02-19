package com.jivesoftware.sdk.api.tile.data;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Tile activity actor data
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ActivityActor {
    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
