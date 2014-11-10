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

import com.jivesoftware.sdk.client.BaseJiveClient;
import com.jivesoftware.sdk.client.JiveAPIClient;
import com.jivesoftware.sdk.client.JiveClientException;
import com.jivesoftware.sdk.client.JiveRunAs;
import com.jivesoftware.sdk.api.data.ApiVersion;
import com.jivesoftware.sdk.api.data.JiveContent;
import com.jivesoftware.sdk.api.data.JiveContents;

import java.net.URI;

/**
 * Created by rrutan on 11/7/14.
 */
public class ExampleApiRunner {

    private static final String BASIC_AUTH_USERNAME = "username";
    private static final String BASIC_AUTH_PASSWORD = "password";

    //** NOTE:  SEE THE FOLLOWING JC DOCUMENT ON HOW TO SETUP YOUR JIVE INSTANCE FOR RUN-AS
    //** https://community.jivesoftware.com/docs/DOC-110343
    private static final JiveRunAs runAs = new JiveRunAs(JiveRunAs.Strategy.email,"some.email@devnull");

    private static final String JIVE_INSTANCE_URL = "https://community.jivesoftware.com";


    public static void main(String[] args) throws JiveClientException {

        JiveAPIClient client = new JiveAPIClient();

        getApiVersion(client);

        listContents(client);

    } // end main

    private static void getApiVersion(JiveAPIClient client) throws JiveClientException {

        ApiVersion version = (ApiVersion)client.call(
            BaseJiveClient.HttpMethods.GET,
            URI.create(JIVE_INSTANCE_URL+"/api/version"),
            "application/json",
            "application/json",
            null, // USED ONLY FOR SENDING DATA (see: com.jivesoftware.sdk.api.tile.data.ListTile for an example)
            null, // DEFAULTS TO GUEST, BUT USE THIS FOR NAMED CONTEXTS - client.getBasicAuth(BASIC_AUTH_USERNAME,BASIC_AUTH_PASSWORD),
            null, // runAs, ONLY USED IF EXECUTING RUN-AS LOGIC
            ApiVersion.class);

        System.out.println("Your Jive Version is: " + version.getJiveVersion());
    } // end getApiVersion

    private static void listContents(JiveAPIClient client) throws JiveClientException {

        String allContents = "/api/core/v3/contents?count=5";

        JiveContents contents = (JiveContents)client.call(
            BaseJiveClient.HttpMethods.GET,
            URI.create(JIVE_INSTANCE_URL+allContents),
            "application/json",
            "application/json",
            null, // USED ONLY FOR SENDING DATA (see: com.jivesoftware.sdk.api.tile.data.ListTile for an example)
            null, // DEFAULTS TO GUEST, BUT USE THIS FOR NAMED CONTEXTS - client.getBasicAuth(BASIC_AUTH_USERNAME,BASIC_AUTH_PASSWORD),
            null, // runAs, ONLY USED IF EXECUTING RUN-AS LOGIC
            JiveContents.class);

        System.out.println("Your Jive Content:");
        for (JiveContent contentItem : contents.getList()) {
            System.out.println("\t" + contentItem.getContentID() + " : " + contentItem.getSubject() + " : " + contentItem.getAuthor().getDisplayName() + " : " + contentItem.getResources().get("self").getRef());
        } // end for content

    } // end listContents

} // end class
