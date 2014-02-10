package com.jivesoftware.sdk.api.tile;

import com.jivesoftware.sdk.api.entity.TileInstance;
import com.jivesoftware.sdk.api.tile.data.BaseTile;
import com.jivesoftware.sdk.client.JiveClientException;

/**
 * Created by rrutan on 2/4/14.
 */
public interface JiveTile<T extends BaseTile> {

    public String getName();

    public T fetchData(TileInstance tileInstance) throws JiveClientException;

    public void pushData(TileInstance tileInstance, T data) throws JiveClientException;

} // end interface
