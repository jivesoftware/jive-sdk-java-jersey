package com.jivesoftware.sdk.api.tile;

import com.jivesoftware.sdk.api.entity.TileInstance;
import com.jivesoftware.sdk.api.tile.data.ListTile;
import com.jivesoftware.sdk.api.tile.data.TableTile;
import com.jivesoftware.sdk.client.JiveClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by rrutan on 2/4/14.
 */
public abstract class JiveTableTile extends BaseJiveTile implements JiveTile<TableTile>{

    private static final Logger log = LoggerFactory.getLogger(JiveGaugeTile.class);

    @Override
    public TableTile fetchData(TileInstance tileInstance) throws JiveClientException {
        if (log.isDebugEnabled()) { log.debug("fetchData["+ tileInstance.getJivePushUrl()+"] called"); }
        return (TableTile)getTileClient().fetchData(tileInstance, TableTile.class);
    } // end fetchData

    @Override
    public void pushData(TileInstance tileInstance, TableTile data) throws JiveClientException {
        if (log.isDebugEnabled()) { log.debug("pushData["+ tileInstance.getJivePushUrl()+"] called"); }
        getTileClient().pushData(tileInstance,data);
    } // end pushData

}
