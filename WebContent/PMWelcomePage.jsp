<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<LINK REL="stylesheet" TYPE="text/css" HREF="styles.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PM page</title>
</head>
<body class="mandatory">
<f:view>
<center>
<font size="5">Welcome Portfolio Manager...!!!</font><br><br>
<h:form>
<br>
<h:commandLink action="#{pMBean.getAllCust}" value="Stock Suggestion to Customers"></h:commandLink><br><br>
<h:commandLink action="#{pMBean.retrieveVal}" value="Update Profile/Change Password" immediate="true"></h:commandLink>
<br><br>
<h:commandLink value="Logout" action="#{logout.logout}" />
</h:form>
</center>
</f:view>
</body>
</html>