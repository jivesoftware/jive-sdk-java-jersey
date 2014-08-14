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

package com.jivesoftware.sdk.api.entity.impl.memory;

import com.jivesoftware.sdk.api.entity.HealthStatusProvider;
import com.jivesoftware.sdk.api.health.data.HealthMessage;
import com.jivesoftware.sdk.api.health.data.HealthResource;
import com.jivesoftware.sdk.api.health.data.HealthStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by rrutan on 2/6/14.
 */
public class MemoryHealthStatusProvider implements HealthStatusProvider {
    private static final Logger log = LoggerFactory.getLogger(MemoryHealthStatusProvider.class);

    public MemoryHealthStatusProvider() {
        if (log.isTraceEnabled()) { log.trace("constructor called..."); }
    } // end constructor

    @Override
    public void init() throws HealthStatusProviderException {
        if (log.isTraceEnabled()) { log.trace("init called..."); }
    }  // end init

    @Override
    public HealthStatus getStatus() {

        HealthStatus status = new HealthStatus();
        status.setLastUpdate(new Date());
        status.setStatus("ok");

        HealthMessage statusMessage = new HealthMessage();
        statusMessage.setDetail("sample detail");
        statusMessage.setFix("sample fix");
        statusMessage.setLevel("info");
        statusMessage.setSummary("sample summary");

        status.addMessage(statusMessage);

        HealthResource resource = new HealthResource();
        resource.setLastUpdate(new Date());
        resource.setName("resource name");
        resource.setStatus("fault");
        resource.setUrl("http://www.google.com");

        HealthMessage resourceMessage = new HealthMessage();
        resourceMessage.setDetail("resource detail");
        resourceMessage.setFix("resource fix");
        resourceMessage.setLevel("debug");
        resourceMessage.setSummary("resource summary");

        resource.addMessage(resourceMessage);

        status.addResource(resource);

        return status;
    } // end getStatus

    @Override
    public void dispose() {
        if (log.isTraceEnabled()) { log.trace("dispose called..."); }
    } // end dispose

} // end class
