package com.jivesoftware.sdk.api.entity;

import com.jivesoftware.sdk.config.JiveAddOnConfig;

/**
 * Created by rrutan on 2/4/14.
 */
public interface AddOnConfigProvider extends BaseProvider {

    public JiveAddOnConfig getConfig();

}
