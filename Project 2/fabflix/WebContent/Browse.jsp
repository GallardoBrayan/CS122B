<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="fabflix.*,java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Browse Movie Titles</title>
</head>
<body>
<%
	Boolean sortByTitle = (Boolean)session.getAttribute("sortByTitle");
	if(sortByTitle == null)
		sortByTitle = true;
	dbFunctions dbConnection =new dbFunctions();
	dbConnection.make_connection("jdbc:mysql://localhost:3306/moviedb", "root", "root");
	ArrayList<Movie> movieList = dbConnection.getmovieByTilte(0, 20);
%>
	<table class="movie_table" style="width:100%">
<%
	for(Movie currentMovie : movieList )
	{
%>
	<tr>
	<td><img src="<%out.print(currentMovie.getBanner_url());%>"height="42" width="42"></td>
	<td><b><%out.print(currentMovie.getId()); %></b></td>
	<td><a href=<%out.print(currentMovie.getTrailer_url()); %>><%out.print(currentMovie.getTitle());%></a>
	<td><%out.print(currentMovie.getYear()); %></td>
	<td><%out.print(currentMovie.getDirector()); %></td>
	</tr>
	
<%
	}
%>
</table>
</body>
</html>