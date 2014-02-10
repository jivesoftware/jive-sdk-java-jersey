package com.jivesoftware.sdk.event;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by rrutan on 2/1/14.
 */
public class BootStrapEvent extends AbstractEvent implements Serializable {

    public enum Type {

        //TODO: NEED TO CONFIRM EVENTS
        Starting(false),
        Started(false),
        Stopping(false),
        Stopped(false);

        private boolean isError = false;

        Type(boolean isError) {
              this.isError = isError;
        } // end constructor

    } // end class


    private Type type;

    public BootStrapEvent() {};

    public BootStrapEvent(Type type, Map<String,Object> data) {
        this.type = type;
        this.data = data;
    } // end constructor

    public Type getType() {
        return type;
    } // end getType

    public void setType(Type type) {
        this.type = type;
    }

    public Map<String, Object> getData() {
        return data;
    }
    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BootStrapEvent that = (BootStrapEvent) o;

        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (type != that.type) return false;

        return true;
    } // end equals

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BootStrapEvent{" +
                "type=" + type +
                ", data=" + data +
                '}';
    }

} // end class
