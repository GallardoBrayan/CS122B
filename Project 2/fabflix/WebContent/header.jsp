<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="fabflix.*"%>
<%
	dbFunctions dbConnection = new dbFunctions();
	dbConnection.make_connection("jdbc:mysql://localhost:3306/moviedb", "root", "root");
%>
<table>
  <tr>
    <th>FabFlix</th>
    <th><a href="/fabflix/search.jsp">Search</a></th>
    <th><a href="/fabflix/Browse.jsp">Browse</a></th>
    <%if(session.getAttribute("userToken") == null) 
    {%>
    <th>Login</th>
    <% }
    else
    	{
    	User currentUser = (User) session.getAttribute("userToken");
    %>
    <th><%out.print(currentUser.getFirst_name() + "  " + currentUser.getLast_name()); %>(Logout)</th>
    <th>Cart</th>
    <%} %>
  </tr>
</table>

