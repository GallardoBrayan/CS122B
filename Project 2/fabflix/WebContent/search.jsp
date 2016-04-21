<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search for a Movie</title>
<%@ include file="/header.jsp" %>
</head>
<body>
<center><h3>Search</h3></center>

<% 
	session.setAttribute("movie_list", null);
	session.setAttribute("curSearch", null);
%>

  <form action="run_search" method="get">
  Title: <input type="text" name="title"/><br>
  Year: <input type="text" name="year"></input><br>
  Director: <input type="text" name="director"></input><br>
  First Name of Star: <input type="text" name="first_name"></input><br>
  Last Name of Star: <input type="text" name="last_name"></input><br>
  <input type="submit" name="submit" value="Search">
  </form>
</body>
<%@ include file="/footer.jsp" %>
</html>