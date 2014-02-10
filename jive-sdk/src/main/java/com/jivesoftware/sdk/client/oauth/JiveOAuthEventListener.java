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

package com.jivesoftware.sdk.client.oauth;

import com.google.common.collect.Maps;
import com.jivesoftware.sdk.api.entity.JiveInstance;
import com.jivesoftware.sdk.event.JiveInstanceEvent;
import com.jivesoftware.sdk.event.OAuthEvent;
import com.jivesoftware.sdk.event.types.instance.InstanceRegisterSuccess;
import com.jivesoftware.sdk.utils.JiveSDKUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Map;

/**
 * Created by rrutan on 2/7/14.
 */
public class JiveOAuthEventListener {
    private static final Logger log = LoggerFactory.getLogger(JiveOAuthEventListener.class);

    @Inject
    Event<OAuthEvent> oauthEvent;

    @Inject
    private JiveOAuthClient jiveOAuthClient;

    private void fireOAuthEvent(OAuthEvent.Type type, Object context) {
        if (log.isDebugEnabled()) { log.debug("firingOAuthEvent [" + type + "]..."); }
        Map<String, Object> data = Maps.newHashMap();
        data.put(OAuthEvent.CONTEXT,context);
        oauthEvent.fire(new OAuthEvent(type, data));
    } // end fireOAuthEvent

    private void onJiveInstanceRegisterSuccessEvent(@Observes @InstanceRegisterSuccess JiveInstanceEvent event) {
        if (log.isDebugEnabled()) { log.debug("onJiveInstanceRegisterSuccessEvent["+event.getType()+"] called..."); }
        JiveInstance jiveInstance = (JiveInstance)event.getContext();

        if (JiveSDKUtils.isAllExist(jiveInstance.getCode())) {
            //TODO: CHECK IF CODES NEED TO BE REFRESHED USING EXPIRE
            if (log.isDebugEnabled()) { log.debug("Refreshing OAuth Access Tokens["+jiveInstance.getTenantId()+"] ..."); }

            jiveOAuthClient.initAccessTokens(jiveInstance);

            if (log.isDebugEnabled()) { log.debug("Refreshing OAuth Access Tokens ... DONE"); }
            fireOAuthEvent(OAuthEvent.Type.RefreshSuccess,jiveInstance);
        } // end if
    } // end onJiveInstanceRegisterSuccessEvent

} // end class
