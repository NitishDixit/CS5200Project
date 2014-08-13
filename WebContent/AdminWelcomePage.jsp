<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<LINK REL="stylesheet" TYPE="text/css" HREF="styles.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Administrator Welcome Page</title>
</head>
<body class="mandatory">
	<f:view>
		<center>
			<font size="5">Welcome Admin...!!!</font><br>
			<br><br>

			<h:form>


				<h:outputLink value="CreatePM.jsp"> Create Portfolio Manager </h:outputLink>
				<br><br>
				<h:commandLink value="Approve Customer"
					action="#{customerBean.getInactiveCustomer }"></h:commandLink>
				<br><br>
				<h:commandLink value="Delete Customer"
					action="#{customerBean.getAllCustomer}"></h:commandLink><br><br>
				<h:commandLink value="Delete Portfolio Manager"
					action="#{pMBean.getAllPM}"></h:commandLink>
					<br><br>
					<h:commandLink value="Assign Portfolio Manager to Customer"
					action="#{customerPfmBean.getAllCustPM}"></h:commandLink><br>
					<br>
					<h:commandLink value="Delete PM assignment"
					action="#{customerPfmBean.getAllCustPMS}"></h:commandLink>
					<br><br>
					
				<h:commandLink value="Logout" action="#{logout.logout}" />
			</h:form>
		</center>
	</f:view>
</body>
</html>