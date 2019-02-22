<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<head>
    <title>Главное меню</title>
    <!link href="/css/main.css" rel="stylesheet">
</head>
<body>
<h2 class="hello-title">Дуэль, ${login}</h2>

Ваш оппонент: ${opponentLogin}
<br/>
Ваш показатель жизни: ${userHp}
<br/>
Показатель жизни соперника: ${opponentHp}
<br/>
<form action="duel" method="post">
    <input type="submit" value="Начать дуэль">
</form>

<form action="quit" method="post">
    <input type="submit" value="Выйти">
</form>

</body>
</html>