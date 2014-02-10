package com.jivesoftware.sdk.api.entity;

import com.jivesoftware.sdk.JiveAddOnApplication;
import org.glassfish.hk2.api.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

/**
 * Created by rrutan on 2/6/14.
 */
public class HealthStatusProviderFactory implements Factory<HealthStatusProvider> {
    private static final Logger log = LoggerFactory.getLogger(HealthStatusProviderFactory.class);

    @Context
    private Application application;

    public HealthStatusProviderFactory() {
        if (log.isTraceEnabled()) { log.trace("constructor called..."); }
    } // end class

    @Override
    public HealthStatusProvider provide() {
        log.debug("provide called...");
        if (application != null) {

            if (application instanceof JiveAddOnApplication) {
                log.debug("Found Application, getting healthStatusProvider");
                return ((JiveAddOnApplication)application).getHealthStatusProvider();
            } else {
                log.warn("Unable to locate healthStatusProvider, application is not a JiveAddOnApplication");
            } // end if

        } // end if

        log.debug("application is null");

        return null;
    } // end provide

    @Override
    public void dispose(HealthStatusProvider instance) {
        log.debug("dispose called...");
        if (instance != null) {
            instance.dispose();
        } // end if
        instance = null;
    } // end dispose

} // end class
