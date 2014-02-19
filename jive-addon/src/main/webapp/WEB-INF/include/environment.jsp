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
<%@ page import="java.util.Enumeration" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String serviceURL = request.getScheme() + "://" + request.getServerName();
    if (
        (request.getScheme().equals("http") && request.getServerPort() != 80) ||
        (request.getScheme().equals("https") && request.getServerPort() != 443)
            ) {
        serviceURL += ":" + request.getServerPort();
    } // end if
%>


<%--
    // USEFUL FOR DEBUGGING HEADERS ON ACTION/CONFIGURATION SCREENS, CAN ALSO USE CHROME/FIREFOX DEBUGGER NATIVELY
    Enumeration requestHeaders = request.getHeaderNames();
    if (requestHeaders != null && requestHeaders.hasMoreElements()) {
        %><script type="text/javascript"><%
        while (requestHeaders.hasMoreElements()) {
            String requestHeaderName = (String)requestHeaders.nextElement();
            %>console.log('Header: [<%= requestHeaderName %>] : <%= request.getHeader(requestHeaderName) %>');<%
        } // end for header
        %></script><%
    } // end if
--%>