<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    model.Customer customer = (model.Customer) request.getAttribute("customer");
    String errorMessage = (String) request.getAttribute("errorMessage");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Customer</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/customer.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
<%@ include file="/layout/header.jsp" %>
    <div class="form-container">
        <h2>Edit Customer</h2>

        <% if (errorMessage != null) { %>
            <p class="error-message"><%= errorMessage %></p>
        <% } %>

        <% if (customer != null) { %>
        <form action="<%= request.getContextPath() %>/update-customer" method="post">
            <input type="hidden" name="accountNo" value="<%= customer.getAccountNo() %>" />

            <label>Name:</label>
            <input type="text" name="name" value="<%= customer.getName() %>" readonly />

            <label>Email:</label>
            <input type="email" name="email" value="<%= customer.getEmail() %>" required />

            <label>Address:</label>
            <input type="text" name="address" value="<%= customer.getAddress() %>" required />

            <label>Telephone:</label>
            <input type="tel" name="telephone" value="<%= customer.getTelephone() %>" required pattern="[0-9]{10}" title="Enter 10-digit number" />

            <input type="submit" value="Update Customer" />
        </form>
        <% } else { %>
            <p>Customer details not available.</p>
        <% } %>
    </div>
</body>
</html>
