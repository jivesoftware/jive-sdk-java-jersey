package com.jivesoftware.sdk.api.entity.impl.memory;

import com.google.common.collect.Maps;
import com.jivesoftware.sdk.api.entity.TileInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Map;

/**
 * Created by rrutan on 2/4/14.
 */
@Singleton
public class MemoryTileInstanceStore {
    private static final Logger log = LoggerFactory.getLogger(MemoryTileInstanceStore.class);

    private Map<String,TileInstance> tileInstanceMap = null;

    private static MemoryTileInstanceStore instance = null;

    private MemoryTileInstanceStore() {
        tileInstanceMap = Maps.newConcurrentMap();
    } // end constructor

    public static MemoryTileInstanceStore getInstance() {
        if (instance == null) {
            instance = new MemoryTileInstanceStore();
        } // end if
        return instance;
    } // end getInstance

    protected Map<String,TileInstance> getMap() { return tileInstanceMap; }

}
