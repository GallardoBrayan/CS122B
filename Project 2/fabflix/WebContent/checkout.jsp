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
<center><h3>Checkout</h3></center>

<h1>Enter Credit Card Information</h1>
<form action="checkout" method="post">
  First Name: <input type="text" name="first_name"></input><br>
  Last Name: <input type="text" name="last_name"></input><br>
  Credit Card Number: <input type="text" name="cc"/><br>
  Expiration Date: <input type="text" name="exp"></input><br>
  <input type="submit" name="submit">
 </form>

</body>
<%@ include file="/footer.jsp" %>
</html>