package com.jivesoftware.sdk.api.entity;

/**
 * Created by rrutan on 2/3/14.
 */
public interface JiveInstanceProvider extends BaseProvider {

    public JiveInstance getInstanceByTenantId(String tenantId);

    public void remove(JiveInstance jiveInstance) throws JiveInstanceProviderException;

    public void update(JiveInstance jiveInstance) throws JiveInstanceProviderException;

    class JiveInstanceProviderException extends Exception {
        public JiveInstanceProviderException() {
        }

        public JiveInstanceProviderException(String message) {
            super(message);
        }

        public JiveInstanceProviderException(String message, Throwable cause) {
            super(message, cause);
        }

        public JiveInstanceProviderException(Throwable cause) {
            super(cause);
        }

        public JiveInstanceProviderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    } // end inner-class

} // end class
