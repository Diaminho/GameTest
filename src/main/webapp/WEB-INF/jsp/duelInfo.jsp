<%@ page import="java.util.Date" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<% Date start = new Date(); %>
    <head>
        <title>Меню дуэлей</title>
        <link rel="stylesheet" href="resources/css/main.css">
    </head>
    <body>
        <div class="wrapper">
            <div class="content">
                <h1>Дуэли, ${login}</h1>
                <p> Ваш рейтинг: ${rating} </p>
                <p> ${info} </p>
                <p>
                    <form action="search" method="post">
                        <input type="submit" value="Поиск соперника">
                    </form>
                </p>
                <p>
                    <form action="quit" method="post">
                        <input type="submit" value="Выйти">
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