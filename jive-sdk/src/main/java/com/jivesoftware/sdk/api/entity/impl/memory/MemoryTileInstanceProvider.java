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

import com.jivesoftware.sdk.api.entity.TileInstance;
import com.jivesoftware.sdk.api.entity.TileInstanceProvider;
import com.jivesoftware.sdk.event.JiveInstanceEvent;
import com.jivesoftware.sdk.event.types.instance.InstanceUnregister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;

/**
 * Created by rrutan on 2/3/14.
 */
@Singleton
public class MemoryTileInstanceProvider implements TileInstanceProvider {
    private static final Logger log = LoggerFactory.getLogger(MemoryTileInstanceProvider.class);

    private static MemoryTileInstanceProvider instance = null;

    private MemoryTileInstanceProvider() {
        if (log.isTraceEnabled()) { log.trace("constructor called..."); }
    } // end constructor

    public static MemoryTileInstanceProvider getInstance() {
        if (instance == null) {
            instance = new MemoryTileInstanceProvider();
        }// end if
        return instance;
    } // end getInstance

//    @Inject @TileUnregister
//    Event <TileInstanceEvent> tileInstanceUnregister;
//
//    @Inject @TileRegisterSuccess
//    Event <TileInstanceEvent> tileInstanceRegisterSuccess;
//
//    @Inject @TileRegisterFailed
//    Event <TileInstanceEvent> tileInstanceRegisterFailed;
//
//    private void fireTileInstanceEvent(TileInstanceEvent.Type type, TileInstance context) {
//        if (log.isDebugEnabled()) { log.debug("Firing[TileInstanceEvent]["+type+"] ..."); }
//        Map<String, Object> data = Maps.newHashMap();
//        data.put(JiveInstanceEvent.CONTEXT, context);
//
//        TileInstanceEvent event = new TileInstanceEvent(type,context.getItemType(),data);
//
//        switch (type) {
//            case RegisterSuccess :   { tileInstanceRegisterSuccess.fire(event); break; }
//            case RegisterFailed :    { tileInstanceRegisterFailed.fire(event);  break; }
//            case Unregister :        { tileInstanceUnregister.fire(event);      break; }
//        } // end switch
//    } // end fireTileInstanceEvent

    @Override
    public TileInstance getTileInstanceByPushURL(String url) {
        if (log.isDebugEnabled()) { log.debug("getTileInstanceByPushURL ["+url+"] ..."); }
        return MemoryTileInstanceStore.getInstance().getMap().get(url);
    } // end getTileByPushURL

    @Override
    public void remove(TileInstance tileInstance) throws TileInstanceProviderException {
        if (tileInstance == null) {
            if (log.isWarnEnabled()) { log.warn("Unable to Remove [null] Tile Instance, ignoring ...");  }
            //TODO:  LOOP BACK FLOWS VALIDATION (simple return vs. Exception throw)
            return;
        } // end if

        if (log.isDebugEnabled()) { log.debug("remove ["+ tileInstance.getJivePushUrl()+"] ..."); }
        MemoryTileInstanceStore.getInstance().getMap().remove(tileInstance.getJivePushUrl());
    } // end remove

    @Override
    public void update(TileInstance tileInstance) throws TileInstanceProviderException {
        if (tileInstance == null) {
            if (log.isWarnEnabled()) { log.warn("Unable to Update [null] Tile Instance, ignoring ...");  }
            //TODO:  LOOP BACK FLOWS VALIDATION (simple return vs. Exception throw)
            return;
        } // end if

        if (log.isDebugEnabled()) { log.debug("update ["+ tileInstance.getJivePushUrl()+"] ..."); }
        MemoryTileInstanceStore.getInstance().getMap().put(tileInstance.getJivePushUrl(),tileInstance);
    } // end update

    private void onJiveInstanceEvent(@Observes @InstanceUnregister JiveInstanceEvent event) {
         if (log.isDebugEnabled()) { log.debug("JiveInstanceEvent["+event.getType()+"] received ..."); }

         //TODO: thisProvider (list all entries with the same tenantID)

    } // end onJiveInstanceEvent

    @Override
    public void dispose() {
        if (log.isTraceEnabled()) { log.trace("dispose called..."); }
    } // end dispose

} // end class
