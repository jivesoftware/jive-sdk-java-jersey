package com.jivesoftware.addon.example.tile;

import com.jivesoftware.sdk.api.entity.TileInstance;
import com.jivesoftware.sdk.api.tile.JiveTableTile;
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
public class MyExampleTableTile extends JiveTableTile {
    private static final Logger log = LoggerFactory.getLogger(MyExampleTableTile.class);

    public static final String NAME = "jersey-example-table";

    //TODO: INJECT CONFIGURATION WITH FACTORY THAT USES THE "Injectables List to Introspect and CREATE RULE
    // INJECTING PROPERLY TYPED/SCOPE CONFIGURATIONS FROM THE PROPERTY

    @Inject
    private JiveTileClient tileClient = null;

    @Override
    public String getName() {   return NAME; }

    @Override
    protected JiveTileClient getTileClient() {  return tileClient; }

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

    private TableTile getBogusPush(TileInstance tileInstance) {
        TableTile tableTile = new TableTile();
        tableTile.setTitle("Example Table");

        String calendarUrl = tileInstance.getConfig().get("table_url");

        TableRow row1 = new TableRow();
        row1.setName("timestamp");
        row1.setValue(""+new Date().getTime());

        tableTile.addTableRow(row1);

        TableRow row2 = new TableRow();
        row2.setName("table_url");
        row2.setValue("View Table");
        row2.setUrl(calendarUrl);

        tableTile.addTableRow(row2);

        return tableTile;

    } // end getBogusPush

    private TableTile getBogusFetch(TileInstance tileInstance) {
        return getBogusPush(tileInstance);
    } // end getBogusFetch

} // end class
