<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search for a Movie</title>
<%@ include file="header.jsp"%>
</head>
<body>
	<center>
		<h3>Checkout</h3>
	</center>


<%
Cart shopping_cart = (Cart) session.getAttribute("shopping_cart");
if (shopping_cart == null || shopping_cart.get_item_count() == 0) {
	shopping_cart = new Cart();
%>
	<h3>Nothing to checkout.  Your cart is empty.</h3>
	<a href="search" style="margin-right:10px;">Return to Search</a><a href="Browse">Return to Browse</a>
<% } else {%>
	<h1>Enter Credit Card Information</h1>
	<form action="CheckoutServlet" method="post">
		First Name: <input type="text" name="first_name" /><br> Last
		Name: <input type="text" name="last_name" /><br> Credit Card
		Number: <input type="text" name="cc" /><br> Expiration Date: <input
			type="text" name="exp" /><br> <input type="submit" name="submit">
	</form>
<% }%>
</body>
<%@ include file="footer.jsp"%>
</html>