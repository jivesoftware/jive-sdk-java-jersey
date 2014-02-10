package com.jivesoftware.sdk.api.tile.data;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * Created by rrutan on 2/4/14.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class TileAction implements Serializable {

    private String text;
    private String url;
    private String context;

    public TileAction() {
        text = null;
        url = null;
        context = null;
    } // end constructor

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TileAction that = (TileAction) o;

        if (context != null ? !context.equals(that.context) : that.context != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (context != null ? context.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TileAction{" +
                "text='" + text + '\'' +
                ", url='" + url + '\'' +
                ", context='" + context + '\'' +
                '}';
    }

} // end class
