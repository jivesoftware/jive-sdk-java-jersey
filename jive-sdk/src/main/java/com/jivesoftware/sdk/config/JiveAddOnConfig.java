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

package com.jivesoftware.sdk.config;

import com.jivesoftware.sdk.utils.JiveSDKUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;

/**
 * Created by rrutan on 2/6/14.
 */
@Component
@Singleton
public class JiveAddOnConfig extends BaseAddOnConfig {
    private static final Logger log = LoggerFactory.getLogger(JiveAddOnConfig.class);

    private boolean development = false;
    private String clientUrl = "http://localhost";
    private int port = 8090;

    public JiveAddOnConfig() {
        JiveSDKUtils.initBeanFromProperties("jiveclientconfiguration.properties", this);
    } // end constructor

    public String getClientUrl() {
        return clientUrl;
    }

    public void setClientUrl(String clientUrl) {
        this.clientUrl = clientUrl;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isDevelopment() {
        return development;
    }
    public void setDevelopment(boolean development) {
        this.development = development;
    }





} // end class
