<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<head>
    <title>Главное меню</title>
    <!link href="/css/main.css" rel="stylesheet">
</head>
<body>
<h2 class="hello-title">Добро пожаловать, ${login}!</h2>
<br/>
Id сессии: ${sessionId}
<br/>
<form action="duelInfo" method="post">
    <input type="submit" value="Перейти к дуэлям">
</form>

<form action="quit" method="get">
    <input type="submit" value="Выход">
</form>
</body>
</html>