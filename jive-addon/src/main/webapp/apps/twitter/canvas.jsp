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

<div>

    <div class="container" style="display: none;" id="approval">
        <img class="logo" src="http://s3.amazonaws.com/jive/icons/twitter_48x48.png" alt="twitter"/>
        <p>This app demonstrates performing authorization against Twitter using oAuth. Click
            the button below to authorize access to your Twitter account for the app.</p>

        <form onsubmit="return false;" method="post" class="app-form" action="#">
            <p class="app-form-buttons">
                <button id="personalize">Grant Access</button>
            </p>
        </form>
    </div>

    <div class="container"style="display: none;" id="waiting">
        <img class="logo" src="http://s3.amazonaws.com/jive/icons/twitter_48x48.png" alt="twitter"/>
        <p>Awaiting authorization. Please click the button below once you've approved access
            to your data.</p>

        <form onsubmit="return false;" method="post" class="app-form" action="#">
            <p class="app-form-buttons">
                <button id="approvalDone">Authorization Granted</button>
            </p>
        </form>
    </div>

    <div id="main">
        <div class="icon-header container clearfix">
            <img class="logo" src="http://s3.amazonaws.com/jive/icons/twitter_48x48.png" alt="twitter"/>
            <div id="myprofile">
                <h2 id="screenname"></h2>
                <img id="profile-image" src="" width="48" height="48" style="display:none;"/>
            </div>
        </div>
        <div class="container" id="status"></div>
        <div class="container" id="update-form">
            <form class="container" onsubmit="return false;" method="post" class="app-form" action="#">
                <label for="new-status">Your status:</label>
                <textarea id="new-status" placeholder="What’s happening?" style="height: 3em;"></textarea>
                <p class="app-form-buttons">
                    <button id="send-status">Update</button>
                </p>
            </form>
        </div>
    </div>

</div>

<script type="text/javascript">

  var authorizePopUp = null;

  function showOneSection(toshow) {
    var sections = [ 'main', 'approval', 'waiting' ];
    for (var i=0; i < sections.length; ++i) {
      var s = sections[i];
      var el = document.getElementById(s);
      if (s === toshow) {
        el.style.display = "block";
      } else {
        el.style.display = "none";
      }
    }
    gadgets.window.adjustHeight();
  }

  function updateStatus()
  {
    var params = {};
    url = "https://api.twitter.com/1/statuses/update.json";
    params[gadgets.io.RequestParameters.CONTENT_TYPE] = gadgets.io.ContentType.JSON;
    params[gadgets.io.RequestParameters.AUTHORIZATION] = gadgets.io.AuthorizationType.OAUTH;
    params[gadgets.io.RequestParameters.OAUTH_SERVICE_NAME] = "twitter";
    params[gadgets.io.RequestParameters.METHOD] = gadgets.io.MethodType.POST;
    params[gadgets.io.RequestParameters.POST_DATA] = 'status=' + encodeURIComponent($('#new-status')[0].value);

    gadgets.io.makeRequest(url,
    function (response)
    {
      if(response.data)
      {
          $('#update-status')[0].innerHTML = 'updated.';
          setTimeout(function() { $('#update-status')[0].innerHTML = ''; }, 2000);
      }
      else
      {
        errorStr = response.oauthErrorText;
        var main = document.getElementById('main');
        var wtf = document.createTextNode('Something went wrong: ' + errorStr);
        main.appendChild(wtf);
        showOneSection('main');
        showProfileImage(true);
      }

  }, params);
  }

$("#main #send-status").click(updateStatus);

  function showProfileImage(show) {
      var img = $("#profile-image");
      if (show && img.attr("src")) {
          img.show();
      } else {
          img.hide();
      }
  }

  function showResults(result) {
    showOneSection('main');
    showProfileImage(true);

    var titleElement = document.createElement('div');

    var nameNode = document.createTextNode(result.feed.title.$t);

    titleElement.appendChild(nameNode);

    document.getElementById("main").appendChild(titleElement);
    document.getElementById("main").appendChild(document.createElement("br"));

    list = result.feed.entry;

    for(var i = 0; i < list.length; i++) {

      entry = list[i];

      var divElement = document.createElement('div');
      divElement.setAttribute('class', 'name');

      var valueNode = document.createTextNode(entry.gd$email[0].address);

      divElement.appendChild(nameNode);
      divElement.appendChild(valueNode);

      document.getElementById("main").appendChild(divElement);
    }
  }

    function makeRequestCallback(response)
    {
        var approvalUrl = response.oauthApprovalUrl;
        if(!approvalUrl && response.metadata) {
            approvalUrl = response.metadata.oauthApprovalUrl;
            osapi.http.oauthState = response.metadata.oauthState;
            console.log("oauthState from response", osapi.http.oauthState);
        }
        if(approvalUrl)
        {

            if(!this.popup)
            {
                this.popup = new gadgets.oauth.Popup(
                                                approvalUrl,
                                                "height=350, width=550",
                                                function() { showOneSection('waiting'); showProfileImage(false); }, /* onOpen callback */
                                                function() { fetchData() } /* onClose callback */
                                                );

                $('#personalize').bind('click', this.popup.createOpenerOnClick());
                $('#approvalDone').bind('click', this.popup.createApprovedOnClick());
            }

            // personalize.href = response.oauthApprovalUrl;
            showOneSection('approval');
            showProfileImage(false);
        }
        else if (response.data || response.content)
        {
            var twitter = response.data || response.content;

            $('#update-form')[0].style.backgroundColor = twitter.profile_background_color;

            var profImg = $('#profile-image')[0];
            profImg.src = twitter.profile_image_url;

            $('#screenname')[0].innerHTML = twitter.screen_name;
            $('#status')[0].innerHTML = twitter.status.text;

            showOneSection('main');
            showProfileImage(true);
        }
        else
        {
            errorStr = response.oauthErrorText;
            var main = document.getElementById('main');
            var wtf = document.createTextNode('Something went wrong: ' + errorStr);
            main.appendChild(wtf);
            showOneSection('main');
            showProfileImage(true);
        }
    };


  function fetchData()
  {


    var params = {};
    url = "https://api.twitter.com/1/account/verify_credentials.json";
    params[gadgets.io.RequestParameters.CONTENT_TYPE] = gadgets.io.ContentType.JSON;
    params[gadgets.io.RequestParameters.AUTHORIZATION] = gadgets.io.AuthorizationType.OAUTH;
    params[gadgets.io.RequestParameters.OAUTH_SERVICE_NAME] = "twitter";
    params[gadgets.io.RequestParameters.METHOD] = gadgets.io.MethodType.GET;
    gadgets.io.makeRequest(url, makeRequestCallback, params);

    /*
            console.log("oauthState on request", osapi.http.oauthState);
    osapi.http.get({
'href' : "https://api.twitter.com/1/account/verify_credentials.json",
            'format' : 'json',
            'authz' : 'oauth',
            'oauth_service_name': "twitter",
            'noCache': true,
            'OAUTH_RECEIVED_CALLBACK': gadgets.io.oauthReceivedCallbackUrl_,
            'oauthState': osapi.http.oauthState
            }).execute(makeRequestCallback);
    */
  }

  function authorizeApp() {

//        var params = {};
//        url = "http://jivedev.ryanrutan.com:18099/example/oauth/twitter/authorize";
//        params[gadgets.io.RequestParameters.CONTENT_TYPE] = gadgets.io.ContentType.JSON;
//        params[gadgets.io.RequestParameters.AUTHORIZATION] = gadgets.io.AuthorizationType.OAUTH;
//        params[gadgets.io.RequestParameters.OAUTH_SERVICE_NAME] = "twitter";
//        params[gadgets.io.RequestParameters.METHOD] = gadgets.io.MethodType.GET;
//        gadgets.io.makeRequest(url, makeRequestCallback, params);



      var approvalUrl = "http://jivedev.ryanrutan.com:18099/example/oauth/twitter/authorize";
  var params = {};
      url = "https://api.twitter.com/1/account/verify_credentials.json";
      params[gadgets.io.RequestParameters.CONTENT_TYPE] = gadgets.io.ContentType.JSON;
      params[gadgets.io.RequestParameters.AUTHORIZATION] = gadgets.io.AuthorizationType.SIGNED;
      params[gadgets.io.RequestParameters.OAUTH_SERVICE_NAME] = "twitter";
      params[gadgets.io.RequestParameters.METHOD] = gadgets.io.MethodType.GET;
      gadgets.io.makeRequest(url, makeRequestCallback, params);

    authorizePopUp = new gadgets.oauth.Popup(
                        approvalUrl,
                        "height=350, width=550",
                        function() { showOneSection('waiting'); showProfileImage(false); }, /* onOpen callback */
                        function() { fetchData() } /* onClose callback */
                     );

      $('#personalize').bind('click', authorizePopUp.createOpenerOnClick());
      $('#approvalDone').bind('click', authorizePopUp.createApprovedOnClick());

      showOneSection('approval');
      showProfileImage(false);

  }

  gadgets.util.registerOnLoadHandler(authorizeApp);
</script>