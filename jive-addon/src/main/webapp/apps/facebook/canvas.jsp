<%@ include file="/WEB-INF/include/environment.jsp"%>
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