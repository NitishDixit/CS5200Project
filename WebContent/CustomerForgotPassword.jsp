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
Enter Username &nbsp;&nbsp;&nbsp;&nbsp; 
<h:inputText value="#{loginBean.username}" required="true"
					requiredMessage="Can't be left blank" id="uname"></h:inputText>
				<h:message for="uname"></h:message>
				<br>
				<h:commandButton value="Submit Username"
					action="#{loginBean.validateUsername}"></h:commandButton>
				<br>
				<br>

				<h:outputText value="#{loginBean.message}" style="color: #FF0000"></h:outputText>
			</h:form>
		</center>
	</f:view>
</body>
</html>