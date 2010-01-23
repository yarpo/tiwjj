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
        <title>Gratuluję</title>
    </head>
    <body>
        <h1>GRatuluję</h1>

        <f:view>
            <h:form>
                <p>Udało się zarejestrować</p>
                <p>Twoje imię to : <h:outputText value="#{UserBean.name}"/></p>
                <p>Urodziles się: <h:outputText value="#{UserBean.birthday}"/></p>
            </h:form>
        </f:view>
    </body>
</html>