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

package com.jivesoftware.sdk.api.entity;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.node.IntNode;
import org.codehaus.jackson.node.LongNode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by rrutan on 2/24/14.
 */
@Entity
@JsonDeserialize (using = WebhookDeserializer.class)
public class WebhookInstance {

    @Id
    @GeneratedValue
    @Column(name = "webhook_id")
    private long id;

    private String selfReference;

    private String object;

    private String callback;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSelfReference() {
        return selfReference;
    }

    public void setSelfReference(String selfReference) {
        this.selfReference = selfReference;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}

class WebhookDeserializer extends JsonDeserializer<WebhookInstance> {

    //{
    //     "id": "1006",
    //     "resources": {
    //           "self": {
    //                 "allowed": [
    //                "GET",
    //                "PUT",
    //                "DELETE"
    //            ],
    //             "ref": "http://<jive URL here>/api/core/v3/webhooks/1006"
    //        }
    //    },
    //     "object": "http://<jive URL here>/api/core/v3/places/<place ID here>",
    //     "callback": "http://<service callback URL here>"
    //}

    @Override
    public WebhookInstance deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode root = jsonParser.getCodec().readTree(jsonParser);

        WebhookInstance webhookInstance = new WebhookInstance();
        webhookInstance.setId(((LongNode)root.get("id")).getLongValue());
        webhookInstance.setObject(root.get("object").getTextValue());

        JsonNode resources = root.get("resources");
        JsonNode self = resources.get("self");

        webhookInstance.setSelfReference(self.get("ref").getTextValue());

        webhookInstance.setCallback(root.get("callback").getTextValue());

        self = null;
        resources = null;
        root = null;

        return webhookInstance;
    } // end deserialize

} // end class
