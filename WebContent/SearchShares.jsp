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
				<h6>
					Enter Ticker Symbol:
					<h:inputText value="#{stockQuote.symbol}"></h:inputText>
				</h6>

				<h:commandButton value="Get Stock Quote"
					action="#{stockQuote.getStockData}" type="submit" id="submit">
				</h:commandButton>
				<h:commandButton value="Reset" type="submit"
					action="#{stockQuote.reset}"></h:commandButton>
				<br>
				<br>
				<h:outputText value="#{stockQuote.failMessage}"
					style="color: #FF0000"></h:outputText>
			</h:form>					
			
		</center>
		<br> <br> <br>
			<h:outputLink value="CustomerWelcomePage.jsp">Back</h:outputLink>
	</f:view>
</body>
</html>