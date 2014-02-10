package com.jivesoftware.addon.example.tile;

import com.jivesoftware.sdk.api.entity.TileInstance;
import com.jivesoftware.sdk.api.tile.JiveGalleryTile;
import com.jivesoftware.sdk.api.tile.data.GalleryImage;
import com.jivesoftware.sdk.api.tile.data.GalleryTile;
import com.jivesoftware.sdk.api.tile.data.TableRow;
import com.jivesoftware.sdk.api.tile.data.TableTile;
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
public class MyExampleGalleryTile extends JiveGalleryTile {
    private static final Logger log = LoggerFactory.getLogger(MyExampleGalleryTile.class);

    public static final String NAME = "jersey-example-gallery";

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

    private GalleryTile getBogusPush(TileInstance tileInstance, int index) {
        GalleryTile galleryTile = new GalleryTile();
        galleryTile.setTitle("Example Gallery");

        String imageIndex = tileInstance.getConfig().get("image_idx");

        GalleryImage image = new GalleryImage();
        image.setImage("http://jivedev.ryanrutan.com:18099/tiles/jersey-example-gallery/images/photo"+index+".png");
        image.setThumb("http://jivedev.ryanrutan.com:18099/tiles/jersey-example-gallery/images/photo" + index + "-thumb.png");
        image.setTitle("Image "+index);

        galleryTile.addGalleryImage(image);

        return galleryTile;

    } // end getBogusPush

    private GalleryTile getBogusFetch(TileInstance tileInstance) {
        return getBogusPush(tileInstance,1);
    } // end getBogusFetch
} // end class
