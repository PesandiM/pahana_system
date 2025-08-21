<%@ page import="java.util.List, java.util.ArrayList, model.BillItem, model.Bill" %>
<%
    Bill bill = (Bill) request.getAttribute("bill");
        List<BillItem> items = bill != null ? bill.getItems() : new ArrayList<>();
        int count = 1;
        double grandTotal = 0;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Invoice - Bill #<%= bill.getBillId() %></title>
    <style>
        body {
            margin: 40px;
            color: #333;
        }

        .invoice-box {
            max-width: 800px;
            margin: auto;
            padding: 30px;
            border: 1px solid #eee;
            box-shadow: 0 0 10px rgba(0,0,0,0.05);
            background-color: #fff;
        }

        h1 {
            text-align: center;
            color: #4CAF50;
            margin-bottom: 20px;
        }

        .invoice-header {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }

        .invoice-header div {
            line-height: 1.5;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        table th, table td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }

        table th {
            background-color: #f4f4f4;
        }

        table tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .total-row td {
            font-weight: bold;
            font-size: 1.1em;
        }

        .text-right {
            text-align: right;
        }

        .print-button {
            margin-top: 20px;
            text-align: center;
        }

        .print-button button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            font-size: 1em;
            border-radius: 5px;
        }

        .print-button button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="invoice-box">
            <h1>Invoice</h1>
            <hr/>
            <h2>Pahana Edu Bookshop</h2>
            <p>
                0112916235 / 072365596 <br>
                23, Charles Drive, Colombo 03 <br>
                pahanaedu@gmail.com
            </p>
            <hr/>

            <div class="invoice-header">
                <div>
                    <strong>Bill ID:</strong> <%= bill.getBillId() %><br>
                    <strong>Date:</strong> <%= bill.getBillDate() != null ? bill.getBillDate() : "N/A" %>
                </div>
                <div>
                        <strong>Customer:</strong> <%= bill.getCustomerName() %><br>
                        <strong>Email:</strong> <%= bill.getCustomerEmail() %><br>
                        <strong>Phone:</strong> <%= bill.getCustomerPhone() %><br>
                        <strong>Address:</strong> <%= bill.getCustomerAddress() %>
                </div>
            </div>

            <table>
                <tr>
                    <th>#</th>
                    <th>Type</th>
                    <th>Item</th>
                    <th>Qty</th>
                    <th>Price</th>
                    <th>Total</th>
                </tr>
                <% for (BillItem item : items) {
                       grandTotal += item.getItemTotal();
                %>
                <tr>
                    <td><%= count++ %></td>
                    <td><%= item.getItemType() %></td>
                    <td><%= item.getItemName() %></td>
                    <td><%= item.getQuantity() %></td>
                    <td class="text-right"><%= String.format("%.2f", item.getItemPrice()) %></td>
                    <td class="text-right"><%= String.format("%.2f", item.getItemTotal()) %></td>
                </tr>
                <% } %>
                <tr>
                    <td colspan="5"><strong>Total</strong></td>
                    <td class="text-right"><%= String.format("%.2f", grandTotal) %></td>
                </tr>
            </table>
        </div>
    </body>
    </html>