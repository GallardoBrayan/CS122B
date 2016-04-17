<%@page
	import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"%>
<style>
#form_div {
	text-align: center;
}
</style>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>FabFlix</title>
<%@ include file="/header.jsp" %>
</head>
<body>
	<h3>Fabflix Login</h3>
	<%
		String error = (String) request.getSession().getAttribute("error");
		if ( "loginError".equals(error)) 
		{
			
	%>
	<h3>Invalid User name or password</h3>
	<%
		} 
	%>

	<div id="form_div">
		<form action="/fabflix/MainPage.jsp" method="post">
			User name: <input type="text" name="username" /> Password: <input
				type="password" name="pass"></input> <input type="submit"
				name="submit">
		</form>
	</div>


</body>
</html>
