<%@ page import="java.util.Date" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<% Date start = new Date(); %>
<head>
    <title>Дуэль</title>
    <link rel="stylesheet" href="resources/css/main.css">
</head>
    <body>
        <div class="wrapper">
            <div class="content">
                <h1>Начинается дуэль, ${login}</h1>

                <table align="center" cellspacing="5" cellpadding="5">
                    <tr>
                        <td><p>Вы: ${login}</p></td>
                        <td><p>Ваш оппонент: ${opponentLogin} </p></td>
                    </tr>
                    <tr>
                        <td><p>Ваш показатель жизни: ${userHp}</p></td>
                        <td><p>Показатель жизни соперника: ${opponentHp}</p></td>
                    </tr>
                    <tr>
                        <td>
                            <progress max=${userHpBeforeDuel} value=${userHp}>
                            </progress>
                        </td>
                        <td>
                            <progress max=${opponentHpBeforeDuel} value=${opponentHp}>
                            </progress>
                        </td>
                    </tr>
                </table>

                <p>${info}</p>

                <p>
                    <form action="attack" method="post">
                        <input type="submit" value="Атака">
                    </form>
                </p>
                <p>
                    <form action="menu" method="post">
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