<%@page import="java.sql.*,
 javax.sql.*,
  java.util.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
%> 


<style>

#form_div
{
	text-align: center;
}
</style>
<html>
<body>
<%
 ArrayList<String> display_rules = (ArrayList<String>)session.getAttribute("display_rules");
 int movies_per_page = Integer.parseInt(display_rules.get(0));
%>

<center><h3>Movie_list</h3></center>
Results shown per page: <%=movies_per_page%>
<form action="servlet/run_search" method="get">
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
<th>Id</th>
<th><a href="servlet/run_search?title_sort=title_sort">Title</a></th>
<th><a href="servlet/run_search?year_sort=year_sort">Year</a></th>
<th>Director</th>
<th>Genres</th>
<th>Stars</th>
</tr>

<% ArrayList<LinkedHashMap<String,ArrayList<String>>> movies = (ArrayList<LinkedHashMap<String,ArrayList<String>>>)session.getAttribute("movies"); 
   int page_number = Integer.parseInt(display_rules.get(1));
   int row_count = 0;
%>
<% for(Map.Entry<String, ArrayList<String>> movie_row : movies.get(0).entrySet()) { 
		row_count = row_count + 1;
		String id = movie_row.getKey();
		ArrayList<String> movie_info = movie_row.getValue();
		ArrayList<String> star_info = movies.get(1).get(id);
		ArrayList<String> genre_info = movies.get(2).get(id);
%>
<tr>
	<td><%=id%></td>
	<td><%=movie_info.get(0)%></td>
	<td><%=movie_info.get(1) %></td>
	<td><%=movie_info.get(2) %></td>
	<td><%=genre_info.toString().replace("[", "").replace("]","")%></td>
	<td><%
			for(int i=0; i < star_info.size(); i = i + 1)
			{
				String name = star_info.get(i);
				if(!(i + 1 == star_info.size()))
					name = name + ",";
			%>
			<%=name%>
		  <%}%>
	</td>
</tr>

<% } %>
</table>
<p>Current Page :<%=page_number+1%><p></br>
<%if(page_number > 0){%>
<a href = "servlet/run_search?page_number=<%=page_number-1%>" >Prev</a>
<% }%>

<%if(!(row_count < movies_per_page)){%>
<a href = "servlet/run_search?page_number=<%=page_number+1%>" >Next</a>
<% } %>
</body>
</html>
