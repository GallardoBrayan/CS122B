<%@page
	import="java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*"%>
<style type="text/css">
#form_div {
	text-align: center;
}
</style>
<html>
<head>
<script src='https://www.google.com/recaptcha/api.js'></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>FabFlix</title>
<%@ include file="header.jsp"%>
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
		} else if ("mustLogin".equals(error)) {
			session.setAttribute("error", null);
	%>
	<h3>You must login to access this site.</h3>
	<%
		} else if ("reCAPTHAFailed".equals(error)) {
			session.setAttribute("error", null);
	%>
	<h3>You must verify that you are not a robot to login..</h3>
	<%
		}
	%>
	<div align="center">
		<fieldset style="width: 500px" class="field">
			<legend>Existing Member Sign In:</legend>

			<div id="form_div">
				<form action="LoginHandler" method="post">
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
						<tr>
							<td width="35"><div class="tinyspacer">&nbsp;</div></td>
							<td width="100"><div class="tinyspacer">&nbsp;</div></td>
							<td><div class="tinyspacer">&nbsp;</div></td>
						</tr>
						<tr>
							<td width="35">&nbsp;</td>
							<td><div class="g-recaptcha"
									data-sitekey="6LdKZB4TAAAAAH5T35loD6-ljrbujqHPYYgzZMwz"></div>
							</td>
							<td></td>
						</tr>
						<tr>
							<td width="35">&nbsp;</td>
							<td><input type="submit" name="Login" value="Login">
							</td>
							<td></td>
						</tr>
						<tr>
							<td width="35"><div class="tinyspacer">&nbsp;</div></td>
							<td width="100"><div class="tinyspacer">&nbsp;</div></td>
							<td><div class="tinyspacer">&nbsp;</div></td>
						</tr>
					</table>
				</form>
			</div>
		</fieldset>
		&nbsp;<br />
	</div>
</body>
<%@ include file="footer.jsp"%>
</html>
