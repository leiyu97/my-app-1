<!--%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %-->
<%
    Object counterObj = session.getAttribute("counter");
    int counter = 0;
    if (counterObj != null && counterObj instanceof Integer) {
        counter = ((Integer) counterObj).intValue();
    }
    counter++;
    session.setAttribute("counter", new Integer(counter));
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cluster counter</title>
    </head>
    <body>
        <h1>Hello World!</h1>
          <!-- % System.out.println("System.getProperties() is ######## "+System.getProperties());

          //System.getProperties();


          % -->
          <!-- % System.out.println("System.getProperties() is ######## "+System.getProperty("http.nonProxyHosts"));%> -->

          <% com.larinia.app.DirectMemoryTest.writeMaximumDirectMemorySizeToStdOut(); %>
          <%com.larinia.app.DirectMemoryTest.writeUsedDirectMemoryToStdOut();%>

          <%
          // Create cookies
          Cookie cookie = new Cookie("dtCookie", "gerberishlllllsafakhah");

          // Set expiry date after 24 Hrs for both the cookies.
          cookie.setMaxAge(60*60*24);

          // Add the cookies in the response header.
          response.addCookie( cookie );

          Cookie cookie2 = new Cookie("leiCookie", "testLeiCookie");
          response.addCookie( cookie2 );


          %>

        <table>
            <tr><td>Number of visits to this page</td><td><%=counter%></td></tr>
            <tr><td>request.getServerName() - Apache:</td> <td><%=request.getServerName()%></td></tr>
            <tr><td>request.getSession().getId():</td>  <td><%=request.getSession().getId()%></td></tr>
            <tr><td>java.net.InetAddress.getLocalHost().getHostName():</td><td><%=java.net.InetAddress.getLocalHost().getHostName()%></td></tr>
            <tr><td>system properties:</td>  <td><%=System.getProperties()%> </td></tr>
        </table>

    </body>
</html>
