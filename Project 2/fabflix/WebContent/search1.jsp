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
<center><h3>Search</h3></center>



  <form action="/test/servlet/run_search" method="get">
  Title: <input type="text" name="title"/></br>
  Year: <input type="text" name="year"></input></br>
  Director: <input type="text" name="director"></input></br>
  First Name: <input type="text" name="first_name"></input></br>
  Last Name: <input type="text" name="last_name"></input></br>
  <input type="submit" name="submit">
  </form>


</body>
</html>
