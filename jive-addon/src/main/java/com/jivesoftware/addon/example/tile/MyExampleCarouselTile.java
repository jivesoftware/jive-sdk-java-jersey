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
import com.jivesoftware.sdk.api.tile.JiveCarouselTile;
import com.jivesoftware.sdk.api.tile.JiveSectionListTile;
import com.jivesoftware.sdk.api.tile.data.*;
import com.jivesoftware.sdk.client.JiveClientException;
import com.jivesoftware.sdk.event.TileInstanceEvent;
import com.jivesoftware.sdk.event.TileInstanceEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.TimerTask;

@Singleton
public class MyExampleCarouselTile extends JiveCarouselTile implements TileInstanceEventListener {
    private static final Logger log = LoggerFactory.getLogger(MyExampleTableTile.class);

    public static final String NAME = "jersey-example-carousel";

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

    private CarouselTile getBogusPush(TileInstance tileInstance) {
        TileAction action = new TileAction();
        action.setText("Developers Community");
        action.setUrl("https://developer.jivesoftware.com");

        CarouselTile carouselTile = new CarouselTile();
        carouselTile.setTitle("Example Carousel");

        carouselTile.setAutoPlay(true);
        carouselTile.setPreviewPane(true);
        carouselTile.setSpeed(CarouselTile.Speed.custom);
        carouselTile.setDelay(5);
        carouselTile.setTransition(CarouselTile.Transition.slide);

        CarouselItem item1 = new CarouselItem();
        item1.setDescription("Item1 Description");
        item1.setImage("http://www.easternct.edu/ctreview/connections/stories/images/carousel_1.jpg");
        item1.setImageURI(item1.getImage());
        item1.setTitleLink("http://developer.jivesoftware.com");
        item1.setTitleText("Jive Software - Carousel 1");

        CarouselItem item2 = new CarouselItem();
        item2.setDescription("Item1 Description");
        item2.setImage("http://thispublicaddress.com/tPA4/images/06_06/carousel.jpg");
        item2.setImageURI(item2.getImage());
        item2.setTitleLink("https://community.jivesoftware.com/community/developer");
        item2.setTitleText("Jive Software - Carousel 2");

        CarouselItem item3 = new CarouselItem();
        item3.setDescription("Item1 Description");
        item3.setImage("http://upload.wikimedia.org/wikipedia/en/9/9e/Carousel_1945_Bdwy.jpg");
        item3.setImageURI(item3.getImage());
        item3.setTitleLink("https://community.jivesoftware.com/community/developer");
        item3.setTitleText("On Broadway");

        carouselTile.addItem(item1);
        carouselTile.addItem(item2);
        carouselTile.addItem(item3);

        return carouselTile;

    } // end getBogusPush

    private CarouselTile getBogusFetch(TileInstance tileInstance) {
        return getBogusPush(tileInstance);
    } // end getBogusFetch

} //end class