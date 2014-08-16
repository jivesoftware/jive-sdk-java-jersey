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

package com.jivesoftware.sdk.api.webhook.data;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by rrutan on 8/16/14.
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class Webhook implements Serializable {


    public enum ContentEvent {

        document, discussion, file, dm, post, task, url, update, idea

    } // end ContentWebhook

    public enum SystemEvent {
        // SYSTEM WEBHOOKS
        user_account, user_session, user_membership, social_group, webhook

    } // end WebhookType

    public enum Verb {
        // CONTENT VERBS
        created, modified, commented, replied, liked, outcome_set,
        // SYSTEM VERBS
        user_account_created, user_account_deleted, user_account_disabled, user_account_enabled, user_account_invisible, user_account_visible,
        user_profile_modified, user_profile,
        user_type_modified,
        user_session_login, user_session_logout,
        user_membership_added, user_membership_removed,
        social_group_created, social_group_renamed, social_group_deleted,
        webhook_created, webhook_deleted, webhook_enabled, webhook_disabled, webhook_marked_buggy, webhook_marked_not_buggy;

        public String getFullName() { return "jive:"+name(); }
        public boolean equals(String verb) {
            if (verb == null) {
                return false;
            } // end if
            return (verb.equals(name()) || verb.equals(getFullName()));
        } // end equals
    } // end enum

    private WebhookJiveObject object;

    private String url;

    private String content;

    private String title;

    //*** TODO: MAY NEED A DIFFERENT TYPE
    private WebhookJiveObject actor;

    private String verb;

    private WebhookJiveObject jive;

    private WebhookJiveObject target;

    private Date published;

    private Date updated;

    private WebhookProvider provider;

    private String webhook;

}
