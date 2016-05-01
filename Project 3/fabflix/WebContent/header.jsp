<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="fabflix.*, java.util.*, java.text.*"%>
<%
	dbFunctions dbConnection = new dbFunctions();
	dbConnection.make_connection("jdbc:mysql://localhost:3306/moviedb", "root", "root");
%>
<div class="logo_area">
	<div style="float: left">

		<a href="MainPage"><img src="resources/logo.png" height="42"
			width="42" alt=""> Fabflix</a> <a href="search">Search</a> <a
			href="Browse">Browse</a>
	</div>
	<div style="float: right">

		<%
			if (session.getAttribute("userToken") == null) {
		%>
		<a href="Login">Login</a>
		<%
			String url = request.getRequestURL().toString();
				if (!url.toLowerCase().contains("login")) {
					request.getSession().setAttribute("error", "mustLogin");
					response.sendRedirect("Login");
				}
			} else {
				User currentUser = (User) session.getAttribute("userToken");
		%>
		<%=currentUser.getFirst_name() + "  " + currentUser.getLast_name()%>
		( <a href="LoginHandler?logout=1"> Logout </a> ) <a href="cart">
			Cart </a> (<a href="checkout">Checkout</a>)
		<%
			}
		%>

	</div>
</div>

<div style="clear: both;"></div>
<hr>
