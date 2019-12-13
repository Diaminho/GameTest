<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date" %>


<!DOCTYPE html>
<% Date start = new Date(); %>
<head>
    <title>Авторизация</title>
    <link rel="stylesheet" href="resources/css/main.css">
</head>
<body>
<div class="wrapper">
    <div class="content">
        <form action = "auth" method = "POST">
            <h1> Авторизация</h1>
            <p>Введите логин: <input type = "text" name = "login"/> </p>
            <p>Введите пароль: <input type = "password" name = "password"/> </p>
            <p> <input type = "submit" value = "Ок" />
        </form>
        <p>${info}</p>
    </div>
    <div class="footer">
        <p>page:
            <% Date end = new Date(); %>
            <%= end.getTime() - start.getTime() %>ms
        </p>
    </div>
</div>
</body>
</html>