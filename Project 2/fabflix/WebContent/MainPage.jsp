<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to Fabflix</title>
<%@ include file="/header.jsp"%>
</head>
<body>

	<%
		User userToLogin = dbConnection.loginToFabFlix(request.getParameter("username"), request.getParameter("pass"));
		if(userToLogin == null)
		{
			request.getSession().setAttribute("error", "loginError");
			response.sendRedirect("login.jsp");
		}
		else
		{
		request.getSession().setAttribute("userToken", userToLogin);
	%>
	Hi
	<%
		out.print( userToLogin.getFirst_name()); 
	%>
	
	<form action="/fabflix/Browse.jsp">
    <input type="submit" value="Search the movie Database">
	</form>
	
	<form action="/fabflix/Browse.jsp">
    <input type="submit" value="Browse the movie Database">
	</form>
	
	<%
		}
	%>
</body>
</html>