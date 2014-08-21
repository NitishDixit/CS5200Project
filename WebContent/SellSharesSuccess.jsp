<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<f:view>
		<center>
			<br> <br> <br> <br>
			<h2>
				Sell order for the stock
				<h:outputText value="#{stockQuote.symbol}"></h:outputText>
				was successful
			</h2>
			<br>
			<br>
			<h:outputLink value="CustomerWelcomePage.jsp">Back to Home</h:outputLink>
		</center>

	</f:view>
</body>
</html>