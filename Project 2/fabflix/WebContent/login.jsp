<%@page
	import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"
 %>
<style type="text/css">
#form_div {
	text-align: center;
}
</style>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>FabFlix</title>
<%@ include file="/header.jsp"%>
</head>
<body>
	<h3>Fabflix Login</h3>
	<%
		String error = (String) request.getSession().getAttribute("error");
		if ("loginError".equals(error)) {
			session.setAttribute("error", null);
	%>
	<h3>Invalid User name or password</h3>
	<%
		}
	%>
	<div align="center">
		<fieldset style="width: 500px" class="field">
			<legend>Existing Member Sign In:</legend>

			<div id="form_div">
				<form action="MainPage" method="post">
					<table style="font-size: small">
						<tr>
							<td width="35">&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td width="35">&nbsp;</td>
							<td width="100">User name(email address): <input type="text"
								name="username" /></td>
						</tr>
						<tr>
							<td width="35"><div class="tinyspacer">&nbsp;</div></td>
							<td width="100"><div class="tinyspacer">&nbsp;</div></td>
							<td><div class="tinyspacer">&nbsp;</div></td>
						</tr>
						<tr>
							<td width="35">&nbsp;</td>
							<td width="100"><div align="left">
									Password: <input type="password" name="pass" />
								</div></td>
							<td></td>
						</tr>
						<tr>
							<td width="35">&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
					</table>
					<input type="submit" name="Login" value="Login">
				</form>
			</div>
		</fieldset>
		&nbsp;<br />
	</div>
</body>
</html>
