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
				<h2>
					KeyStats for the stock
					<h:outputText value="#{stockKeyStatsBean.stock_tickr}"></h:outputText>
				</h2>
				<h:dataTable border="1" value="#{stockKeyStatsBean.stockKeyStats}"
					var="table">

					<h:column id="column1">
						<f:facet name="header">
							<h:outputText value="Statistics for #{stockKeyStatsBean.stock_tickr}"></h:outputText>
						</f:facet>
						<h:outputText value="#{table.statistics}"></h:outputText>
					</h:column>

					<h:column id="column2">
						<f:facet name="header">
							<h:outputText value="Term"></h:outputText>
						</f:facet>
						<h:outputText value="#{table.term}"></h:outputText>
					</h:column>

					<h:column id="column3">
						<f:facet name="header">
							<h:outputText value="Content"></h:outputText>
						</f:facet>
						<h:outputText value="#{table.content}"></h:outputText>
					</h:column>
				</h:dataTable>				
			</h:form>
		</center>
		<h:outputLink value="StockQuote.jsp">Back</h:outputLink>
	</f:view>
</body>
</html>