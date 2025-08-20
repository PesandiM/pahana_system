<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<% String message = (String) request.getAttribute("message"); %>
<% if (message != null) { %>
    <script>
        alert("<%= message %>");
    </script>
<% } %>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Item</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/items.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
<%@ include file="/layout/header.jsp" %>
<div class="form-container">

<form action="<%= request.getContextPath() %>/users" method="post">
<input type="hidden" name="action" value="add">
    <label>Username:</label>
    <input type="text" name="username" required><br>

    <label>Password:</label>
    <input type="password" name="password" required><br>

    <label>Role:</label>
    <select name="role">
        <option value="Admin">Admin</option>
        <option value="Staff">Staff</option>
    </select><br>

    <button type="submit">Add User</button>
</form>
</div>
</body>
</html>
