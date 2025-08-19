<%@ page import="java.util.List, model.Bill" %>
<%
    List<Bill> bills = (List<Bill>) request.getAttribute("bills");
%>
<!DOCTYPE html>
<html>
<head>
    <title>View Bills</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/bills.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
<%@ include file="/layout/header.jsp" %>

<% if (bills != null && !bills.isEmpty()) { %>
    <table border="1" cellpadding="8" style="width: 100%; border-collapse: collapse;">
        <thead style="background-color: #2c3e50; color: white;">
            <tr>
                <th>Bill ID</th>
                <th>Date</th>
                <th>Customer</th>
                <th>Total Amount</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <% for (Bill bill : bills) { %>
            <tr style="text-align:center;">
                <td><%= bill.getBillId() %></td>
                <td><%= bill.getBillDate() != null ? bill.getBillDate() : "N/A" %></td>
                <td><%= bill.getCustomerName() %></td>
                <td>Rs. <%= String.format("%.2f", bill.getTotalAmount()) %></td>
                <td>
                    <form method="post" action="<%= request.getContextPath() %>/billing?action=viewBill">
                        <input type="hidden" name="billId" value="<%= bill.getBillId() %>"/>
                        <button type="submit">View</button>
                    </form>
                </td>
            </tr>
            <% } %>
        </tbody>
    </table>
<% } else { %>
    <p>No bills found.</p>
<% } %>

</body>
</html>
