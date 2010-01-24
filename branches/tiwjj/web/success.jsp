<%-- 
    Document   : success
    Created on : 2010-01-23, 13:03:40
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
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <title>Technologie Internetowe w Języku Java - Udało się zarejesrtować</title>
    </head>
    <body>

        <div id="top">
            <h1>Gratuluję - rejestracja się powiodła</h1>
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
                    <h1>Udało się zarejestrować</h1>
                    <p>Twoje imię to : <h:outputText value="#{UserBean.name}"/></p>
                    <p>E-mail: <h:outputText value="#{UserBean.mail}"/></p>
                </h:form>
            </f:view>
        </div>

        <div id="footer">
            <p>&copy; Copyright by yarpo 2010</p>

        </div>


    </body>
</html>
