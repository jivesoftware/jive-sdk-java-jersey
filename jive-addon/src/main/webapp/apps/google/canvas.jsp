<%@ include file="/WEB-INF/include/environment.jsp"%>

<link rel="stylesheet" type="text/css" href="stylesheets/main.css">



<script type="text/javascript">

    var ALIAS = "google-oauth2-example";

    function loadFiles() {
        console.log("loadFiles() started");
        osapi.jive.connects.get({
            alias : ALIAS,
            href : '/drive/v2/files'
        }).execute(function (response) {
            console.log("loadFiles() response = " + JSON.stringify(response));
            if (response.error) {
                if (response.error.code == 401) {
                    console.log("Received a 401 response, triggering reconfiguration");
                    osapi.jive.connects.reconfigure(ALIAS, response, function(feedback) {
                        console.log("Received reconfigure feedback " + JSON.stringify(feedback));
                        loadFiles();
                    })
                }
                else {
                    alert("Error " + response.error.code + " loading data: " + response.error.message);
                }
            }
            else {
                // Use the first returned API version by default
                console.log("loadFiles() success ...");
                showFiles(response.content[0]);
            }
        });
    }

    function showFiles(files) {
        console.log("showFiles called ...");
        $('#google-files pre').html(files);
    } // end showProfile

    gadgets.util.registerOnLoadHandler(loadFiles);
</script>
<div id="google-files">
    <pre>loading</pre>
</div>