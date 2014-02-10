package com.jivesoftware.sdk.api.tile;

import com.jivesoftware.sdk.api.entity.TileInstance;
import com.jivesoftware.sdk.api.tile.data.CalendarTile;
import com.jivesoftware.sdk.api.tile.data.GalleryTile;
import com.jivesoftware.sdk.client.JiveClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by rrutan on 2/4/14.
 */
public abstract class JiveGalleryTile extends BaseJiveTile implements JiveTile<GalleryTile>{

    private static final Logger log = LoggerFactory.getLogger(JiveGalleryTile.class);

    @Override
    public GalleryTile fetchData(TileInstance tileInstance) throws JiveClientException {
        if (log.isDebugEnabled()) { log.debug("fetchData["+ tileInstance.getJivePushUrl()+"] called"); }
        return (GalleryTile)getTileClient().fetchData(tileInstance, GalleryTile.class);
    } // end fetchData

    @Override
    public void pushData(TileInstance tileInstance, GalleryTile data) throws JiveClientException {
        if (log.isDebugEnabled()) { log.debug("pushData["+ tileInstance.getJivePushUrl()+"] called"); }
        getTileClient().pushData(tileInstance,data);
    } // end pushData

} // end class
