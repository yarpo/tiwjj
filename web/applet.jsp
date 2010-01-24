<%--
    Document   : applet
    Created on : 2010-01-23, 16:21:22
    Author     : Patryk Jar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <title>Technologie Internetowe w Języku Java - applet</title>
    </head>
    <body>

        <div id="top">
            <h1>Applet</h1>
            <ul>
                <li><a href="/tiwjj/faces/home.jsp">home</a></li>
                <li><a href="/tiwjj/faces/applet.jsp">applet</a></li>
                <li><a href="/tiwjj/faces/register.jsp">rejestracja</a></li>
            </ul>
            <br style="clear: both"/>
        </div>

        <div id="content">
            <h1>Zagraj</h1>
            <applet ARCHIVE="applet/SePilkarzyki.jar" CODE="tiwjj/Main.class" WIDTH="270" HEIGHT="350"></applet>

            <p><b>Zasady:</b></p>
            <ul>
                <li>Gra jest turowa</li>
                <li>Można wykonać co najmniej jeden ruch</li>
                <li>Jeśli twój ruch sprawi, że końcowy punkt znajdzie się na linii, albo w puncie, gdzie już wcześniej ktoś był - ruszasz ponownie</li>
                <li>Należy strzelić bramkę przeciwnikowi</li>
            </ul>
        </div>

        <div id="footer">
            <p>&copy; Copyright by yarpo 2010</p>

        </div>


    </body>
</html>
