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
					DashBoard for the user
					<h:outputText value="#{loginBean.username}"></h:outputText>
				</h2>
				<h:dataTable border="1" value="#{customerStockDetailsBean.csdbList}"
					var="table">

					<h:column id="column">
						<f:facet name="header">
							<h:outputText value="Company Symbol"></h:outputText>
						</f:facet>
						<h:outputText value="#{table.stock_tickr}"></h:outputText>
					</h:column>

					<h:column id="column2">
						<f:facet name="header">
							<h:outputText value="Quantity"></h:outputText>
						</f:facet>
						<h:outputText value="#{table.quantity}"></h:outputText>
					</h:column>

					<h:column id="column3">
						<f:facet name="header">
							<h:outputText value="Bought At"></h:outputText>
						</f:facet>
						<h:outputText value="#{table.bought_at}"></h:outputText>
					</h:column>

					<h:column id="column4">
						<f:facet name="header">
							<h:outputText value="Current Price"></h:outputText>
						</f:facet>
						<h:outputText value="#{table.currentPrice}"></h:outputText>
					</h:column>

					<h:column id="column5">
						<f:facet name="header">
							<h:outputText value="Profit/Loss %"></h:outputText>
						</f:facet>
						<h:outputText value="#{table.profit_loss}"></h:outputText>
					</h:column>

					<h:column id="column6">
						<f:facet name="header">
							<h:outputText value="Column"></h:outputText>
						</f:facet>
						<h:outputLink value="SellShares.jsp">Sell</h:outputLink>
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