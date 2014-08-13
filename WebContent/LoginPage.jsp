<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<LINK REL="stylesheet" TYPE="text/css" HREF="styles.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
</head>
<body class= "mystyle2">
	<f:view>
		<center>
			<h:form>
				<h1>
					<font color="red">Welcome to Portfolio Management System</font><br> <br>
				</h1>
				<h:outputLink value="JsonTrial.jsp">Get Stock Quote</h:outputLink>
				<br>
				<br>
				<br>
				<h:panelGrid border="1" columns="3">
					<h:outputText value="Username"></h:outputText>
					<h:inputText required="true" value="#{loginBean.username}"
						id="username" requiredMessage="*Username cannot be blank">
					</h:inputText>
					<h:message for="username" style="color: #FF0000"></h:message>
					<h:outputText value="Password"></h:outputText>
					<h:inputSecret required="true" id="password"
						value="#{loginBean.password}"
						requiredMessage="*Password cannot be left blank">
					</h:inputSecret>
					<h:message for="password" style="color: #FF0000"></h:message>
				</h:panelGrid>
				<h:commandButton value="Login" type="submit"
					action="#{loginBean.authenticate}"></h:commandButton>
				<h:commandButton value="Reset" type="reset"></h:commandButton>
				<br>
				<br>
				<h:outputText value="#{loginBean.message}" style="color: #FF0000"></h:outputText>
				<br>
				<h:outputLink value="CreateCustomer.jsp">New User? Signup Now</h:outputLink>
				<br>
				<br>
			</h:form>
		</center>
	</f:view>
</body>
</html>