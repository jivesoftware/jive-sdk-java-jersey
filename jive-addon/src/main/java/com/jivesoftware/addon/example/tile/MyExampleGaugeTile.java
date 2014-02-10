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
import com.jivesoftware.sdk.api.tile.JiveGaugeTile;
import com.jivesoftware.sdk.api.tile.data.GaugeSection;
import com.jivesoftware.sdk.api.tile.data.GaugeTile;
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
import java.util.List;
import java.util.TimerTask;

/**
 * Created by rrutan on 2/4/14.
 */
@Singleton
public class MyExampleGaugeTile extends JiveGaugeTile {
    private static final Logger log = LoggerFactory.getLogger(MyExampleGaugeTile.class);

    public static final String NAME = "jersey-example-gauge";

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
            for (int x=0; x<10; x++) {
                if (log.isDebugEnabled()) { log.debug("pushing data"); }

                /*** PUSH DATA TO TILE ***/
                try {
                    pushData(tileInstance, getBogusPush(tileInstance,(x % 5)));
                } catch (JiveClientException jce) {
                    log.error("Unable to Post to Tile",jce);
                } // end try/catch

                /*** PAUSE BETWEEN PUSHES TILE ***/
                try {
                    Thread.currentThread().sleep(6000);
                } catch (InterruptedException ie) {
                    /**** WOKE-UP *****/
                } // end try/catch
            } // end for
        } // end run

    } // end class

    private GaugeTile getBogusPush(TileInstance tileInstance, int index) {
        GaugeTile gaugeTile = new GaugeTile();

        gaugeTile.setMessage("Jive SDK (Java) - Jersey - Example Gauge Tile");

        List<GaugeSection> sections = Lists.newArrayList();
        GaugeSection s1 = new GaugeSection();
        s1.setColor("#ff0000");
        s1.setLabel("Hot");

        GaugeSection s2 = new GaugeSection();
        s2.setColor("#ffb6c1");
        s2.setLabel("Warm");

        GaugeSection s3 = new GaugeSection();
        s3.setColor("#cecece");
        s3.setLabel("Unknown");

        GaugeSection s4 = new GaugeSection();
        s4.setColor("#b0c4de");
        s4.setLabel("Cool");

        GaugeSection s5 = new GaugeSection();
        s5.setColor("#4169e1");
        s5.setLabel("Cold");

        sections.add(s1);
        sections.add(s2);
        sections.add(s3);
        sections.add(s4);
        sections.add(s5);

        gaugeTile.setSections(sections);
        gaugeTile.setMessage("Example ["+index+"]");
        gaugeTile.setActiveIndex(index);
        gaugeTile.setStatus(""+index);

        return gaugeTile;

    } // end getBogusPush

    private GaugeTile getBogusFetch(TileInstance tileInstance) {
        return getBogusPush(tileInstance,1);
    } // end getBogusFetch

} // end class
