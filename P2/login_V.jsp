<%@page import="java.sql.*,
 javax.sql.*,
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
<center><h3>Login</h3></center>
<%
  if((String)request.getSession().getAttribute("error") != null){
%>
	<h3>ERRR</h3>
<% }else{%>

<h3>workkk</h3>
<%}%>

<div id="form_div">
  <form action="/test/servlet/login" method="post">
  Username: <input type="text" name="username"/></br>
  Password: <input type="password" name="pass"></input></br>
  <input type="submit" name="submit">
  </form>
 </div>


</body>
</html>
