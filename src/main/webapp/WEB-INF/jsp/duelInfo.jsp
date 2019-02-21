<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<head>
    <title>Главное меню</title>
    <!link href="/css/main.css" rel="stylesheet">
</head>
<body>
<h2 class="hello-title">Дуэли, ${login}</h2>

Ваш рейтинг: ${rating}

<form action="menu" method="post">
    <input type="submit" value="Назад">
</form>

</body>
</html>