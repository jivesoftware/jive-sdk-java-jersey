package com.jivesoftware.sdk.api.tile.data;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * Created by rrutan on 2/4/14.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class ListItem implements Serializable {

    private String icon;

    @JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
    private String text;

    private TileAction action;

    private long userID;

    private boolean userIsPartner;

    private long containerID;

    private int containerType;

    private String linkDescription;

    private String linkMoreDescription;

    public ListItem() {
        icon = null;
        text = null;
        action = null;
        userID = -1;
        userIsPartner = false;
        containerID = -1;
        containerType = -1;
        linkDescription = null;
        linkMoreDescription = null;
    } // end constructor

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TileAction getAction() {
        return action;
    }

    public void setAction(TileAction action) {
        this.action = action;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public boolean isUserIsPartner() {
        return userIsPartner;
    }

    public void setUserIsPartner(boolean userIsPartner) {
        this.userIsPartner = userIsPartner;
    }

    public long getContainerID() {
        return containerID;
    }

    public void setContainerID(long containerID) {
        this.containerID = containerID;
    }

    public int getContainerType() {
        return containerType;
    }

    public void setContainerType(int containerType) {
        this.containerType = containerType;
    }

    public String getLinkDescription() {
        return linkDescription;
    }

    public void setLinkDescription(String linkDescription) {
        this.linkDescription = linkDescription;
    }

    public String getLinkMoreDescription() {
        return linkMoreDescription;
    }

    public void setLinkMoreDescription(String linkMoreDescription) {
        this.linkMoreDescription = linkMoreDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListItem listItem = (ListItem) o;

        if (containerID != listItem.containerID) return false;
        if (containerType != listItem.containerType) return false;
        if (userID != listItem.userID) return false;
        if (userIsPartner != listItem.userIsPartner) return false;
        if (action != null ? !action.equals(listItem.action) : listItem.action != null) return false;
        if (icon != null ? !icon.equals(listItem.icon) : listItem.icon != null) return false;
        if (linkDescription != null ? !linkDescription.equals(listItem.linkDescription) : listItem.linkDescription != null)
            return false;
        if (linkMoreDescription != null ? !linkMoreDescription.equals(listItem.linkMoreDescription) : listItem.linkMoreDescription != null)
            return false;
        if (text != null ? !text.equals(listItem.text) : listItem.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = icon != null ? icon.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (int) (userID ^ (userID >>> 32));
        result = 31 * result + (userIsPartner ? 1 : 0);
        result = 31 * result + (int) (containerID ^ (containerID >>> 32));
        result = 31 * result + containerType;
        result = 31 * result + (linkDescription != null ? linkDescription.hashCode() : 0);
        result = 31 * result + (linkMoreDescription != null ? linkMoreDescription.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ListItem{" +
                "icon='" + icon + '\'' +
                ", text='" + text + '\'' +
                ", action=" + action +
                ", userID=" + userID +
                ", userIsPartner=" + userIsPartner +
                ", containerID=" + containerID +
                ", containerType=" + containerType +
                ", linkDescription='" + linkDescription + '\'' +
                ", linkMoreDescription='" + linkMoreDescription + '\'' +
                '}';
    }

}  // end class
