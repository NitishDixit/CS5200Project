<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<f:view>
		<center>
			<h:form>
				<h:panelGrid border="1" columns="2">
					<h:outputText value="Enter  Old Password"></h:outputText>
					<h:inputSecret value="#{customerBean.password}"></h:inputSecret>
					<h:outputText value="Enter New Password"></h:outputText>
					<h:inputSecret value="#{customerBean.password}"></h:inputSecret>
					<h:outputText value="Retype Password"></h:outputText>
					<h:inputSecret value="#{customerBean.re_pass}"></h:inputSecret>
				</h:panelGrid>
				<h:commandButton value="Change Password" type="submit"
					action="#{customerBean.passReset}"></h:commandButton>
				<h:commandButton value="Reset" type="reset"></h:commandButton>
			</h:form>
		</center>
	</f:view>
</body>
</html>