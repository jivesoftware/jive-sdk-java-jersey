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

    public  void pushActivity(TileInstance tileInstance, ActivityPushTile data) throws JiveClientException {
        if (log.isTraceEnabled()) { log.trace("pushActivity called ..."); }
        getTileClient().pushActivity(tileInstance,data);
    } // end fetchExtendedProperties

    public void pushComment(TileInstance tileInstance, ActivityCommentTile data) {
        if (log.isTraceEnabled()) { log.trace("pushActivity called ..."); }
        JiveTileClient client = getTileClient();

    } // end pushComment

} // end class
