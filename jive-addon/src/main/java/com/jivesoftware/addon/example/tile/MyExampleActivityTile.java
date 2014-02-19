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
import com.jivesoftware.sdk.api.tile.JiveActivityTile;
import com.jivesoftware.sdk.api.tile.data.*;
import com.jivesoftware.sdk.client.JiveClientException;
import com.jivesoftware.sdk.client.JiveTileClient;
import com.jivesoftware.sdk.event.TileInstanceEvent;
import com.jivesoftware.sdk.event.types.tile.TileRegisterSuccess;
import com.jivesoftware.sdk.event.types.tile.TileUnregister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.TimerTask;

/**
 * Created by rrutan on 2/9/14.
 */
public class MyExampleActivityTile extends JiveActivityTile {
    private static final Logger log = LoggerFactory.getLogger(MyExampleGalleryTile.class);

    public static final String NAME = "jersey-example-activity";

    @Inject
    private JiveTileClient tileClient = null;

    @Override
    public String getName() {   return NAME; }

    @Override
    public JiveTileClient getTileClient() { return tileClient; }

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


    class TileTimerTask extends TimerTask {


        private TileInstance tileInstance = null;

        TileTimerTask(TileInstance tileInstance) {
            this.tileInstance = tileInstance;
        } // end constructor

        @Override
        public void run() {
            for (int x=0; x<8; x++) {
                if (log.isDebugEnabled()) { log.debug("pushing data"); }
                /*** PUSH DATA TO TILE ***/
                try {
                    pushActivity(tileInstance, getBogusPush(tileInstance,(x+1)));
                } catch (JiveClientException jce) {
                    log.error("Unable to Post to Tile",jce);
                } // end try/catch

                /*** PAUSE BETWEEN PUSHES TILE ***/
                try {
                    Thread.currentThread().sleep(8000);
                } catch (InterruptedException ie) {
                    /**** WOKE-UP *****/
                } // end try/catch
            } // end for
        } // end run

    } // end class

    private ActivityPushTile getBogusPush(TileInstance tileInstance, int index) {
        ActivityAction action = new ActivityAction();
        action.setName("posted");

        // This object can be used to define the user completing the activity
        // If not defined, user will be the one who installed the add-on
        //ActivityActor actor = new ActivityActor();
        //actor.setName("Test User");
        //actor.setEmail("some.email@test.com");

        ActivityObject object = new ActivityObject();
        object.setType("jersey-example-activity");
        object.setImage("http://placehold.it/102x102");
        object.setUrl("http://developers.jivesoftware.com");
        object.setTitle("My title");
        object.setDescription("Test activity " + index);

        ActivityEntry activity = new ActivityEntry();
        activity.setAction(action);
        //activity.setActor(actor);
        activity.setObject(object);

        ActivityPushTile tileData = new ActivityPushTile();
        tileData.setActivity(activity);
        return tileData;
    }

    private ActivityTile getBogusFetch(TileInstance tileInstance) {
        return getBogusPush(tileInstance,1);
    } // end getBogusFetch
}
