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
			<br> <br> <br>
			<h3>
				Buy order for
				<h:outputText value="#{stockQuote.symbol}"></h:outputText>
				for quantity
				<h:outputText value="#{stockQuote.quantity}"></h:outputText>
				processed successfully ..!!
			</h3>
			<br><br><br><br>		
		<h4>Would you like make another buy order?  <h:outputLink value="SearchShares.jsp">click here</h:outputLink></h4>
		<br><br>
		<h4><h:outputLink value="CustomerWelcomePage.jsp">Back to Home Page</h:outputLink></h4>
		</center>
	</f:view>
</body>
</html>