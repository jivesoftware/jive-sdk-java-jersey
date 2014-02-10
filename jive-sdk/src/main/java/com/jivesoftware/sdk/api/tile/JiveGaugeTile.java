package com.jivesoftware.sdk.api.tile;

import com.jivesoftware.sdk.api.entity.TileInstance;
import com.jivesoftware.sdk.api.tile.data.GalleryTile;
import com.jivesoftware.sdk.api.tile.data.GaugeTile;
import com.jivesoftware.sdk.client.JiveClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by rrutan on 2/4/14.
 */
public abstract class JiveGaugeTile extends BaseJiveTile implements JiveTile<GaugeTile>{
    private static final Logger log = LoggerFactory.getLogger(JiveGaugeTile.class);

    @Override
    public GaugeTile fetchData(TileInstance tileInstance) throws JiveClientException {
        if (log.isDebugEnabled()) { log.debug("fetchData["+ tileInstance.getJivePushUrl()+"] called"); }
        return (GaugeTile)getTileClient().fetchData(tileInstance, GaugeTile.class);
    } // end fetchData

    @Override
    public void pushData(TileInstance tileInstance, GaugeTile data) throws JiveClientException {
        if (log.isDebugEnabled()) { log.debug("pushData["+ tileInstance.getJivePushUrl()+"] called"); }
        getTileClient().pushData(tileInstance,data);
    } // end pushData

} // end class

