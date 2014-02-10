package com.jivesoftware.sdk.api.entity.impl.memory;

import com.google.common.collect.Maps;
import com.jivesoftware.sdk.api.entity.JiveInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Map;

/**
 * Created by rrutan on 2/4/14.
 */
@Singleton
public class MemoryJiveInstanceStore {
    private static final Logger log = LoggerFactory.getLogger(MemoryJiveInstanceStore.class);

    private Map<String,JiveInstance> jiveInstanceMap = null;

    private static MemoryJiveInstanceStore instance = null;

    private MemoryJiveInstanceStore() {
        jiveInstanceMap = Maps.newConcurrentMap();
    } // end constructor

    public static MemoryJiveInstanceStore getInstance() {
        if (instance == null) {
            instance = new MemoryJiveInstanceStore();
        } // end if
        return instance;
    } // end getInstance

    protected Map<String,JiveInstance> getMap() { return jiveInstanceMap; }

}
