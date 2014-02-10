package com.jivesoftware.sdk.api.tile;

import com.jivesoftware.sdk.api.entity.TileInstance;
import com.jivesoftware.sdk.api.tile.data.ActivityTile;
import com.jivesoftware.sdk.api.tile.data.BaseTile;
import com.jivesoftware.sdk.api.tile.data.CalendarTile;
import com.jivesoftware.sdk.client.JiveClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by rrutan on 2/4/14.
 */

public abstract class JiveCalendarTile extends BaseJiveTile implements JiveTile<CalendarTile>{
    private static final Logger log = LoggerFactory.getLogger(JiveCalendarTile.class);

    @Override
    public CalendarTile fetchData(TileInstance tileInstance) throws JiveClientException {
        if (log.isDebugEnabled()) { log.debug("fetchData["+ tileInstance.getJivePushUrl()+"] called"); }
        return (CalendarTile)getTileClient().fetchData(tileInstance, CalendarTile.class);
    } // end fetchData

    @Override
    public void pushData(TileInstance tileInstance, CalendarTile data) throws JiveClientException {
        if (log.isDebugEnabled()) { log.debug("pushData["+ tileInstance.getJivePushUrl()+"] called"); }
        getTileClient().pushData(tileInstance,data);
    } // end pushData

} // end class
