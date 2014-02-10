/*
 *
 *  * Copyright 2013 Jive Software
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 */

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
