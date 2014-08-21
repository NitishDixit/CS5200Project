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
	<h:form>
		<center>
			<h2>Suggested Stocks from the PortFolio Manager</h2>
			<h:dataTable border="1"
					value="#{suggestedStocksBean.suggestedStockList}" var="table">

					<h:column id="column">
						<f:facet name="header">
							<h:outputText value="Suggested Date"></h:outputText>
						</f:facet>
						<h:outputText value="#{table.suggestedDate}"></h:outputText>
					</h:column>

					<h:column id="column2">
						<f:facet name="header">
							<h:outputText value="Suggestions from PFM"></h:outputText>
						</f:facet>
						<h:outputText value="#{table.comments}"></h:outputText>
					</h:column>					
				</h:dataTable>		
		<h:outputText value="#{suggestedStocksBean.failMessage}"></h:outputText>
		</center>
		</h:form>
	</f:view>
</body>
</html>