<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="fabflix.*,java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Browse Movie Titles</title>
<%@ include file="/header.jsp"%>
</head>
<body>

	<%
		String temp = request.getParameter("sortBy");
		Boolean sortByTitle = Boolean.valueOf(temp == null ? "true" : temp );
		if (sortByTitle == null)
			sortByTitle = true;
	%>
	Browse by
	<form action="Browse" method="get">
	<select name="sortBy" id="sortBySelect" size="1" class="Selector"
		style="width: 82px;" onchange="this.form.submit()"
		>
		<option value="true"<%if(sortByTitle) out.print("selected");%>>Title</option>
		<option value="false"<%if(!sortByTitle) out.print("selected");%>>Genre</option>
	</select>
	</form>
	
	<% 
		temp = request.getParameter("page");
		Integer pageNumber = temp == null ? 0 : Integer.parseInt(temp);
		Integer startNumber = pageNumber == null ? 0 : pageNumber * 20;
		Integer numberOfMovies = 0;
		ArrayList<Movie> movieList = null;

		if (sortByTitle) {
	%>
	<p>
	<div class="atoz-content">
		<div class="atoz-letter">
			<a href=${pageContext.request.requestURL}?title=0>0</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=1>1</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=2>2</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=3>3</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=4>4</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=5>5</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=6>6</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=7>7</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=8>8</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=9>9</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=A>A</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=B>B</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=C>C</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=D>D</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=E>E</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=F>F</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=G>G</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=H>H</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=I>I</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=J>J</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=K>K</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=L>L</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=M>M</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=N>N</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=O>O</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=P>P</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=Q>Q</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=R>R</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=S>S</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=T>T</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=U>U</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=V>V</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=W>W</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=X>X</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=Y>Y</a>&nbsp; <a
				href=${pageContext.request.requestURL}?title=Z>Z</a>
		</div>
	</div>
	<p>
		<%
			temp =request.getParameter("title") ;
			String letter = temp == null ? "A" : temp;
			movieList = dbConnection.getmovieByTilte(startNumber, 20, letter);
			numberOfMovies = dbConnection.countMovieByTilte(letter);
		%>
	
	<table class="movie_table" style="width: 100%">
		<%
			for (Movie currentMovie : movieList) 
			{
		%>
		<tr>
			<td><img src="<%out.print(currentMovie.getBanner_url());%>"
				height="42" width="42"></td>
			<td><b> <%
 	out.print(currentMovie.getId());
 %>
			</b></td>
			<td><a href=<%out.print(currentMovie.getTrailer_url());%>> <%
 	out.print(currentMovie.getTitle());
 %>
			</a>
			<td>
				<%
					out.print(currentMovie.getYear());
				%>
			</td>
			<td>
				<%
					out.print(currentMovie.getDirector());
				%>
			</td>
		</tr>

		<%
	}
%>
	</table>
	<p>
	<div class="page_numbers-content">
		<div class="page_numbers">
			<% 
			int numberOfPages = numberOfMovies / 20 + 1;
			for (int i = 0; i < numberOfPages; i++) {
		%>

			<a href=${pageContext.request.requestURL}?
				onclick="this.href=this.href+'title=<% out.print(letter);%>&page=<% out.print(i);%>'">
				<% out.print(i+1); %>
			</a>&nbsp;
			<%
			}
		}
		else{
			
	%>
	<p>
	<div class="genre_list-content">
		<div class="genre_list">
		<%ArrayList<String> genreList = dbConnection.getGenreList(); 
		for(String currentGenre :genreList)
			{%>
			<a href=${pageContext.request.requestURL}?genre=<%out.print(currentGenre); %>><%out.print(currentGenre); %></a>&nbsp; 
			<%} %>
		</div>
	</div>
	<p>
		<%
			temp =request.getParameter("genre") ;
			String genre = temp == null ? "" : temp;
			movieList = dbConnection.getMovieByGenre(startNumber, 20, genre);
			numberOfMovies = dbConnection.countMovieByTilte(genre);
		%>
	
	<table class="movie_table" style="width: 100%">
		<%
			for (Movie currentMovie : movieList) 
			{
		%>
		<tr>
			<td><img src="<%out.print(currentMovie.getBanner_url());%>"
				height="42" width="42"></td>
			<td><b> <%
 	out.print(currentMovie.getId());
 %>
			</b></td>
			<td><a href=<%out.print(currentMovie.getTrailer_url());%>> <%
 	out.print(currentMovie.getTitle());
 %>
			</a>
			<td>
				<%
					out.print(currentMovie.getYear());
				%>
			</td>
			<td>
				<%
					out.print(currentMovie.getDirector());
				%>
			</td>
		</tr>

		<%
	}
%>
	</table>
	<p>
	<div class="page_numbers-content">
		<div class="page_numbers">
			<% 
			int numberOfPages = numberOfMovies / 20 + 1;
			for (int i = 0; i < numberOfPages; i++) {
		%>

			<a href=${pageContext.request.requestURL}?
				onclick="this.href=this.href+'title=<% out.print(genre);%>&page=<% out.print(i);%>'">
				<% out.print(i+1); %>
			</a>&nbsp;
			<%
			}
		}
		%>

		</div>
	</div>
</body>
<%@ include file="/footer.jsp"%>
</html>