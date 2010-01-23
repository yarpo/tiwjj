<%-- 
    Document   : confirm
    Created on : 2010-01-23, 15:43:13
    Author     : asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="style.css">
        <title>Potwierdź dane</title>
    </head>
    <body>
        <h1>Potwierdź dane</h1>
         <ul>
            <li><a href="/tiwjj/faces/home.jsp">home</a></li>
            <li><a href="/tiwjj/faces/applet.jsp">applet</a></li>
            <li><a href="/tiwjj/faces/register.jsp">rejestracja</a></li>
        </ul>
         <f:view>
            <h:form>
                <p>Podałeś takie dane:</p>
                <p>Twoje imię to : <h:outputText value="#{UserBean.name}"/></p>
               
                <p><h:inputHidden value="#{UserBean.name}" id="name" required="true"/>
                <h:message for="name" style="color:red" />
                <h:inputHidden value="#{UserBean.password}" id="password" required="true">
                    <h:message for="password" style="color:red" />
                </h:inputHidden>
                <h:inputHidden value="#{UserBean.mail}" id="email" required="true">
                        <f:validator validatorId="tiwjj.EmailValidator" />
                </h:inputHidden>
                <h:message for="email" style="color:red" />
                
                <h:commandButton value="Potwierdź" action="submit" />
                <h:commandButton value="Wróć" action="reset" />
            </h:form>
        </f:view>
    </body>
</html>
