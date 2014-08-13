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

<h:outputText value="Customer Linked to PM....!!!"></h:outputText>
<br><br><br><br><br><br><br>
<h:outputLink value="AdminWelcomePage.jsp">Home</h:outputLink>
<h:form>
				<h:commandLink value="Logout" action="#{logout.logout}" />
			</h:form>
</center>
</f:view>
</body>
</html>