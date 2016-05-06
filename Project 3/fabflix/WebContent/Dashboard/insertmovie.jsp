<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="fabflix.*"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert Movie</title>
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
		<table>
			<tr>
				<td>Movie Title(required):</td>
				<td><input type="text" name="title" required><br></td>
			</tr>
			<tr>
				<td>Director(required):</td>
				<td><input type="text" name="director" required><br></td>
			</tr>
			<tr>
				<td>Year(required):</td>
				<td><input type="text" name="year" required><br></td>
			</tr>
			<tr>
				<td>Trailer URL:</td>
				<td><input type="text" name="trailer" /><br></td>
			</tr>
			<tr>
				<td>Banner URL:</td>
				<td><input type="text" name="banner" /><br></td>
			</tr>
			<tr>
				<td>Star First Name:</td>
				<td><input type="text" name="firstname" /><br></td>
			</tr>
			<tr>
				<td>Star Last Name:</td>
				<td><input type="text" name="lastname" /><br></td>
			</tr>
			<tr>
				<td>Star Date of Birth(yyyy/mm/dd):</td>
				<td><input type="date" name="DateOfBirth" /><br></td>
			</tr>
			<tr>
				<td>Star Photo URL:</td>
				<td><input type="text" name="starphotourl" /><br></td>
			</tr>
			<tr>
				<td>Movie Genre:</td>
				<td><input type="text" name="genre" /><br></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" name="submit" value="Add Movie"></td>
			</tr>
		</table>
	</form>

</body>
</html>