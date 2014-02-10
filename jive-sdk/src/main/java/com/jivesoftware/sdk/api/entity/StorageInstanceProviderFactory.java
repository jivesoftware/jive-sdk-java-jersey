package com.jivesoftware.sdk.api.entity;

import com.jivesoftware.sdk.JiveAddOnApplication;
import org.glassfish.hk2.api.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

/**
 * Created by rrutan on 2/6/14.
 */
@Singleton
public class StorageInstanceProviderFactory implements Factory<StorageInstanceProvider> {
    private static final Logger log = LoggerFactory.getLogger(StorageInstanceProviderFactory.class);

    @Context
    private Application application;

    public StorageInstanceProviderFactory() {
        if (log.isTraceEnabled()) { log.trace("constructor called..."); }
    } // end class

    @Override
    public StorageInstanceProvider provide() {
        log.debug("provide called...");
        if (application != null) {

            if (application instanceof JiveAddOnApplication) {
                log.debug("Found Application, getting storageInstanceProvider");
                return ((JiveAddOnApplication)application).getStorageInstanceProvider();
            } else {
                log.warn("Unable to locate storageInstanceProvider, application is not a JiveAddOnApplication");
            } // end if

        } // end if

        log.debug("application is null");

        return null;
    } // end provide

    @Override
    public void dispose(StorageInstanceProvider instance) {
        log.debug("dispose called...");
        if (instance != null) {
            instance.dispose();
        } // end if
        instance = null;
    } // end dispose

} // end class