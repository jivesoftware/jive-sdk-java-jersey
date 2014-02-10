package com.jivesoftware.sdk.event;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by rrutan on 2/1/14.
 */
public class JiveInstanceEvent extends AbstractEvent implements Serializable {

    public enum Type {
        RegisterSuccess(false),
        RegisterFailed(true),
        Unregister(false);

        private boolean isError = false;

        Type(boolean isError) {
              this.isError = isError;
        } // end constructor

    } // end enum

    private Type type;

    public JiveInstanceEvent() {
        super();
    } // end constructor

    public JiveInstanceEvent(Type type) {
        this();
        this.type = type;
    } // end constructor

    public JiveInstanceEvent(Type type, Map<String,Object> data) {
        this(type);
        this.data = data;
    } // end constructor

    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JiveInstanceEvent that = (JiveInstanceEvent) o;

        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (type != that.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JiveInstanceEvent{" +
                "type=" + type +
                ", data=" + data +
                '}';
    }

} // end class