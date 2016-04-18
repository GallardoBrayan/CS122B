<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="fabflix.*"%>
<%
	dbFunctions dbConnection = new dbFunctions();
	dbConnection.make_connection("jdbc:mysql://localhost:3306/moviedb", "root", "root");
%>
<div class="logo_area">
<div style="float:left">

<img src="/fabflix/resources/logo.png" height="42" width="42">
    Fabflix
  <a href="/fabflix/search.jsp">Search</a>
<a href="/fabflix/Browse.jsp">Browse</a>
</div>
<div style="float:right">

    <%if(session.getAttribute("userToken") == null) 
    {%>
   <a href="/fabflix/login.jsp">Login</a>
    <% }
    else
    	{
    	User currentUser = (User) session.getAttribute("userToken");
    %>
   <%out.print(currentUser.getFirst_name() + "  " + currentUser.getLast_name()); %> ( Logout )
    Cart
    <%} %>

</div>
</div>

<div style="clear: both;"></div>

