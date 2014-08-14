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
import com.jivesoftware.sdk.api.entity.StorageInstanceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by rrutan on 2/6/14.
 */
public class MemoryStorageInstanceProvider implements StorageInstanceProvider {
    private static final Logger log = LoggerFactory.getLogger(MemoryStorageInstanceProvider.class);

    private Map<String,StorageInstance> memoryStorageInstanceStore;

    public MemoryStorageInstanceProvider() {
        if (log.isTraceEnabled()) { log.trace("constructor called..."); }
        memoryStorageInstanceStore = Maps.newConcurrentMap();
    } // end constructor

    @Override
    public void init() throws StorageInstanceProviderException {
        if (log.isTraceEnabled()) { log.trace("init called..."); }
    } // end init

    @Override
    public void dispose() {
        if (log.isTraceEnabled()) { log.trace("dispose called..."); }
    } // end dispose

} // end class
