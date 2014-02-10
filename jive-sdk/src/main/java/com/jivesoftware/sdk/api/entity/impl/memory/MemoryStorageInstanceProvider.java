package com.jivesoftware.sdk.api.entity.impl.memory;

import com.jivesoftware.sdk.api.entity.StorageInstanceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by rrutan on 2/6/14.
 */
public class MemoryStorageInstanceProvider implements StorageInstanceProvider {
    private static final Logger log = LoggerFactory.getLogger(MemoryStorageInstanceProvider.class);

    private static MemoryStorageInstanceProvider instance = null;

    private MemoryStorageInstanceProvider() {
        if (log.isTraceEnabled()) { log.trace("constructor called..."); }
    } // end constructor

    public static MemoryStorageInstanceProvider getInstance() {
        if (instance == null) {
            instance = new MemoryStorageInstanceProvider();
        }// end if
        return instance;
    } // end getInstance

    @Override
    public void dispose() {
        if (log.isTraceEnabled()) { log.trace("dispose called..."); }
    } // end dispose

} // end class
