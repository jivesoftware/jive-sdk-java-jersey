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

package com.jivesoftware.sdk.api.entity.impl.jpa;

import com.jivesoftware.sdk.api.entity.JiveInstance;
import com.jivesoftware.sdk.api.entity.JiveInstanceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by rrutan on 2/3/14.
 */
public class JpaJiveInstanceProvider implements JiveInstanceProvider {
    private static final Logger log = LoggerFactory.getLogger(JpaJiveInstanceProvider.class);

    @Override
    public JiveInstance getInstanceByTenantId(String tenantId) {
        return null;
    }

    @Override
    public void remove(JiveInstance jiveInstance) throws JiveInstanceProviderException {
    }

    @Override
    public void update(JiveInstance jiveInstance) throws JiveInstanceProviderException {
    }

    @Override
    public void dispose() {
    }
}
