package com.jivesoftware.sdk.api.entity;

import com.jivesoftware.sdk.api.health.data.HealthStatus;

/**
 * Created by rrutan on 2/6/14.
 */
public interface HealthStatusProvider extends BaseProvider {

    public HealthStatus getStatus();

} // end interface
