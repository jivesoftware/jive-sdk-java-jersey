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
                    "image_idx": "1"
                };

                // prepopulate the sequence input dialog
                $("#image_idx").val( json["image_idx"]);

                $("#btn_submit").click( function() {
                    config["image_idx"] = $("#image_idx").val();
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
        Image Index (1-10):
        <br>
        <input type="text" id="image_idx">
    </p>

    <button id="btn_submit">Submit</button>

</div>

</body>
</html>
