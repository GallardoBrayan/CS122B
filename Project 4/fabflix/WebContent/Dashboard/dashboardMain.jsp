<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="fabflix.*, java.util.*, java.text.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Dashboard</title>
</head>
<body>

	<%
		User userToLogin = (User) session.getAttribute("empToken");
		if (userToLogin == null) {
			response.sendRedirect( request.getContextPath()+ "/_dashboard");
			userToLogin = new User();
		}
		
		Star newStar = (Star)  session.getAttribute("StarAdded");
		Boolean fromStar = (Boolean) session.getAttribute("fromStarAdd");
		
		if(newStar != null && fromStar)
		{
			%>
	<h3><%=newStar.getFirst_name()%>
		<%=newStar.getLast_name()%>
		was add successfully
	</h3>
	<% 
		}
		else if (newStar != null)
		{

			%>
	<h3><%=newStar.getFirst_name()%>
		<%=newStar.getLast_name()%>
		was <b>not</b> add successfully
	</h3>
	<% 
		}
		
		String MovieAddResponse =(String)session.getAttribute("AddResult");
		if(MovieAddResponse != null)
		{
			%>
	<h3><%=MovieAddResponse %></h3>
	<% 
		}
		session.setAttribute("AddResult", null);
		session.setAttribute("StarAdded", null);
		session.setAttribute("fromStarAdd", null);
			
	%>
	<div align="center">
		<table>
			<tr>
				<td width="35">&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<th>
					<div align="center">
						Hello <b><%=userToLogin.getFirst_name()%>!</b> <br>Welcome to
						FablFlix!
					</div>
				</th>
			</tr>
			<tr>
				<td width="35">&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<th>
					<form action="insertstar">
						<input type="submit" value="Insert a Star into the movie Database">
					</form>
				</th>
			</tr>
			<tr>
				<td width="35">&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td width="35">&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<th>
					<form action="insertmovie">
						<input type="submit"
							value="Insert a Movie into the movie Database">
					</form>
				</th>
			</tr>
			<tr>
				<td width="35">&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<%
				@SuppressWarnings("unchecked") 
				Map<String,Map<String,String>> metaData = (Map<String,Map<String,String>> )session.getAttribute("metaData");
			if(metaData == null)
			{
			%>
			<tr>
				<th>
					<form action="DashboardFunctions">
						<input type="submit" name="submit"
							value="Provide the metadata of the database">
					</form>
				</th>
			</tr>
			<%
			}
			%>
		</table>
		<%
		if(metaData != null)
			{
		%>

		<h3>Database Metadata</h3>
		<%
		for(String tableName : metaData.keySet())
		{
		%>
		<h4>
			Table:
			<%=tableName %></h4>
		<table>
			<%for(String rowName : metaData.get(tableName).keySet()) 
		{
		%>
			<tr>
				<td><%=rowName %></td>
				<td><%=metaData.get(tableName).get(rowName) %></td>
			</tr>
			<%
			}
		%>
		</table>
		<%
			}
			%>
		<%
			}
			%>
	</div>
</body>
</html>