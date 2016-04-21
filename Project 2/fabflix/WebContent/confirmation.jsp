<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search for a Movie</title>
<%@ include file="/header.jsp" %>
</head>
<body>
<center><h3>Confirmation</h3></center>
<%
String message = (String)session.getAttribute("sale_success_message");
		if(message == null)
		{
			response.sendRedirect("MainPage.jsp");
		}
%>
<h1><%=message%></h1>
<a href="search" style="margin-right:10px;">Return to Search</a><a href="Browse">Return to Browse</a>
</body>
</html>