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
		try{
			movieid = Integer.parseInt(request.getParameter("movieid"));
		}catch (Exception e)
		{
			movieid = null;
		}
	
		if (movieid != null) {
			LinkedHashMap<Integer, Movie> movie_list = (LinkedHashMap<Integer, Movie>) session.getAttribute("movie_list");
			Movie movieToDispay;
			if (movie_list != null && movie_list.containsKey(movieid)) {
				movieToDispay = movie_list.get(movieid);
			} else {
				movieToDispay = dbConnection.returnMovieFromID(movieid);
			}
			if (movieToDispay != null) {
	%>
	<table border="2">
		<tr>
			<td rowspan="7"><img src="<%=movieToDispay.getBanner_url()%>" alt="">
			</td>
		</tr>

		<tr>
			<td>Title:</td>
			<td><%=movieToDispay.getTitle()%></td>
		</tr>
		<tr>
			<td>Year:</td>
			<td><%=movieToDispay.getYear()%></td>
		</tr>
		<tr>
			<td>Director:</td>
			<td><%=movieToDispay.getDirector()%></td>
		</tr>
		<tr>
			<td>ID:</td>
			<td><%=movieid%></td>
		</tr>
		<tr>
			<td>Stars:</td>
			<td><%
						String outputString = "";
								for (String star : movieToDispay.getStars()) {
									outputString += star + ", ";
								}
								outputString = outputString.substring(0, outputString.length() - 2);
								out.print(outputString);
					%></td>
		</tr>
		<tr>
			<td>Genre:</td>
			<td><%
						outputString = "";
								for (String Genre : movieToDispay.getGenres()) {
									outputString += Genre + ", ";
								}
								outputString = outputString.substring(0, outputString.length() - 2);
								out.print(outputString);
					%></td>
		</tr>
		<tr>
			<td>Price:</td><td> $<%=dbConnection.getMoviePrice(movieid)%></td>
		</tr>

	</table>
	<%
		}
		
		else
		{
			%>
	<h2>
		No Movie To Display with id =<%=movieid %></h2>
	<% 
		}
	}
		else
		{
			%>
	<h2>No movieid provided</h2>
	<% 
		}
	%>
</body>
<%@ include file="/footer.jsp"%>
</html>