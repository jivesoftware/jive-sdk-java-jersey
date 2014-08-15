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

import com.google.common.collect.Lists;
import com.jivesoftware.sdk.api.entity.TileInstance;
import com.jivesoftware.sdk.api.tile.JiveCalendarTile;
import com.jivesoftware.sdk.api.tile.data.CalendarEvent;
import com.jivesoftware.sdk.api.tile.data.CalendarTile;
import com.jivesoftware.sdk.api.tile.data.TileAction;
import com.jivesoftware.sdk.client.JiveClientException;
import com.jivesoftware.sdk.event.TileInstanceEvent;
import com.jivesoftware.sdk.event.TileInstanceEventListener;
import com.jivesoftware.sdk.utils.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by rrutan on 2/4/14.
 */
@Singleton
public class MyExampleCalendarTile extends JiveCalendarTile implements TileInstanceEventListener {
    private static final Logger log = LoggerFactory.getLogger(MyExampleCalendarTile.class);

    public static final String NAME = "jersey-example-calendar";

    @Override
    public String getName() {   return NAME; }

    @Override
    public boolean accepts(TileInstanceEvent event) {
        boolean accept = (event.getTileName().equals(getName()) &&
                (
                        TileInstanceEvent.Type.RegisterSuccess.equals(event.getType()) ||
                        TileInstanceEvent.Type.Unregister.equals(event.getType())
                )
        );
        if (log.isTraceEnabled()) { log.trace("accepts[name="+event.getTileName()+"], type=["+event.getType()+"] => "+accept); }
        return accept;
    } // end accepts

    @Override
    public void process(TileInstanceEvent event) throws TileInstanceEventException {
        if (log.isDebugEnabled()) { log.debug("process[name=" + getName() + "] ...");   }
        if (event.getType().equals(TileInstanceEvent.Type.RegisterSuccess)) {
            onRegisterEvent(event);
        } else if (event.getType().equals(TileInstanceEvent.Type.Unregister)) {
            onUnregisterEvent(event);
        } // end if
    } // end process

    private void onRegisterEvent(TileInstanceEvent event) {
        TileInstance tileInstance = (TileInstance)event.getContext();

        new Thread(new TileTimerTask(tileInstance)).start();
    } // end onRegisterEvent

    private void onUnregisterEvent(TileInstanceEvent event) {
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
            for (int x=0; x<8; x++) {
                if (log.isDebugEnabled()) { log.debug("pushing data"); }

                /*** PUSH DATA TO TILE ***/
                try {
                    pushData(tileInstance, getBogusPush(tileInstance,(x+1)));
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

    private CalendarTile getBogusPush(TileInstance tileInstance, int index) {
        String section = tileInstance.getConfig().get("section");

        CalendarTile calendarTile = new CalendarTile();
        calendarTile.setTitle("Example Calendar - "+new Date().getTime());

        calendarTile.setLocationDisplay(false);

        List<CalendarEvent> events = Lists.newArrayList();

        CalendarEvent event = new CalendarEvent();
        event.setStart(DateTimeUtils.dateToTileIso(new Date(System.currentTimeMillis()+(1000*60*60*24*(index+5)))));
        event.setDuration("01:00:00");
        event.setLocation("Somewhere ["+section+"]");
        event.setTitle("Event Title");
        event.setDescription("Example Description");

        TileAction action = new TileAction();
        action.setText("Developers Community");
        action.setUrl("https://developers.jivesoftware.com");
        event.setAction(action);

        events.add(event);

        calendarTile.setEvents(events);

        return calendarTile;

    } // end getBogusPush

    private CalendarTile getBogusFetch(TileInstance tileInstance) {
        return getBogusPush(tileInstance,1);
    } // end getBogusFetch

} // end class
