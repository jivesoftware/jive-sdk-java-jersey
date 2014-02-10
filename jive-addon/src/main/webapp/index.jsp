<%@ include file="/WEB-INF/include/environment.jsp"%>
<%@ page import="java.util.UUID" %>
<%@ page import="com.jivesoftware.sdk.JiveAddOnConfig" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<html>
<head>
    <style type="text/css">
        pre { font-size: .6em; border: 1px solid black; border-radius: 4px; padding: 4px; }
        p { font-size: .7em; }
        ul { padding-top: 0px ; }
        ul li { font-size: .6em; }
    </style>
</head>
<%
    String serverURL = request.getScheme() + "://" + request.getServerName() + request.getServerPort();
    String updatedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date());
    String releasedOn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(new Date())+"Z";
    boolean hasServiceSignature = (JiveAddOnConfig.getApplicationServiceSignature() != null);
    boolean hasRunAsStrategy = (JiveAddOnConfig.getApplicationRunAsStrategy() != null && JiveAddOnConfig.getApplicationRunAsStrategy().length() > 0 );
    String appContext = (request.getParameter("appContext") != null) ? request.getParameter("appContext") : "example";
    String tileName = (request.getParameter("tileName") != null) ? request.getParameter("tileName") : "example-tile";
    String appName = (request.getParameter("appName") != null) ? request.getParameter("appName") : "example-app";
    String randomUUID1 = UUID.randomUUID().toString();
    String randomUUID2 = UUID.randomUUID().toString();
    String randomUUID3 = UUID.randomUUID().toString();
%>
<body>
<h1>Jive SDK (Java) - Jersey Implementation</h1>
<!--
<strong>Jive Add-On - Sample Files</strong><br/>
<p>You can use these templates to help you create your extension configuration files in <strong>/src/main/extension</strong>.<br/>
    When you are done configuring your definition.json and meta.json, you can delete this file!<br/>
    Note:  You can affect the output by setting the following request GET parameters to this JSP.
<ul>
    <li><strong>appContext</strong> - default <em>example</em></li>
    <li><strong>tileName</strong> - default <em>example-tile</em></li>
    <li><strong>appName</strong> - default <em>example-app</em></li>
</ul>
</p>
<p>For more information be sure to visit: <a href="https://community.jivesoftware.com/docs/DOC-102993" target="_blank">documentation</a> in the Developer Community</p>
<p>TODO:  Add Configurator Interface Possibly</p>
<hr/>
<strong>/src/main/extension/meta.json - Template</strong><br/>
<p>Values pulled from @annotations on your sub-class of <strong>JiveAddOnApplication</strong> and request params specified above</p>
<pre>{
    "package_version": "1.0",
    "id": "<%= JiveAddOnConfig.getApplicationId() %>",
    "uuid": "<%= JiveAddOnConfig.getApplicationId() %>",
    "type": "client-app",
    "name": "<%= JiveAddOnConfig.getApplicationName() %>",
    "description": "<%= JiveAddOnConfig.getApplicationDescription() %>",
    "minimum_version": "0000",
    "icon_16": "extension-16.png",
    "icon_48": "extension-48.png",
    "icon_128": "extension-128.png",
    "status": "available",
    "released_on": "<%= releasedOn %>",
    "service_url": "<%= serverURL %>",
    "config_url" : "%serviceURL%/addon/<%= appContext %>/configure.html",
    "register_url": "%serviceURL%/<%= appContext %>/instance/register",
    "unregister_url": "%serviceURL%/<%= appContext %>/instance/unregister",
    "redirect_url": "%serviceURL%/<%= appContext %>/oauth/callback",
    "health_url" : "%serviceURL%/<%= appContext %>/health/ping",
    "preinstall" : "%serviceURL%/addon/<%= appContext %>/preinstall.html",
    "website_url" : "http://www.jivesoftware.com/social-business-platform/add-ons/",
    "community_url" : "https://community.jivesoftware.com",
    "author" : "Your Name",
    "author_affiliation" : "Your Company",
    "author_email" : "someone@devnull",
    "info_email" : "someoneelse@devnull"
}</pre>
<hr/>
<strong>/src/main/extension/definition.json - Template</strong><br/>
<p>Values pulled from @annotations on your sub-class of <strong>JiveAddOnApplication</strong> and request params specified above</p>
<pre>{
"integrationUser": {
    "systemAdmin": <% if (hasServiceSignature) { %>true,<% } else { %>false<% } %>
    <% if (hasServiceSignature) { %>"jiveServiceSignature" : "<%=JiveAddOnConfig.getApplicationServiceSignature()%>" <% } %> <% if (hasRunAsStrategy) { %>,<% } %> <%
        if (hasRunAsStrategy) { %>"runAsStrategy" : "<%= JiveAddOnConfig.getApplicationRunAsStrategy() %>" <% } %>
},
"tiles": [
    {
        "sampleData": {
            "title": "<%= tileName %> - List",
            "contents": [
                {
                    "text": "TODO: Update Default Text"
                }
            ],
            "config": {
                "listStyle": "contentList"
            }
        },
        "displayName": "<%= tileName %>",
        "name": "<%= JiveAddOnConfig.getApplicationName() %> - <%= tileName %>",
        "description": "A description for <%= JiveAddOnConfig.getApplicationName() %> - <%= tileName %>",
        "style": "LIST",
        "icons": {
            "16": "<%= serverURL %>/images/extension-16.png",
            "48": "<%= serverURL %>/images/extension-48.png",
            "128": "<%= serverURL %>/images/extension-128.png"
        },
        "action": "<%= serverURL %>/tiles/<%= tileName %>/action.html",
        "id": "<%= randomUUID1 %>",
        "definitionDirName": "<%= tileName %>",
        "published": "<%= updatedDate %>",
        "updated": "<%= updatedDate %>",
        "config": "<%= serverURL %>/tiles/<%= tileName %>/configure.html",
        "unregister": "<%= serverURL %>/<%= appContext %>/tiles/unregister",
        "register": "<%= serverURL %>/<%= appContext %>/tiles/register"
    }
],
"templates": [
    {
        "name": "defaultTemplate",
        "displayName": "<%= JiveAddOnConfig.getApplicationName() %> - Example Template",
        "description": "<%= JiveAddOnConfig.getApplicationName() %> - Example Template Description",
        "tiles": [
            "<%= tileName %>"
        ]
    }
],
"osapps": [
    {
        "name": "<%= JiveAddOnConfig.getApplicationName() %> - Example App",
        "id": "<%= randomUUID2 %>",
        "appPath": "<%= randomUUID2 %>",
        "url": "<%= serverURL %>/apps/<%= appName %>/app.xml"
    }
],
"storageDefinitions": [
    {
        "displayName": "File System Storage",
        "description": "Saves files on the node.js machine",
        "registerPlace": "<%= serverURL %>/<%= appContext %>/storage/register",
        "userMappingOptions": [
            "EMAIL"
        ],
        "supportedResourceNames": [
            "uploadFile",
            "downloadFile"
        ],
        "prefixUrl": "<%= serverURL %>",
        "resolveResources": "<%= serverURL %>/<%= appContext %>/storage/resolve",
        "icons": {
            "16": "extension-16.png",
            "48": "extension-48.png",
            "128": "extension-128.png"
        },
        "healthCheckURL": "<%= serverURL %>/<%= appContext %>/storage/ping",
        "name": "<%= randomUUID3 %>",
    }
],
"jabCartridges": [
    {
        "name": "example_cartridge",
        "displayName": "Example Cartridge",
        "patterns": [
            "community.jivesoftware.com/community/developer"
        ],
        "zipFileName": "<googledrive></googledrive>.zip"
    }
]
}</pre>
-->
</body>
</html>
