<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.awt.Image,java.net.URL,javax.swing.ImageIcon"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>FabFlix > Movie Details</title>
<%@ include file="header.jsp"%>
<%! @SuppressWarnings("unchecked") %>
</head>
<body>
	<%
		Integer movieid = null;
		try {
			movieid = Integer.parseInt(request.getParameter("movieid"));
		} catch (Exception e) {
			movieid = null;
		}

		if (movieid != null) {
			LinkedHashMap<Integer, Movie> movie_list = (LinkedHashMap<Integer, Movie>) session
					.getAttribute("movie_list");
			Movie movieToDispay;
			if (movie_list != null && movie_list.containsKey(movieid)) {
				movieToDispay = movie_list.get(movieid);
			} else {
				movieToDispay = dbConnection.returnMovieFromID(movieid);
			}
			if (movieToDispay != null) {
	%>
	<table class="movie_table" >
		<tr class="movie_table">
		<td class="movie_table" rowspan="7"><img src="<%
		if(movieToDispay.getBanner_url() != null )
	    {
		    try{
		      Image image = new ImageIcon(new URL(movieToDispay.getBanner_url())).getImage();
		      if(image.getWidth(null) != -1)
		      {
		    	  out.print(movieToDispay.getBanner_url()) ;
		      }
		    }catch(Exception e)
		    {
		    	e.printStackTrace();
		    }
	    }else
	    {
	    	out.print("resources/letters/" + movieToDispay.getTitle().toUpperCase().charAt(0) +".jpg");
	    }
	    %>"
				alt=""></td>
		</tr>

		<tr class="movie_table">
			<td class="movie_table">Title:</td>
			<td class="movie_table"><%=movieToDispay.getTitle()%></td>
		</tr>
		<tr class="movie_table">
			<td class="movie_table">Year:</td>
			<td class="movie_table"><%=movieToDispay.getYear()%></td>
		</tr>
		<tr class="movie_table">
			<td class="movie_table">Director:</td>
			<td class="movie_table"><%=movieToDispay.getDirector()%></td>
		</tr>
		<tr class="movie_table">
			<td class="movie_table">ID:</td>
			<td class="movie_table"><%=movieid%></td>
		</tr>
		<tr class="movie_table">
			<td class="movie_table">Stars:</td>
			<td class="movie_table">
				<%
					int i = 1;
							for (String star : movieToDispay.getStars()) {
				%> <a
				href="getStar?movieid=<%=movieToDispay.getId()%>&amp;star_name=<%=star%>"><%=star%></a>
				<%
					if (!(i == movieToDispay.getStars().size())) {
									out.print(", ");
								}
								++i;
							}
				%>
			</td>
		</tr>
		<tr class="movie_table">
			<td class="movie_table">Genre:</td>
			<td class="movie_table">
				<%
					String outputString = "";
							for (String Genre : movieToDispay.getGenres()) {
								outputString += Genre + ", ";
							}
							if (outputString.contains(","))
								outputString = outputString.substring(0, outputString.length() - 2);
							out.print(outputString);
				%>
			</td>
		</tr>
		<tr class="movie_table">
			<td class="movie_table">Price:</td>
			<td class="movie_table">$<%=dbConnection.getMoviePrice(movieid)%></td>
			<td class="movie_table"><a href="CartServlet?movie_id=<%=movieid%>">Add To Cart</a></td>
		</tr>

	</table>
	<%
		}

			else {
	%>
	<h2>
		No Movie To Display with id =<%=movieid%></h2>
	<%
		}
		} else {
	%>
	<h2>No movie id provided</h2>
	<%
		}
	%>
</body>
<%@ include file="footer.jsp"%>
</html>