<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.StockReportDTO" %>

<html>
<head>
    <title>Stock Report</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/customer.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
<%@ include file="/layout/header.jsp" %>
    <h2>Stock Summary Report</h2>

    <table>
        <tr>
            <th>Item ID</th>
            <th>Name</th>
            <th>Type</th>
            <th>Quantity Available</th>
            <th>Price (LKR)</th>
        </tr>

        <%
            List<StockReportDTO> stockList = (List<StockReportDTO>) request.getAttribute("stockList");
            if (stockList != null && !stockList.isEmpty()) {
                for (StockReportDTO item : stockList) {
        %>
        <tr>
            <td><%= item.getItemId() %></td>
            <td><%= item.getItemName() %></td>
            <td><%= item.getItemType() %></td>
            <td><%= item.getQuantity() %></td>
            <td><%= item.getPrice() %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="5">No stock data available</td>
        </tr>
        <%
            }
        %>
    </table>
</body>
</html>