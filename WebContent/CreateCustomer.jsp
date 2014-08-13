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
			<h2>Portfolio Management System Customer Sign up page</h2>
			<br>
				<h:panelGrid border="1" columns="3">
					<h:outputText value="Customer Name"></h:outputText>
					<h:inputText value="#{customerBean.customerName}" id="name"
						required="true" requiredMessage="*Name cannot be blank."></h:inputText>
					<h:message for="name" style="color: #FF0000"></h:message>
					<h:outputText value="Address"></h:outputText>
					<h:inputTextarea id="address" value="#{customerBean.address}"
						required="true" requiredMessage="*Address cannot be blank"></h:inputTextarea>
					<h:message for="address" style="color: #FF0000"></h:message>
					<h:outputText value="Contact Number"></h:outputText>
					<h:inputText id="contact" value="#{customerBean.contactNumber }"
						required="true" requiredMessage="*Contact number cannot be blank">
						<f:validator validatorId="num_validator" />
					</h:inputText>
					<h:message for="contact" style="color: #FF0000"></h:message>
					<h:outputText value="Account Number"></h:outputText>
					<h:inputText id="acc" value="#{customerBean.accountNumber}"
						required="true" requiredMessage="*Account Number cannot be blank">
						<f:validator validatorId="accountValidator" />
					</h:inputText>
					<h:message for="acc" style="color: #FF0000"></h:message>
					<h:outputText value="Email Id"></h:outputText>
					<h:inputText id= "email" value="#{customerBean.eMail}" required ="true" requiredMessage="Email Id cannot be blank">
					<!-- f:validator validatorId="emailValidator"/-->
					</h:inputText>
					<h:message for="email" style="color: #FF0000"></h:message>					
					<h:outputText value="Security Question: Which city were you born?"></h:outputText>
					<h:inputText id="ans" value="#{customerBean.answer}"
						required="true" requiredMessage="*Answer cannot be blank">
					</h:inputText>
					<h:message for="ans" style="color: #FF0000"></h:message>				
					<h:outputText value="Username"></h:outputText>
					<h:inputText id="username" value="#{customerBean.username}"
						required="true" requiredMessage="*Username cannot be left blank">
						<f:validator validatorId="usernamevalidator" />
					</h:inputText>
					<h:message for="username" style="color: #FF0000"></h:message>
					<h:outputText value="Password"></h:outputText>
					<h:inputSecret id="pass" value="#{customerBean.password}"
						required="true" requiredMessage="*Password cannot be left blank">
					</h:inputSecret>
					<h:message for="pass" style="color: #FF0000"></h:message>
					<h:outputText value="Retype Password"></h:outputText>
					<h:inputSecret id="repass" value="#{customerBean.re_pass}"
						required="true" requiredMessage="*Field cannot be left blank">
					</h:inputSecret><br>
					<h:message for="repass" style="color: #FF0000"></h:message>
				</h:panelGrid>
				<h:commandButton value="Submit" type="submit"
					action="#{customerBean.save}"></h:commandButton>
				<h:commandButton value="Reset" type="reset"></h:commandButton>
				<br>
				<br>
				<h:outputText value="" style="color: #FF0000"></h:outputText>
				<h:outputLink value="LoginPage.jsp">Login Page</h:outputLink>
			</h:form>
		</center>
	</f:view>
</body>
</html>