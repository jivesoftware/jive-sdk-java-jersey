package com.jivesoftware.sdk.api.tile;

import com.jivesoftware.sdk.api.entity.TileInstance;
import com.jivesoftware.sdk.api.tile.data.ListTile;
import com.jivesoftware.sdk.client.JiveClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by rrutan on 2/4/14.
 */
public abstract class JiveListTile extends BaseJiveTile implements JiveTile<ListTile>{
    private static final Logger log = LoggerFactory.getLogger(JiveGaugeTile.class);

    @Override
    public ListTile fetchData(TileInstance tileInstance) throws JiveClientException {
        if (log.isDebugEnabled()) { log.debug("fetchData["+ tileInstance.getJivePushUrl()+"] called"); }
        return (ListTile)getTileClient().fetchData(tileInstance, ListTile.class);
    } // end fetchData

    @Override
    public void pushData(TileInstance tileInstance, ListTile data) throws JiveClientException {
        if (log.isDebugEnabled()) { log.debug("pushData["+ tileInstance.getJivePushUrl()+"] called"); }
        getTileClient().pushData(tileInstance,data);
    } // end pushData

}
