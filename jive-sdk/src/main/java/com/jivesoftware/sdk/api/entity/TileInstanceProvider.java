package com.jivesoftware.sdk.api.entity;

/**
 * Created by rrutan on 2/3/14.
 */
public interface TileInstanceProvider extends BaseProvider {

    public TileInstance getTileInstanceByPushURL(String url);

    public void remove(TileInstance tileInstance) throws TileInstanceProviderException;

    public void update(TileInstance tileInstance) throws TileInstanceProviderException;

    class TileInstanceProviderException extends Exception {
        public TileInstanceProviderException() {
        }

        public TileInstanceProviderException(String message) {
            super(message);
        }

        public TileInstanceProviderException(String message, Throwable cause) {
            super(message, cause);
        }

        public TileInstanceProviderException(Throwable cause) {
            super(cause);
        }

        public TileInstanceProviderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    } // end constructor
} // end interface
