<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<LINK REL="stylesheet" TYPE="text/css" HREF="styles.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Page</title>
</head>
<body class="mandatory">
	<f:view>
		<h:form>
			<center>
				<h2>
					Welcome Home
					<h:outputText value="#{loginBean.username}"></h:outputText>
				</h2>
				<br> <br>
				<h3>What Operation would you like to perform?</h3>
				<br> <br>
				<h:outputLink value="SearchShares.jsp">Order Shares</h:outputLink>
				<br> <br>
				<h:commandLink
					action="#{customerStockDetailsBean.getCustStockDetails}">View Portfolio</h:commandLink>
				<br> <br>
				<h:commandLink
					action="#{custTransactionHistoryBean.getCustTransactionDetails}">View Transaction History</h:commandLink>
				<br> <br>


				<h:commandLink action="#{accountBean.getAccountDetails}">Account Details</h:commandLink>
				<br>
				<br>
				<h:commandLink value="See Stock Suggestion"
					action="#{stockSuggestionBean.getCustomerStockSuggestion}"></h:commandLink>
				<br> <br>
				<h:commandLink action="#{customerBean.retrieveVal}">Update Profile</h:commandLink>
				<br>
				<br>
				<h:commandLink value="View Portfolio Mananger"
					action="#{customerBean.getPM}"></h:commandLink>
				<br> <br>
				<h:commandLink value="Logout" action="#{logout.logout}" />
			</center>
		</h:form>
	</f:view>
</body>
</html>