<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, model.User" %>
<html>
<head>
    <title>User Management</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/items.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
<%@ include file="/layout/header.jsp" %>
    <h2>User List</h2>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Role</th>
            <th>Actions</th>
        </tr>
        <%
            List<User> users = (List<User>) request.getAttribute("userList");
            if (users != null) {
                for (User u : users) {
        %>
        <tr>
            <td><%= u.getUserId() %></td>
            <td><%= u.getUsername() %></td>
            <td><%= u.getRole() %></td>
            <td>
            <a href="<%= request.getContextPath() %>/users?action=delete&user_id=<%= u.getUserId() %>" onclick="return confirm('Are you sure?');">Delete</a>
            </td>
        </tr>
        <%
                }
            }
        %>
    </table>
</body>
</html>
