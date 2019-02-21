<%@ page contentType="text/html" pageEncoding="UTF-8" %>


<!DOCTYPE html>
<head>
    <title>Авторизация</title>
    <!link href="/css/main.css" rel="stylesheet">
</head>
<body>
<form action = "auth" method = "POST">
    Введите логин: <input type = "text" name = "login">
    <br />
    Введите пароль: <input type = "text" name = "password" />
    <br />
    <input type = "submit" value = "Ок" />
</form>
</body>
</html>