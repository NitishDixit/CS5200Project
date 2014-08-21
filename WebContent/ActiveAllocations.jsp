<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Active Allocations</title>
</head>
<body>
<f:view>
<center>
<h3>
Active Portfolio Managers Allocations</h3>
		<h:dataTable border="1" value="#{customerPfmBean.customerPfms }" var="cp">
			<h:column id="column1">
			<h:outputText value="#{cp.id.pfmUser}"></h:outputText>
				<f:facet name="header">
					<h:outputText value="PortFolio Manager ID"></h:outputText>
				</f:facet>
			</h:column>
			<h:column id="column2">
			<h:outputText value="#{cp.id.custUser}"></h:outputText>
				<f:facet name="header">
					<h:outputText value="Customer ID"></h:outputText>
				</f:facet>
			</h:column>
		</h:dataTable><br><br>
		<h:form>
		<h:panelGrid columns="3" border="0">
		<h:outputText value="Enter the Customer Id"></h:outputText>
		<h:inputText value="#{customerPfmBean.custId}" id="custId" required="true" requiredMessage="*Cant be left blank"></h:inputText>
		<h:message for="custId" style="color: #FF0000"></h:message>
		<h:outputText value="Enter the Portfolio Manager Id"></h:outputText>
		<h:inputText value="#{customerPfmBean.pmId}" id="pmId" required="true" requiredMessage="*Cant be left blank"></h:inputText>
		<h:message for="pmId" style="color: #FF0000"></h:message>
		<h:outputText value=""></h:outputText>
		
		<h:commandButton value="Deallocate Customer" type="submit" action="#{customerPfmBean.deleteAlloc}"></h:commandButton>
		<h:commandButton value="Reset" type="reset"></h:commandButton></h:panelGrid><br><br>
		<h:outputText value="#{customerPfmBean.message}" style="color: #FF0000"></h:outputText><br>
		</h:form>
		<h:outputLink value="AdminWelcomePage.jsp">Home</h:outputLink><br>
<h:form>
<h:commandLink action="#{logout.logout}">LogOut</h:commandLink>
</h:form>
</center>
</f:view>
</body>
</html>