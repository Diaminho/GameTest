<%@ page import="java.util.Date" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>


<!DOCTYPE html>
<% Date start = new Date(); %>
    <head>
        <title>Главное меню</title>
        <link rel="stylesheet" href="resources/css/main.css">
    </head>
    <body>
        <div class="wrapper">
            <div class="content">
                <h1>Добро пожаловать, ${login}!</h1>
                <p>Ваш рейтинг: ${rating}<p/>
                <p>
                    <form action="duelInfo" method="post">
                        <input type="submit" value="Перейти к дуэлям">
                    </form>
                </p>
                <p>
                    <form action="quit" method="post">
                        <input type="submit" value="Выход">
                    </form>
                    </p>
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