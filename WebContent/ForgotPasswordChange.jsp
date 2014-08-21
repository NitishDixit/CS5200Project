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
<h:form>
<h:panelGrid border="1" columns="3">
<h:outputText value="Enter New Password"></h:outputText>
<h:inputSecret value="#{loginBean.password}" required="true" requiredMessage="* Cant be left Blank" id="pass"></h:inputSecret>
<h:message for="pass"></h:message>
<h:outputText value="Retype New Password"></h:outputText>
<h:inputSecret value="#{loginBean.re_pass}" required="true" requiredMessage="* Cant be left Blank" id="repass"></h:inputSecret>
<h:message for="repass"></h:message>
</h:panelGrid>
<h:commandButton value="Change Password" action="#{loginBean.changePassword}"></h:commandButton><br><br><h:outputText value="#{loginBean.message}" style="color: #FF0000"></h:outputText>
</h:form>
</center>
</f:view>
</body>
</html>