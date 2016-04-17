<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="fabflix.*,java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Browse Movie Titles</title>
<%@ include file="/header.jsp" %>
</head>
<body>
	<div class="atoz-content">
		<div class="atoz-letter">
			<a href="/fabflix/Browse.jsp?title=0">0</a><a
				href="/fabflix/Browse.jsp?title=1">1</a><a
				href="/fabflix/Browse.jsp?title=2">2</a><a
				href="/fabflix/Browse.jsp?title=3">3</a><a
				href="/fabflix/Browse.jsp?title=4">4</a><a
				href="/fabflix/Browse.jsp?title=5">5</a><a
				href="/fabflix/Browse.jsp?title=6">6</a><a
				href="/fabflix/Browse.jsp?title=7">7</a><a
				href="/fabflix/Browse.jsp?title=8">8</a><a
				href="/fabflix/Browse.jsp?title=9">9</a><a
				href="/fabflix/Browse.jsp?title=A">A</a><a
				href="/fabflix/Browse.jsp?title=B">B</a><a
				href="/fabflix/Browse.jsp?title=C">C</a><a
				href="/fabflix/Browse.jsp?title=D">D</a><a
				href="/fabflix/Browse.jsp?title=E">E</a><a
				href="/fabflix/Browse.jsp?title=F">F</a><a
				href="/fabflix/Browse.jsp?title=G">G</a><a
				href="/fabflix/Browse.jsp?title=H">H</a><a
				href="/fabflix/Browse.jsp?title=I">I</a><a
				href="/fabflix/Browse.jsp?title=J">J</a><a
				href="/fabflix/Browse.jsp?title=K">K</a><a
				href="/fabflix/Browse.jsp?title=L">L</a><a
				href="/fabflix/Browse.jsp?title=M">M</a><a
				href="/fabflix/Browse.jsp?title=N">N</a><a
				href="/fabflix/Browse.jsp?title=O">O</a><a
				href="/fabflix/Browse.jsp?title=P">P</a><a
				href="/fabflix/Browse.jsp?title=Q">Q</a><a
				href="/fabflix/Browse.jsp?title=R">R</a><a
				href="/fabflix/Browse.jsp?title=S">S</a><a
				href="/fabflix/Browse.jsp?title=T">T</a><a
				href="/fabflix/Browse.jsp?title=U">U</a><a
				href="/fabflix/Browse.jsp?title=V">V</a><a
				href="/fabflix/Browse.jsp?title=W">W</a><a
				href="/fabflix/Browse.jsp?title=X">X</a><a
				href="/fabflix/Browse.jsp?title=Y">Y</a><a
				href="/fabflix/Browse.jsp?title=Z">Z</a>
		</div>
	</div>
	<%
		Boolean sortByTitle = (Boolean) session.getAttribute("sortByTitle");
		if (sortByTitle == null)
			sortByTitle = true;
		String letter = request.getParameter("title");
		ArrayList<Movie> movieList = dbConnection.getmovieByTilte(0, 20,(letter != null ? letter :"A"));
	%>
	<table class="movie_table" style="width: 100%">
		<%
			for (Movie currentMovie : movieList) {
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
</body>
<%@ include file="/footer.jsp" %>
</html>