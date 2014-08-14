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
import com.jivesoftware.sdk.api.entity.TileInstance;
import com.jivesoftware.sdk.api.entity.TileInstanceProvider;
import com.jivesoftware.sdk.event.TileInstanceEvent;
import com.jivesoftware.sdk.event.TileInstanceEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Map;

/**
 * Created by rrutan on 2/3/14.
 */
@Singleton
public class MemoryTileInstanceProvider implements TileInstanceProvider, TileInstanceEventListener {
    private static final Logger log = LoggerFactory.getLogger(MemoryTileInstanceProvider.class);

    private Map<String,TileInstance> memoryTileInstanceStore;

    public MemoryTileInstanceProvider() {
        if (log.isTraceEnabled()) { log.trace("constructor called..."); }
        memoryTileInstanceStore = Maps.newConcurrentMap();
    } // end constructor

    @Override
    public void init() throws TileInstanceProviderException {
        if (log.isTraceEnabled()) { log.trace("init called..."); }
    } // end init


    @Override
    public TileInstance getTileInstanceByPushURL(String url) {
        if (log.isDebugEnabled()) { log.debug("getTileInstanceByPushURL ["+url+"] ..."); }
        return memoryTileInstanceStore.get(url);
    } // end getTileByPushURL

    @Override
    public void remove(TileInstance tileInstance) throws TileInstanceProviderException {
        if (tileInstance == null) {
            if (log.isWarnEnabled()) { log.warn("Unable to Remove [null] Tile Instance, ignoring ...");  }
            //TODO:  LOOP BACK FLOWS VALIDATION (simple return vs. Exception throw)
            return;
        } // end if

        if (log.isDebugEnabled()) { log.debug("remove ["+ tileInstance.getJivePushUrl()+"] ..."); }
        memoryTileInstanceStore.remove(tileInstance.getJivePushUrl());
    } // end remove

    @Override
    public void update(TileInstance tileInstance) throws TileInstanceProviderException {
        if (tileInstance == null) {
            if (log.isWarnEnabled()) { log.warn("Unable to Update [null] Tile Instance, ignoring ...");  }
            //TODO:  LOOP BACK FLOWS VALIDATION (simple return vs. Exception throw)
            return;
        } // end if

        if (log.isDebugEnabled()) { log.debug("update ["+ tileInstance.getJivePushUrl()+"] ..."); }
        memoryTileInstanceStore.put(tileInstance.getJivePushUrl(), tileInstance);
    } // end update

    @Override
    public boolean accepts(TileInstanceEvent event) {
        if (log.isTraceEnabled()) { log.trace("accepts event["+event.getTileName()+","+event.getType()+"] ..."); }
        boolean accept = TileInstanceEvent.Type.Unregister.equals(event.getType());
        return accept;
    } // end accepts

    @Override
    public void process(TileInstanceEvent event) throws TileInstanceEventException {
         if (log.isDebugEnabled()) { log.debug("JiveInstanceEvent["+event.getType()+"] received ..."); }

         //TODO: thisProvider (list all entries with the same tenantID)

    } // end onJiveInstanceEvent

    @Override
    public void dispose() {
        if (log.isTraceEnabled()) { log.trace("dispose called..."); }
    } // end dispose


} // end class
