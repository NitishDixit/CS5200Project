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
			<h2>Transaction History for the user <h:outputText value="#{loginBean.username}"></h:outputText> </h2>
				<h:dataTable border="1"
					value="#{custTransactionHistoryBean.custTransactionList}" var="table1">

					<h:column id="column">
						<f:facet name="header">
							<h:outputText value="Company Symbol"></h:outputText>
						</f:facet>
						<h:outputText value="#{table1.stockTickr}"></h:outputText>
					</h:column>

					<h:column id="column2">
						<f:facet name="header">
							<h:outputText value="Transaction Type"></h:outputText>
						</f:facet>
						<h:outputText value="#{table1.transactionType}"></h:outputText>
					</h:column>

					<h:column id="column3">
						<f:facet name="header">
							<h:outputText value="Buy/Sell price per share"></h:outputText>
						</f:facet>
						<h:outputText value="#{table1.priceBoughtAt}"></h:outputText>
					</h:column>
					
					<h:column id="column7">
						<f:facet name="header">
							<h:outputText value="Transaction Date"></h:outputText>
						</f:facet>
						<h:outputText value="#{table1.quantity}"></h:outputText>
					</h:column>
					
					<h:column id="column4">
						<f:facet name="header">
							<h:outputText value="Transaction Date"></h:outputText>
						</f:facet>
						<h:outputText value="#{table1.transactionDate}"></h:outputText>
					</h:column>					
				</h:dataTable>
			</h:form>
		</center>
		<br>
		<br>
		<br>
		<br>

		<h:outputLink value="CustomerWelcomePage.jsp">Back</h:outputLink>
	</f:view>
</body>
</html>