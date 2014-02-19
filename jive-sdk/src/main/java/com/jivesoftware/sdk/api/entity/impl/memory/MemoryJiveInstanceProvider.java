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

import com.jivesoftware.sdk.api.entity.JiveInstance;
import com.jivesoftware.sdk.api.entity.JiveInstanceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

/**
 * Created by rrutan on 2/3/14.
 */
@Singleton
public class MemoryJiveInstanceProvider implements JiveInstanceProvider {
    private static final Logger log = LoggerFactory.getLogger(MemoryJiveInstanceProvider.class);

    private static MemoryJiveInstanceProvider instance = null;

    private MemoryJiveInstanceProvider() {
        if (log.isTraceEnabled()) { log.trace("constructor called..."); }
    } // end constructor

    public static MemoryJiveInstanceProvider getInstance() {
        if (instance == null) {
            instance = new MemoryJiveInstanceProvider();
        }// end if
        return instance;
    } // end getInstance

    @Override
    public void init() throws JiveInstanceProviderException {
        if (log.isTraceEnabled()) { log.trace("init called..."); }
    } // end init

    public void addInstance(JiveInstance jiveInstance) {
        if (log.isDebugEnabled()) { log.debug("Adding Instance ["+jiveInstance.getTenantId()+"] ..."); }
        MemoryJiveInstanceStore.getInstance().getMap().put(jiveInstance.getTenantId(),jiveInstance);
    } // end addInstance

    @Override
    public JiveInstance getInstanceByTenantId(String tenantId) {
        if (log.isTraceEnabled()) { log.trace("getInstanceByTenantId called..."); }
        return MemoryJiveInstanceStore.getInstance().getMap().get(tenantId);
    } // end getInstanceByTenantId

    @Override
    public void remove(JiveInstance jiveInstance) throws JiveInstanceProviderException {
        if (log.isTraceEnabled()) { log.trace("remove called..."); }
        MemoryJiveInstanceStore.getInstance().getMap().remove(jiveInstance.getTenantId());
    } // end remove

    @Override
    public void update(JiveInstance jiveInstance) throws JiveInstanceProviderException {
        if (log.isTraceEnabled()) { log.trace("update called..."); }
        MemoryJiveInstanceStore.getInstance().getMap().put(jiveInstance.getTenantId(),jiveInstance);
    } // end update

    @Override
    public void dispose() {
        if (log.isTraceEnabled()) { log.trace("dispose called..."); }
    } // end dispose

} // end class