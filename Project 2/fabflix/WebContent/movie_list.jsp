<%@page import="java.sql.*,
 javax.sql.*,
 java.util.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*,
 fabflix.*"
%> 


<style>

#form_div
{
	text-align: center;
}
</style>
<html>
<head>
<title>Display Movie Titles</title>
<%@ include file="/header.jsp"%>
</head>
<body>
<h3>Movie_list</h3>
<%
	SearchParameters curSearch = (SearchParameters)session.getAttribute("curSearch");
%>
Results shown per page: <%=curSearch.getMoviePerPage()%>
<form action="run_search" method="get">
  Select Amount of results shown per page: <select name="movies_per_page">
  	<option selected="selected" disabled="disabled">Select a value</option>
    <option value="10">10</option>
    <option value="25">25</option>
    <option value="50">50</option>
    <option value="100">100</option>
  </select>
  <br><br>
  <input type="submit">
</form>

<table border="1">
<tr>
<th></th>
<th>Id</th>
<th><a href="run_search?sort_type=title">Title</a></th>
<th><a href="run_search?sort_type=year">Year</a></th>
<th>Director</th>
<th>Genres</th>
<th>Stars</th>
</tr>

<% 
LinkedHashMap<Integer, Movie> movie_list = (LinkedHashMap<Integer, Movie>)session.getAttribute("movie_list"); 
 
for(Integer id: movie_list.keySet())
{
	Movie movie = movie_list.get(id);
%>
<tr>
	<td><img height="42" width="42" src="<%=movie.getBanner_url()%>"></td>
	<td><%=id%></td>
	<td><%=movie.getTitle()%></td>
	<td><%=movie.getYear() %></td>
	<td><%=movie.getDirector()%></td>
	<td><%
		String outputString ="";
		for(String genre : movie.getGenres())
		{
			outputString += genre + ", ";
		}
		outputString = outputString.substring(0,outputString.length() - 2);
		out.print(outputString);
	%></td>
	<td><%
		outputString ="";
		for(String star : movie.getStars())
		{
			outputString += star + ", ";
		}
		outputString = outputString.substring(0,outputString.length() - 2);
		out.print(outputString);
			%>
	</td>
</tr>

<% } %>
</table>

</body>
<%@ include file="/footer.jsp"%>
</html>
