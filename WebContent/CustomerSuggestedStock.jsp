<%@taglib uri="http://java.sun.com/jstl/core" prefix="c"%><%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
<h2>Stock Suggestions</h2>
		<h:dataTable border="1" value="#{stockSuggestionBean.suggestedStocks}" var="s">
			<h:column id="column1">
			<h:outputText value="#{s.id.stockTickr }"></h:outputText>
				<f:facet name="header">
					<h:outputText value="Suggested Stock"></h:outputText>
				</f:facet>
			</h:column>
			<h:column id="column2">
			<h:outputText value="#{s.suggestedDate}"></h:outputText>
				<f:facet name="header">
					<h:outputText value="Suggested Date"></h:outputText>
				</f:facet>
			</h:column>
			
			<h:column id="column3">
			<h:outputText value="#{s.comments}"></h:outputText>
				<f:facet name="header">
					<h:outputText value="Comments"></h:outputText>
				</f:facet>
			</h:column>
		</h:dataTable><br>
		<h:outputLink value="CustomerWelcomePage.jsp">Home</h:outputLink>
<h:form>
				<h:commandLink value="Logout" action="#{logout.logout}" />
			</h:form>
		</center>
	</f:view>
</body>
</html>