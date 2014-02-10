<%
    String serviceURL = request.getScheme() + "://" + request.getServerName();
    if (
        (request.getScheme().equals("http") && request.getServerPort() != 80) ||
        (request.getScheme().equals("https") && request.getServerPort() != 443)
            ) {
        serviceURL += ":" + request.getServerPort();
    } // end if
%>