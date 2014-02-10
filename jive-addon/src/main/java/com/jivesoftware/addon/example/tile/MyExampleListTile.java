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

package com.jivesoftware.addon.example.tile;

import com.jivesoftware.sdk.api.entity.TileInstance;
import com.jivesoftware.sdk.api.tile.JiveListTile;
import com.jivesoftware.sdk.api.tile.data.ListItem;
import com.jivesoftware.sdk.api.tile.data.ListTile;
import com.jivesoftware.sdk.client.JiveClientException;
import com.jivesoftware.sdk.client.JiveTileClient;
import com.jivesoftware.sdk.event.TileInstanceEvent;
import com.jivesoftware.sdk.event.types.tile.TileRegisterSuccess;
import com.jivesoftware.sdk.event.types.tile.TileUnregister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;
import java.util.TimerTask;

/**
 * Created by rrutan on 2/4/14.
 */
@Singleton
public class MyExampleListTile extends JiveListTile {
    private static final Logger log = LoggerFactory.getLogger(MyExampleListTile.class);

    public static final String NAME = "jersey-example-list";

    //TODO: INJECT CONFIGURATION WITH FACTORY THAT USES THE "Injectables List to Introspect and CREATE RULE
    // INJECTING PROPERLY TYPED/SCOPE CONFIGURATIONS FROM THE PROPERTY

    @Inject
    private JiveTileClient tileClient = null;

    @Override
    public String getName() {   return NAME; }

    @Override
    protected JiveTileClient getTileClient() { return tileClient; }

    public void onRegisterSuccessEvent(@Observes @TileRegisterSuccess TileInstanceEvent event) {
        if (!event.getTileName().equals(getName())) {
            if (log.isTraceEnabled()) { log.trace("onRegisterSuccessEvent[name="+event.getTileName()+"], ignoring ..."); }
            return;
        } // end if

        if (log.isDebugEnabled()) { log.debug("onRegisterSuccessEvent[name="+getName()+"] ..."); }
        TileInstance tileInstance = (TileInstance)event.getContext();

        new Thread(new TileTimerTask(tileInstance)).start();
    } // end onReadyEvent

    public void onUnregisterEvent(@Observes @TileUnregister TileInstanceEvent event) {
        if (!event.getTileName().equals(getName())) {
            if (log.isTraceEnabled()) { log.trace("onUnregisterEvent[name="+event.getTileName()+"], ignoring ..."); }
            return;
        } // end if

        if (log.isDebugEnabled()) { log.debug("onUnregisterEvent[name="+getName()+"] ..."); }

    } // end onUnregisterEvent

    /******
     *
     ******/
    class TileTimerTask extends TimerTask {


        private TileInstance tileInstance = null;

        TileTimerTask(TileInstance tileInstance) {
            this.tileInstance = tileInstance;
        } // end constructor

        @Override
        public void run() {
            for (int x=0; x<12; x++) {
                if (log.isDebugEnabled()) { log.debug("pushing data"); }

                /*** PUSH DATA TO TILE ***/
                try {
                    pushData(tileInstance, getBogusPush(tileInstance));
                } catch (JiveClientException jce) {
                    log.error("Unable to Post to Tile",jce);
                } // end try/catch

                /*** PAUSE BETWEEN PUSHES TILE ***/
                try {
                    Thread.currentThread().sleep(5000);
                } catch (InterruptedException ie) {
                    /**** WOKE-UP *****/
                } // end try/catch
            } // end for
        } // end run

    } // end class

    private ListTile getBogusPush(TileInstance tileInstance) {
        ListTile tileData = new ListTile();
        tileData.setTitle("Howdy");

        if (!tileData.getConfig().containsKey("listStyle")) {
           tileData.getConfig().put("listStyle","peopleList");
        } // end if

        String startSequence = tileInstance.getConfig().get("startSequence");
        tileData.getConfig().put("startSequence",startSequence);

        ListItem item = new ListItem();
        item.setIcon("http://jivedev.ryanrutan.com:18099/images/extension-16.png");
//        item.setContainerID(1007);
//        item.setContainerType(700);
//        item.setUserID(2017);
//        item.setUserIsPartner(false);
        item.setLinkDescription("Test Description");
        item.setLinkMoreDescription("More Description");
        item.setText("" + new Date().getTime() + " : "+startSequence);
        tileData.addListItem(item);

        return tileData;
    } // end getBogusPush

    private ListTile getBogusFetch(TileInstance tileInstance) {
        return getBogusPush(tileInstance);
    } // end getBogusFetch

} // end class
