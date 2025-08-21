<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Customer</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/customer.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
    <%@ include file="/layout/header.jsp" %>

    <div class="form-container">
        <h2>Add Customer</h2>

        <form id="addCustomerForm" action="<%= request.getContextPath() %>/add-customer" method="post">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" required>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>

                <label for="address">Address:</label>
                <input type="text" id="address" name="address" required>

                <label for="telephone">Telephone:</label>
                <input type="tel" id="telephone" name="telephone" required pattern="[0-9]{10}" title="Enter 10-digit number">

                <input type="submit" value="Add Customer">
            </form>
    </div>
</body>
</html>