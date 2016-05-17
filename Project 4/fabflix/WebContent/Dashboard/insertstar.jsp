<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="fabflix.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert Star</title>
</head>
<body>
	<%
		User userToLogin = (User) session.getAttribute("empToken");
		if (userToLogin == null) {
			response.sendRedirect( request.getContextPath()+ "/_dashboard");
			userToLogin = new User();
		}
	%>
	<form action="DashboardFunctions" method="post">
		Star Name: <input type="text" name="name" required><br> Date of
		Birth(YYYY/MM/DD): <input type="date" name="DateOfBirth" required><br>
		Photo URL: <input type="text" name="starphotourl" ><br>
		 <input type="submit" name="submit" value="Add Star">
	</form>

</body>
</html>