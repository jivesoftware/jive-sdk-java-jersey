package com.jivesoftware.sdk.api.entity.impl.memory;

import com.jivesoftware.sdk.api.entity.AddOnConfigProvider;
import com.jivesoftware.sdk.config.JiveAddOnConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

/**
 * Created by rrutan on 2/6/14.
 */
@Singleton
public class MemoryAddOnConfigProvider implements AddOnConfigProvider {
    private static final Logger log = LoggerFactory.getLogger(MemoryAddOnConfigProvider.class);

    private static MemoryAddOnConfigProvider instance = null;

    private JiveAddOnConfig jiveAddOnConfig = null;

    private MemoryAddOnConfigProvider() {
        if (log.isTraceEnabled()) { log.trace("constructor called..."); }
    } // end constructor

    public static MemoryAddOnConfigProvider getInstance() {
        if (instance == null) {
            instance = new MemoryAddOnConfigProvider();
        }// end if
        return instance;
    } // end getInstance

    @Override
    public JiveAddOnConfig getConfig() {
        if (jiveAddOnConfig == null) {
            jiveAddOnConfig = new JiveAddOnConfig();
        } // end if
        return jiveAddOnConfig;
    } // end getConfig

    @Override
    public void dispose() {
        if (log.isTraceEnabled()) { log.trace("dispose called..."); }
    } // end dispose

} // end close
