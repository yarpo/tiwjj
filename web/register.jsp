<%--
    Document   : register
    Created on : 2010-01-23, 16:21:22
    Author     : Patryk Jar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <title>Technologie Internetowe w Języku Java - formularz rejestracyjny</title>
    </head>
    <body>

        <div id="top">
            <h1>Formularz rejestracyjny</h1>
            <ul>
                <li><a href="/tiwjj/faces/home.jsp">home</a></li>
                <li><a href="/tiwjj/faces/applet.jsp">applet</a></li>
                <li><a href="/tiwjj/faces/register.jsp">rejestracja</a></li>
            </ul>
            <br style="clear: both"/>
        </div>

        <div id="content">
            <f:view>
                <h:form>
                    <p>Login:
                        <h:inputText value="#{UserBean.name}" id="name" required="true"/>
                        <h:message for="name" style="color:red" />
                    </p>
                    <p>Hasło:
                        <h:inputSecret value="#{UserBean.password}" id="pass" required="true"/>
                        <h:message for="pass" style="color:red" />
                    </p>
                    <p>Email:
                        <h:inputText value="#{UserBean.mail}" id="email" required="true">
                            <f:validator validatorId="tiwjj.EmailValidator" />
                        </h:inputText>
                        <h:message for="email" style="color:red" />
                    </p>
                    <h:commandButton value="Submit" action="submit" />
                </h:form>
            </f:view>
        </div>

        <div id="footer">
            <p>&copy; Copyright by yarpo 2010</p>
        </div>
    </body>
</html>
