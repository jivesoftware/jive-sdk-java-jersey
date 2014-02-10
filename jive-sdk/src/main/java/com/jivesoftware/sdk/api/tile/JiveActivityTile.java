package com.jivesoftware.sdk.api.tile;

import com.jivesoftware.sdk.api.entity.TileInstance;
import com.jivesoftware.sdk.api.tile.data.*;
import com.jivesoftware.sdk.client.JiveClientException;
import com.jivesoftware.sdk.client.JiveTileClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by rrutan on 2/4/14.
 */

public abstract class JiveActivityTile extends BaseJiveTile implements JiveTile<ActivityTile> {
    private static final Logger log = LoggerFactory.getLogger(JiveActivityTile.class);

    @Override
    public ActivityTile fetchData(TileInstance tileInstance) throws JiveClientException {
        if (log.isDebugEnabled()) { log.debug("fetchData["+ tileInstance.getJivePushUrl()+"] called"); }
        return (ActivityTile)getTileClient().fetchData(tileInstance, ActivityTile.class);
    } // end fetchData

    @Override
    public void pushData(TileInstance tileInstance, ActivityTile data) throws JiveClientException {
        if (log.isDebugEnabled()) { log.debug("pushData["+ tileInstance.getJivePushUrl()+"] called"); }
        getTileClient().pushData(tileInstance,data);
    } // end pushData

    public  void pushActivity(TileInstance tileInstance, ActivityPushTile data) {
        if (log.isTraceEnabled()) { log.trace("pushActivity called ..."); }
        JiveTileClient client = getTileClient();
    } // end fetchExtendedProperties

    public void pushComment(TileInstance tileInstance, ActivityCommentTile data) {
        if (log.isTraceEnabled()) { log.trace("pushActivity called ..."); }
        JiveTileClient client = getTileClient();

    } // end pushComment

} // end class
