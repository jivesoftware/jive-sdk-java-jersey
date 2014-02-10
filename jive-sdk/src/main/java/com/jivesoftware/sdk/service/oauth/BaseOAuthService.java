package com.jivesoftware.sdk.service.oauth;

import com.google.common.collect.Maps;
import com.jivesoftware.sdk.service.BaseAddOnService;
import org.glassfish.jersey.client.oauth2.TokenResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by rrutan on 2/5/14.
 */
public abstract class BaseOAuthService extends BaseAddOnService {
    private static final Logger log = LoggerFactory.getLogger(BaseOAuthService.class);

    public abstract String getOAuthServiceName();
    public abstract int getOAuthVersion();

    protected String getFlowSessionKey() { return this.getClass().getName()+"-flow"; }
    protected String getInstanceIDSessionKey() { return this.getClass().getName()+"-instanceID"; }
    protected String getUserIDSessionKey() { return this.getClass().getName()+"-userID"; }

    private void initOAuthSuccessEventData(Map<String, Object> data, String instanceID, String userID) {
        data.put("jive.userID",userID);
        data.put("jive.instanceID",instanceID);

        data.put("oauth.service",getOAuthServiceName());
        data.put("oauth.type",getOAuthVersion());
    } // end initOAuthSuccessEventData

    protected Map<String, Object> getOAuth1GrantSuccessData(String instanceID, String userID, String token, String verifier) {
        Map data = Maps.newHashMap();

        initOAuthSuccessEventData(data,instanceID,userID);

        data.put("oauth1.token",token);
        data.put("oauth1.verifier",verifier);

        if (getOAuthVersion() != 1) {
            log.warn("OAuth Service declares OAuthVersion != 1, expected 1 with call to getOAuth1GrantSuccessData");
        } // end if

        return data;
    } // end getOAuth1GrantSuccessData

    protected Map<String, Object> getOAuth2GrantSuccessData(String instanceID, String userID, String code, TokenResult tokens) {
        Map data = Maps.newHashMap();

        initOAuthSuccessEventData(data, instanceID, userID);

        data.put("oauth2.code",code);
        data.put("oauth2.tokens",tokens);

        if (getOAuthVersion() != 2) {
            log.warn("OAuth Service declares OAuthVersion != 2, expected 2 with call to getOAuth2GrantSuccessData");
        } // end if

        return data;
    } // end getOAuth2GrantSuccessData
}
