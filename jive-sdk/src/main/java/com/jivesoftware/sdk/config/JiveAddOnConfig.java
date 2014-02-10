package com.jivesoftware.sdk.config;

import com.jivesoftware.sdk.service.oauth.google.GoogleOAuth2Service;
import com.jivesoftware.sdk.utils.JiveSDKUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

/**
 * Created by rrutan on 2/6/14.
 */
@Singleton
public class JiveAddOnConfig extends BaseAddOnConfig {
    private static final Logger log = LoggerFactory.getLogger(JiveAddOnConfig.class);

    private boolean development = false;

    public JiveAddOnConfig() {
        JiveSDKUtils.initBeanFromProperties("jiveclientconfiguration.properties", this);
    } // end constructor

    public boolean isDevelopment() {
        return development;
    }

    public void setDevelopment(boolean development) {
        this.development = development;
    }
} // end class
