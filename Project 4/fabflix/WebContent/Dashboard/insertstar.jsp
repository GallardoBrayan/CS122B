<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="fabflix.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
		Birth(yyyy/mm/dd): <input type="date" name="DateOfBirth" required><br>
		Photo Url: <input type="text" name="starphotourl" ><br>
		 <input type="submit" name="submit" value="Add Star">
	</form>

</body>
</html>