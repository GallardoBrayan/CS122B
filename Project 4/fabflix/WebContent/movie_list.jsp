<%@page
	import="java.sql.*,
 javax.sql.*,
 java.util.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*,
 fabflix.*"%>

<html>
<head>
<title>Display Movie Titles</title>
<%@ include file="/header.jsp"%>
<%!@SuppressWarnings("unchecked")%>
</head>

<body>
	<h3>Movie_list</h3>
	<%
		SearchParameters curSearch = (SearchParameters) session.getAttribute("curSearch");
		if (curSearch == null  && session.getAttribute("userToken") != null) {
			response.sendRedirect("MainPage");
		} else if (curSearch != null)
		{
			if (curSearch.getFromBrowse()) 
			{
				if (curSearch.getByTitle()) 
				{
	%>
				<br>
				<div class="atoz-content">
				Browse by Title
					<div class="atoz-letter">
						<%
							for (int i = 0; i < 10; i++) 
							{
						%>
								<a href="run_search?browse=1&amp;title=<%=i%>"><%=i%></a>&nbsp;
						<%
							}
							for (char i = 'A'; i <= 'Z'; i++) 
							{
						%>
								<a href="run_search?browse=1&amp;title=<%=i%>"><%=i%></a>&nbsp;
						<%
							}
						%>
					</div>
				</div>
		<%
				}
				else 
				{
		%>

					<br>
					<div class="genre_list-content">
					<div class="genre_list">
						<%
							ArrayList<String> genreList = dbConnection.getGenreList();
							for (String currentGenre : genreList) 
							{
						%>
								<a href="run_search?browse=1&amp;genre=<%=currentGenre%>"><%=currentGenre%></a>&nbsp;
						<%
							}
						%>
					</div>
					</div>
		<%
				}
			}
		%>
			Results shown per page:
			<%=curSearch.getMoviePerPage()%>
			<form action="run_search" method="get">
				Select Amount of results shown per page: 
				<select name="movies_per_page" onchange="this.form.submit()">
				<option selected="selected" disabled="disabled">Select a value</option>
				<option value="10">10</option>
				<option value="25">25</option>
				<option value="50">50</option>
				<option value="100">100</option>
			</select>
			</form>

		<p>
			<a href="run_search?sort_type=year">Sort by release Year</a>
		</p>
		<table border="1">
			<tr>
				<th>Id</th>
				<th><a href="run_search?sort_type=title">Title</a></th>
				<th>Director</th>
				<th>Genres</th>
				<th>Cart</th>
			</tr>

		<%
			LinkedHashMap<Integer, Movie> movie_list = (LinkedHashMap<Integer, Movie>) session.getAttribute("movie_list");
			if(movie_list == null)
			{
				movie_list = new LinkedHashMap<Integer, Movie>();
			}
			for (Integer id : movie_list.keySet()) 
			{
				Movie movie = movie_list.get(id);
		%>
				<tr>
					<td><%=id%></td>
					<td>
						<div id="movie_pop_up_area<%=id%>"  onmouseout="popOutOff(<%=id%>);" >
							<a href="getMovie?movieid=<%=id%>" onmouseover="popUpDetails(<%=id%>);"><%=movie.getTitle()%></a>
								<div id="movie_pop_up<%=id%>" class="movie_pop_up"  onmouseout="popOutOff(<%=id%>);" ></div>
						</div>
					</td>
					<td><%=movie.getDirector()%></td>
					<td>
						<%
							String outputString = "";
									for (String genre : movie.getGenres()) {
										outputString += genre + ", ";
									}
									if (outputString.contains(","))
										outputString = outputString.substring(0, outputString.length() - 2);
									out.print(outputString);
						%>
					</td>
					<td><a href="CartServlet?movie_id=<%=id%>">Add To Cart</a></td>
				</tr>

		<%
			}
		%>
		</table>
		<p>
			<%
				int page_number = Integer.parseInt(curSearch.getCurrentPage());
				if (page_number > 0) {
			%>
			<a href="run_search?page_number=<%=page_number - 1%>">Prev</a>
			<%
				}
			%>
		</p>
		<p>
			Current Page :<%=page_number + 1%></p>
		<p>
		<%
			if (movie_list.size() >= Integer.parseInt(curSearch.getMoviePerPage())) {
		%>
			<a href="run_search?page_number=<%=page_number + 1%>">Next</a>
		<%
			}
		
		%>
		</p>
	<%
		}
		
	%>
</body>
<%@ include file="footer.jsp"%>
</html>
