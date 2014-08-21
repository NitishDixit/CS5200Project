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
<center><h1>All Portfolio Manager</h1>
			<h:dataTable border="1" value="#{pMBean.pfmDetails}"
				var="hello">
				<h:column id="column1">
					<h:outputText value="#{hello.id}"></h:outputText>
					<f:facet name="header">
						<h:outputText value="PM Id"></h:outputText>
					</f:facet>
				</h:column>
				<h:column id="column2">
					<h:outputText value="#{hello.pfmName}"></h:outputText>
					<f:facet name="header">
						<h:outputText value="PM Name"></h:outputText>
					</f:facet>
				</h:column>
				<h:column id="column3">
					<h:outputText value="#{hello.contactNumber}"></h:outputText>
					<f:facet name="header">
						<h:outputText value="Contact Number"></h:outputText>
					</f:facet>
				</h:column>
				<h:column id="column4">
					<h:outputText value="#{hello.address}"></h:outputText>
					<f:facet name="header">
						<h:outputText value="PM Address"></h:outputText>
					</f:facet>
				</h:column>
				<h:column id="column5">
					<h:outputText value="#{hello.username}"></h:outputText>
					<f:facet name="header">
						<h:outputText value="PM Username"></h:outputText>
					</f:facet>
				</h:column>
</h:dataTable>
<h:form>
<h:panelGrid border="1" columns="3">
					<h:outputText value="Enter PM Id to delete"></h:outputText>
					<h:inputText required="true" value="#{pMBean.pfmID}"
						id="PMId" requiredMessage="PMId cannot be blank">
					</h:inputText>
					<h:message for="PMId" style="color: #FF0000"></h:message>
				</h:panelGrid>
				<h:commandButton value="Delete" type="submit"
					action="#{pMBean.deletePM}"></h:commandButton>
				<h:commandButton value="Reset" type="reset"></h:commandButton></h:form>
				<br>
				<br>
				<h:outputText value="#{customerBean.message}" style="color: #FF0000"></h:outputText>
				<br>
				<br>
				<br>
				
<h:outputLink value="AdminWelcomePage.jsp">Home</h:outputLink>
<h:form>
				<h:commandLink value="Logout" action="#{logout.logout}" />
			</h:form>
			</center>
</f:view>
</body>
</html>