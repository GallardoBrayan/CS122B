<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>FabFlix > Movie Details</title>
<%@ include file="/header.jsp"%>
</head>
<body>
	<%
		Integer movieid = null;
		String full_name = request.getParameter("star_name");
		try{
			movieid = Integer.parseInt(request.getParameter("movieid"));

		}catch (Exception e)
		{
			movieid = null;
			full_name = null;
		}
	
		if (movieid != null && full_name != null) {
			String first_name = full_name.substring(0, full_name.indexOf(" "));
			String last_name = full_name.substring(full_name.indexOf(" ") + 1);

			Star star = dbConnection.getStarFromMovieIdAndName(movieid, first_name, last_name);
			
			
			
	%>
	<table border="2">
		<tr>
			<td rowspan="7"><img src="<%=star.getPhotoUrl()%>" alt="">
			</td>
		</tr>

		<tr>
			<td>Name:</td>
			<td><%=star.getFirst_name()+ " " + star.getLast_name()%></td>
		</tr>
		<tr>
			<td>Date of Birth:</td>
			<td><%=star.getDob()%></td>
		</tr>
		<tr>
			<td>ID:</td>
			<td><%=star.getId()%></td>
		</tr>
		<tr>
			<td>Starred In:</td>
			<td><%
								for (Map.Entry<Integer ,String> pair : star.getMovies().entrySet()) {
				%>
						<a href="getMoive?movie_id=<%=pair.getKey()%>"><%=pair.getValue()%></a>
				<%
								}
					%></td>
		</tr>
	</table>
	<%
		}
		
		else
		{
			%>
	<h2>
		No Star Found</h2>
	<% 
		}
	%>
</body>
<%@ include file="/footer.jsp"%>
</html>