package com.jivesoftware.sdk.api.entity.impl.memory;

import com.google.common.collect.Maps;
import com.jivesoftware.sdk.api.entity.StorageInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Map;

/**
 * Created by rrutan on 2/4/14.
 */
@Singleton
public class MemoryStorageInstanceStore {
    private static final Logger log = LoggerFactory.getLogger(MemoryStorageInstanceStore.class);

    private Map<String,StorageInstance> storageInstanceMap = null;

    private static MemoryStorageInstanceStore instance = null;

    private MemoryStorageInstanceStore() {
        storageInstanceMap = Maps.newConcurrentMap();
    } // end constructor

    public static MemoryStorageInstanceStore getInstance() {
        if (instance == null) {
            instance = new MemoryStorageInstanceStore();
        } // end if
        return instance;
    } // end getInstance

    protected Map<String,StorageInstance> getMap() { return storageInstanceMap; }

}
