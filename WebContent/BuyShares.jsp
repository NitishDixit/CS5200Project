<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Buy Shares</title>
</head>
<body>
	<f:view>
		<center>
			<h:panelGrid border="3" columns="2">
				<h:outputText value="Symbol"></h:outputText>
				<h:outputText value="#{stockQuote.symbol}"></h:outputText>
				<h:outputText value="Name"></h:outputText>
				<h:outputText value="#{stockQuote.name}"></h:outputText>
				<h:outputText value="Days Low"></h:outputText>
				<h:outputText value="#{stockQuote.daysLow}"></h:outputText>
				<h:outputText value="Days High"></h:outputText>
				<h:outputText value="#{stockQuote.daysHigh}"></h:outputText>
				<h:outputText value="Last Trade Price"></h:outputText>
				<h:outputText value="#{stockQuote.lastTradePrice}"></h:outputText>
				<h:outputText value="Change"></h:outputText>
				<h:outputText value="#{stockQuote.change}"></h:outputText>
				<h:outputText value="Year High"></h:outputText>
				<h:outputText value="#{stockQuote.yearHigh}"></h:outputText>
				<h:outputText value="Year Low"></h:outputText>
				<h:outputText value="#{stockQuote.yearLow}"></h:outputText>
				<h:outputText value="Stock Exchange"></h:outputText>
				<h:outputText value="#{stockQuote.stockExchange}"></h:outputText>
			</h:panelGrid>
			<br>
			<h:form>
			<h:panelGrid border="1" columns="1">
				<h:outputText value="Buy shares for this stock" />
			</h:panelGrid>
			<h:panelGrid border="1" columns="3">
				<h:outputText value="quantity" />
				<h:inputText value="#{stockQuote.quantity}"></h:inputText>
			</h:panelGrid>
			
				<h:commandButton value="Place Order" type="submit"
					action="#{stockQuote.placeBuyOrder}">
				</h:commandButton>
				<br><br>
				<h:outputText value="#{stockQuote.failMessage}"></h:outputText>
			</h:form>
			<br> <br> <br>
		</center>
		<h:outputLink value="SearchShares.jsp">Back</h:outputLink>
	</f:view>
</body>
</html>