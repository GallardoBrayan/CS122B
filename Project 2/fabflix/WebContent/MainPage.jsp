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
		User userToLogin = (User) session.getAttribute("userToken");
		if (userToLogin == null && request.getParameter("username") != null) {
			userToLogin = dbConnection.loginToFabFlix(request.getParameter("username"),
					request.getParameter("pass"));
			response.addHeader("Refresh","0"); 
		}
		if (userToLogin == null) {
			request.getSession().setAttribute("error", "loginError");
			response.sendRedirect("login.jsp");
			 
		} else
		{
		request.getSession().setAttribute("userToken", userToLogin);
	%>
	<div align="center">
		<table>
			<tr>
				<td width="35">&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<th>
					<div align="center">
						Welcome to FablFlix <b><%=userToLogin.getFirst_name()%></b>
					</div>
				</th>
			</tr>
			<tr>
				<td width="35">&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<th>
					<form action="search">
						<input type="submit" value="Search the movie Database">
					</form>
				</th>
			</tr>
			<tr>
				<td width="35">&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<th>
					<form action="Browse">
						<input type="submit" value="Browse the movie Database">
					</form>
				</th>
			</tr>
		</table>
	</div>
<% } %>
</body>
</html>