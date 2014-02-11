<%@ include file="/WEB-INF/include/environment.jsp"%>
<%--
  ~ /*
  ~  * Copyright 2013 Jive Software
  ~  *
  ~  *    Licensed under the Apache License, Version 2.0 (the "License");
  ~  *    you may not use this file except in compliance with the License.
  ~  *    You may obtain a copy of the License at
  ~  *
  ~  *       http://www.apache.org/licenses/LICENSE-2.0
  ~  *
  ~  *    Unless required by applicable law or agreed to in writing, software
  ~  *    distributed under the License is distributed on an "AS IS" BASIS,
  ~  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~  *    See the License for the specific language governing permissions and
  ~  *    limitations under the License.
  ~  */
  --%>

<link rel="stylesheet" type="text/css" href="stylesheets/main.css">

<script type="text/javascript">

    var ALIAS = "facebook-oauth2-example";

    function loadMe() {
        console.log("loadMe() started");
        osapi.jive.connects.get({
            alias : ALIAS,
            href : '/me'
        }).execute(function (response) {
            console.log("loadMe() response = " + JSON.stringify(response));
            if (response.error) {
                if (response.error.code == 401) {
                    console.log("Received a 401 response, triggering reconfiguration");
                    osapi.jive.connects.reconfigure(ALIAS, response, function(feedback) {
                        console.log("Received reconfigure feedback " + JSON.stringify(feedback));
                        loadMe();
                    })
                }
                else {
                    alert("Error " + response.error.code + " loading data: " + response.error.message);
                }
            }
            else {
                // Use the first returned API version by default
                console.log("loadMe() success ...");
                showProfile(response.content[0]);
            }
        });
    }

    function showProfile(profile) {
        console.log("showProfile called ...");
        $('#facebook-profile pre').html(profile);
    } // end showProfile

    gadgets.util.registerOnLoadHandler(loadMe);
</script>
<div id="facebook-profile">
    <pre>loading</pre>
</div>