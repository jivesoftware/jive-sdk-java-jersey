<%@ include file="/WEB-INF/include/environment.jsp"%>
<html>
<head>
    <style type="text/css">
        body {
            color: #87ceeb;
        }
    </style>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    <script type="text/javascript">
        (function() {
            jive.tile.onOpen(function(config, options ) {
                gadgets.window.adjustHeight();

                if ( typeof config === "string" ) {
                    config = JSON.parse(config);
                }

                var json = config || {
                    "startSequence": "1"
                };

                // prepopulate the sequence input dialog
                $("#table_url").val( json["table_url"]);

                $("#btn_submit").click( function() {
                    config["table_url"] = $("#table_url").val();
                    jive.tile.close(config, {} );
                    gadgets.window.adjustHeight(400);
                });
            });

        })();
    </script>
</head>

<body>
<div class="j-card">
    <!-- <h2>Configuration</h2> -->

    <p>
        Table Url:
        <br>
        <input type="text" id="table_url">
    </p>

    <button id="btn_submit">Submit</button>

</div>

</body>
</html>
