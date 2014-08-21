<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer's Account Details</title>
</head>
<body>
	<f:view>
		<center>
			<h:form>
				<h3>
					<h:outputText
						value="Account Details for the customer #{loginBean.username}"></h:outputText>
				</h3>
				<h:panelGrid border="1" columns="2">
					<h:outputText value="Account Number "></h:outputText>
					<h:outputText value="#{accountBean.accountNo}"></h:outputText>
					<h:outputText value="Balance"></h:outputText>
					<h:outputText value="#{accountBean.balance}"></h:outputText>
				</h:panelGrid>
				<br>
				<br>
				<h3>
					<h:outputText value="Transfer Funds to PFM System?"></h:outputText>
				</h3>
				<h:panelGrid border="1" columns="2">
					<h:outputText value="Account Number"></h:outputText>
					<h:outputText value="#{accountBean.accountNo}"></h:outputText>
					<h:outputText value="Amount"></h:outputText>
					<h:inputText value="#{accountBean.amount}"></h:inputText>
				</h:panelGrid>
				<h:commandButton value="Initiate Transfer"
					action="#{accountBean.transferFunds}" type="submit"></h:commandButton>
			</h:form>
			<br>
			<br>
			<br>
			<h:outputText value="#{accountBean.failMessage}"></h:outputText>
			<br> <br> <br> <br>
		</center>
		<h:outputLink value="CustomerWelcomePage.jsp">Back</h:outputLink>
	</f:view>
</body>
</html>