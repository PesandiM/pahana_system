<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Pahana Edu Login</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<h1>Welcome to <br> Pahana Edu Bookshop!!</h1><br>
<div class="form-container">
    <form action="login" method="post">
        <h2>Login</h2><br>
        <input type="email" name="username" placeholder="User name" required class="box"><br>
        <input type="password" name="password" placeholder="password" required class="box"> <br><br>
        <input type="submit" name="submit-btn" value="Login" class="btn">
    </form>
    <p style="color:red;">
        <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "" %>
    </p>
</div>
</body>
</html>