<%-- 
    Document   : greeting
    Created on : 2010-01-23, 12:54:51
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
        <title>Strona na tiwjj</title>
    </head>
    <body>
        <h1>Witaj na mojej stronie</h1>

        <f:view>
            <h:form>
                <p>Podaj swoje imię:
                    <h:inputText value="#{UserBean.name}" id="name" required="true"/>
                    <h:message for="name" style="color:red" />
                </p>
                <p>Podaj date urodzin swoich urodzin:
                    <h:inputText value="#{UserBean.birthday}" id="birthday" required="true">
                        <f:convertDateTime pattern="dd/MM/yyyy" />
                    </h:inputText> (dd/mm/yyyy)
                    <h:message for="birthday" style="color:red" />
                </p>
                <p>Podaj email:
                    <h:inputText value="#{UserBean.mail}" id="email" required="true">
                        <f:validator validatorId="tiwjj.EmailValidator" />
                    </h:inputText>
                    <h:message for="email" style="color:red" />
                </p>
                <h:commandButton value="Submit" action="submit" />
            </h:form>
        </f:view>
    </body>
</html>
