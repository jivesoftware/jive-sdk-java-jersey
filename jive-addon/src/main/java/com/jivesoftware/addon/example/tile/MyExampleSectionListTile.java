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
import com.jivesoftware.sdk.api.tile.JiveSectionListTile;
import com.jivesoftware.sdk.api.tile.data.SectionListItem;
import com.jivesoftware.sdk.api.tile.data.SectionListSection;
import com.jivesoftware.sdk.api.tile.data.SectionListTile;
import com.jivesoftware.sdk.api.tile.data.TileAction;
import com.jivesoftware.sdk.client.JiveClientException;
import com.jivesoftware.sdk.event.TileInstanceEvent;
import com.jivesoftware.sdk.event.TileInstanceEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.TimerTask;

@Singleton
public class MyExampleSectionListTile extends JiveSectionListTile implements TileInstanceEventListener {
    private static final Logger log = LoggerFactory.getLogger(MyExampleTableTile.class);

    public static final String NAME = "jersey-example-sectionlist";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean accepts(TileInstanceEvent event) {
        boolean accept = (event.getTileName().equals(getName()) &&
                (
                        TileInstanceEvent.Type.RegisterSuccess.equals(event.getType()) ||
                                TileInstanceEvent.Type.Unregister.equals(event.getType())
                )
        );
        if (log.isTraceEnabled()) {
            log.trace("accepts[name=" + event.getTileName() + "], type=[" + event.getType() + "] => " + accept);
        }
        return accept;
    } // end accepts

    @Override
    public void process(TileInstanceEvent event) throws TileInstanceEventException {
        if (log.isDebugEnabled()) {
            log.debug("process[name=" + getName() + "] ...");
        }
        if (event.getType().equals(TileInstanceEvent.Type.RegisterSuccess)) {
            onRegisterEvent(event);
        } else if (event.getType().equals(TileInstanceEvent.Type.Unregister)) {
            onUnregisterEvent(event);
        } // end if
    } // end process

    private void onRegisterEvent(TileInstanceEvent event) {
        TileInstance tileInstance = (TileInstance) event.getContext();

        new Thread(new TileTimerTask(tileInstance)).start();
    } // end onRegisterEvent

    private void onUnregisterEvent(TileInstanceEvent event) {
        if (log.isDebugEnabled()) {
            log.debug("onUnregisterEvent[name=" + getName() + "] ...");
        }

    } // end onUnregisterEvent

    /**
     * ***
     * <p/>
     * ****
     */

    class TileTimerTask extends TimerTask {


        private TileInstance tileInstance = null;

        TileTimerTask(TileInstance tileInstance) {
            this.tileInstance = tileInstance;
        } // end constructor

        @Override
        public void run() {
            for (int x = 0; x < 12; x++) {
                if (log.isDebugEnabled()) {
                    log.debug("pushing data");
                }

                /*** PUSH DATA TO TILE ***/
                try {
                    pushData(tileInstance, getBogusPush(tileInstance));
                } catch (JiveClientException jce) {
                    log.error("Unable to Post to Tile", jce);
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

    private SectionListTile getBogusPush(TileInstance tileInstance) {
        TileAction action = new TileAction();
        action.setText("Developers Community");
        action.setUrl("https://developer.jivesoftware.com");

        SectionListTile sectionListTile = new SectionListTile();
        sectionListTile.setTitle("Example SectionList");
        sectionListTile.setStyle(SectionListTile.Styles.accordion);

        SectionListSection section1 = new SectionListSection();
        section1.setDesc("Example Description 1");
        section1.setText("Example Text 1");

        SectionListItem item1 = new SectionListItem();
        item1.setText("Text 1");
        item1.setAction(action);
        item1.setAvatar("https://developer.jivesoftware.com/favicon.ico");
        item1.setByline("Byline 1");

        SectionListItem item2 = new SectionListItem();
        item2.setText("Text 2");
        item2.setAction(action);
        item2.setAvatar("https://developer.jivesoftware.com/favicon.ico");
        item2.setByline("Byline 2");

        section1.addItem(item1);
        section1.addItem(item2);

        /**** *****/

        SectionListSection section2 = new SectionListSection();
        section2.setDesc("Example Description 2");
        section2.setText("Example Text 2");

        SectionListItem item4 = new SectionListItem();
        item4.setText("Text 4");
        item4.setAction(action);
        item4.setAvatar("https://developer.jivesoftware.com/favicon.ico");
        item4.setByline("Byline 4");

        SectionListItem item5 = new SectionListItem();
        item5.setText("Text 5");
        item5.setAction(action);
        item5.setAvatar("https://developer.jivesoftware.com/favicon.ico");
        item5.setByline("Byline 5");

        section2.addItem(item4);
        section2.addItem(item5);

        sectionListTile.addSection(section1);
        sectionListTile.addSection(section2);

        return sectionListTile;

    } // end getBogusPush

    private SectionListTile getBogusFetch(TileInstance tileInstance) {
        return getBogusPush(tileInstance);
    } // end getBogusFetch

} //end class