package com.jivesoftware.sdk.event;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by rrutan on 2/8/14.
 */
public abstract class AbstractEvent {

    public static final String ERROR = "error";
    public static final String CONTEXT = "context";

    protected Map<String,Object> data;

    protected AbstractEvent() {
        this.data = Maps.newHashMap();
    } // end constructor

    public void setContext(Object object) { data.put(CONTEXT,object); }
    public Object getContext() {
        if (data.containsKey(CONTEXT)) { return data.get(CONTEXT); }
        return null;
    } // end getContext

    public void setError(Object object) { data.put(ERROR,object); }
    public Object getError() {
        if (data.containsKey(ERROR)) { return data.get(ERROR); }
        return null;
    } // end getError

} // end class
