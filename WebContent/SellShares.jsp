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
			<h:form>
				<h3>
					<h:outputText value="Place Sell Order"></h:outputText>
				</h3>
				<h:panelGrid border="1" columns="2">
					<h:outputText value="Company Id "></h:outputText>
					<h:inputText value="#{stockQuote.symbol}"></h:inputText>
					<h:outputText value="Quantity"></h:outputText>
					<h:inputText value="#{stockQuote.quantity}"></h:inputText>
				</h:panelGrid>
				<h:commandButton value="Sell Order"
					action="#{stockQuote.placeSellOrder}" type="submit"></h:commandButton>
			</h:form>
			<h:outputText value="#{stockQuote.failMessage}"></h:outputText><br><br><br><br>			
		</center>
    	<h:outputLink value="ViewPortfolio.jsp">Back</h:outputLink>
	</f:view>
</body>
</html>