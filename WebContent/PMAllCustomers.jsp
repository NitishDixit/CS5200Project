<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<f:view>
<center>
<h1>List Of Customer</h1>
		<h:dataTable border="1" value="#{pMBean.customerDetails}" var="custs">
		<h:column id="column">
			<h:outputText value="#{custs.id}"></h:outputText>
				<f:facet name="header">
					<h:outputText value="Customer ID"></h:outputText>
				</f:facet>
			</h:column>
			<h:column id="column1">
			<h:outputText value="#{custs.customerName}"></h:outputText>
				<f:facet name="header">
					<h:outputText value="Customer Name"></h:outputText>
				</f:facet>
			</h:column>
			<h:column id="column2">
			<h:outputText value="#{custs.contactNumber}"></h:outputText>
				<f:facet name="header">
					<h:outputText value="Customer Contact Number"></h:outputText>
				</f:facet>
			</h:column>
			<h:column id="column3">
			<h:outputText value="#{custs.accountNumber}"></h:outputText>
				<f:facet name="header">
					<h:outputText value="Customer Account Number"></h:outputText>
				</f:facet>
			</h:column>
		</h:dataTable><br><br><br><h:form>
				
			</h:form><br><br>
			<h:form>
				<h:panelGrid border="0" columns="2">
					<h:outputText value="Enter the Customer Id"></h:outputText>
					<h:inputText id="custid" value="#{stockSuggestionBean.custId}" required="true" requiredMessage="Cant be left Blank">
					<f:validator  validatorId="custvalidate"></f:validator>
					
					</h:inputText>
					<h:commandButton value="Submit" type="submit" action="#{stockSuggestionBean.navigate}"></h:commandButton>
					<h:commandButton value="Reset" type="reset"></h:commandButton>	
					
							
				</h:panelGrid>
				<h:message for="custid"></h:message><br></h:form>
				<br><h:outputLink value="PMWelcomePage.jsp">Home</h:outputLink>	<br><br><h:form><h:commandLink value="Logout" action="#{logout.logout}" />
			</h:form>
		</center>
	</f:view>
</body>
</html>