<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Customer" %>
<%@ page import="dto.CustomerSummaryDTO" %>
<html>
<head>
    <title>Customer Purchases Report</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/bills.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
<%@ include file="/layout/header.jsp" %>
<h2 style="text-align:center;">Customer Purchases Report</h2>

<form method="get" action="reports">
    <input type="hidden" name="action" value="customer" />
    <label for="customerId">Select Customer:</label>
    <select name="customerId" id="customerDropdown">
        <option value="">-- Choose Customer --</option>
        <%
            List<Customer> customers = (List<Customer>) request.getAttribute("customers");
            String selectedId = request.getParameter("customerId");
            if (customers != null) {
                for (Customer c : customers) {
        %>
        <option value="<%= c.getAccountNo() %>" <%= String.valueOf(c.getAccountNo()).equals(selectedId) ? "selected" : "" %>>
            <%= c.getName() %> (Acc: <%= c.getAccountNo() %>)
        </option>
        <%
                }
            }
        %>
    </select>
    <button class="viewpurchasebtn" type="submit">View Purchases</button>
</form>

<br/>

<%
    List<CustomerSummaryDTO> purchases = (List<CustomerSummaryDTO>) request.getAttribute("purchases");
    if (purchases != null && !purchases.isEmpty()) {
%>
<table>
    <tr>
        <th>Bill ID</th>
        <th>Date</th>
        <th>Item Name</th>
        <th>Quantity</th>
        <th>Price</th>
        <th>Total</th>
    </tr>
    <%
        for (CustomerSummaryDTO item : purchases) {
    %>
    <tr>
        <td><%= item.getBillId() %></td>
        <td><%= item.getDate() %></td>
        <td><%= item.getItemName() %></td>
        <td><%= item.getQuantity() %></td>
        <td><%= item.getPrice() %></td>
        <td><%= item.getTotal() %></td>
    </tr>
    <%
        }
    %>
</table>
<%
    } else if (selectedId != null && !selectedId.isEmpty()) {
%>
<p>No purchases found for this customer.</p>
<%
    }
%>

</body>
</html>
