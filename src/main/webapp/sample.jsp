 <%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
 <%
    request.setCharacterEncoding("UTF-8");
    String firstname = request.getParameter("firstname");

     System.out.println("first name is "+firstname);
     System.out.println("char encoding is " +request.getCharacterEncoding());
%>