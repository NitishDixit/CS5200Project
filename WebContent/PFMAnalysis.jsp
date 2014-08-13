<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PFM Analysis</title>
</head>
<body>
	<f:view>
		<center>
			<h:form>
				<h3>
					<h:outputText value="Stocks Analysis Page"></h:outputText>
				</h3>
				<h:panelGrid border="1" columns="2">
					<h:outputText value="Enter Stock Quote"></h:outputText>
					<h:inputText value="#{stockKeyStatsBean.stock_tickr}"></h:inputText>
				</h:panelGrid>
				<br>
				<h:form>
					<h:commandButton value="Get Key Stats"
						action="#{stockKeyStatsBean.getKeyStats}" type="submit"></h:commandButton>
					<br>
					<br>
					<h:outputText value="#{stockKeyStatsBean.failMessage}"></h:outputText>
				</h:form>
			</h:form>
		</center>
		<br>
		<br>
		<br>
		<br>
		<h:outputLink value="StockQuote.jsp">Back</h:outputLink>
	</f:view>
</body>
</html>