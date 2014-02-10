package com.jivesoftware.sdk.client;

/**
 * Created by rrutan on 2/9/14.
 */
public class JiveClientException extends Exception {

    protected Object data;
    protected Object context;
    protected Class dataClass;

    public JiveClientException() {
    }

    public JiveClientException(String message) {
        super(message);
    }

    public JiveClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public JiveClientException(Throwable cause) {
        super(cause);
    }

    public JiveClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static JiveClientException buildException(String message, Throwable throwable, Object context, Object data, Class dataClass) {
        JiveClientException exception = new JiveClientException(message,throwable);
        exception.setContext(context);
        exception.setData(data);
        exception.setDataClass(dataClass);
        return exception;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getContext() {
        return context;
    }

    public void setContext(Object context) {
        this.context = context;
    }

    public Class getDataClass() {
        return dataClass;
    }

    public void setDataClass(Class dataClass) {
        this.dataClass = dataClass;
    }
} // end class
