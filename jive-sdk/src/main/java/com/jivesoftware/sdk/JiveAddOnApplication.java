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

package com.jivesoftware.sdk;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jivesoftware.sdk.api.entity.*;
import com.jivesoftware.sdk.api.entity.impl.memory.*;
import com.jivesoftware.sdk.client.JiveAPIClient;
import com.jivesoftware.sdk.client.JiveAnalyticsClient;
import com.jivesoftware.sdk.client.JiveTileClient;
import com.jivesoftware.sdk.client.oauth.JiveOAuthClient;
import com.jivesoftware.sdk.client.oauth.JiveOAuthEventListener;
import com.jivesoftware.sdk.service.filter.*;
import com.jivesoftware.sdk.service.health.HealthService;
import com.jivesoftware.sdk.service.instance.InstanceService;
import com.jivesoftware.sdk.service.oauth.JiveOAuth2Service;
import com.jivesoftware.sdk.service.oauth.facebook.FacebookOAuth2Service;
import com.jivesoftware.sdk.service.oauth.google.GoogleOAuth2Service;
import com.jivesoftware.sdk.service.oauth.twitter.TwitterOAuth1Service;
import com.jivesoftware.sdk.service.storage.StorageService;
import com.jivesoftware.sdk.service.tile.TileService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.jboss.weld.Weld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Singleton;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Created by rrutan on 1/29/14.
 */
@Singleton
public abstract class JiveAddOnApplication extends ResourceConfig {
    private static final Logger log = LoggerFactory.getLogger(JiveAddOnApplication.class);

    public JiveAddOnApplication() {

        register(JacksonFeature.class);

      } // end constructor

} // end class
