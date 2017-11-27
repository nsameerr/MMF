<%-- 
    Document   : redirect
    Created on : Mar 18, 2014, 6:13:05 PM
    Author     : 07958
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
    </head>
    <body>
        <% response.sendRedirect(request.getContextPath() + "/faces/index.xhtml"); %>
    </body>
</html>
