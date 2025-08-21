<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.sql.*, util.DBConn" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Book" %>
<%@ page import="model.Stationery" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Customers</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/items.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
<%@ include file="/layout/header.jsp" %>
<div class="table-container">
    <h2>Books</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th><th>Name</th><th>Price</th><th>Qty</th><th>Author</th><th>ISBN</th><th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Book> books = (List<Book>) request.getAttribute("books");
                if (books != null && !books.isEmpty()) {
                    for (Book book : books) {
            %>
                <tr>
                    <td><%= book.getId() %></td>
                    <td><%= book.getName() %></td>
                    <td><%= book.getPrice() %></td>
                    <td><%= book.getQuantity() %></td>
                    <td><%= book.getAuthor() %></td>
                    <td><%= book.getIsbn() %></td>
                    <td>
                        <a href="items?action=edit&id=<%= book.getId() %>&type=book">Edit</a> |
                        <a href="items?action=delete&id=<%= book.getId() %>&type=book"
                           onclick="return confirm('Delete book <%= book.getName() %>?')">Delete</a>
                    </td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr><td colspan="7">No books found.</td></tr>
            <%
                }
            %>
        </tbody>
    </table>

    <h2>Stationery</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th><th>Name</th><th>Price</th><th>Qty</th><th>Manufacturer</th><th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Stationery> stationeries = (List<Stationery>) request.getAttribute("stationeries");
                if (stationeries != null && !stationeries.isEmpty()) {
                    for (Stationery stationery : stationeries) {
            %>
                <tr>
                    <td><%= stationery.getId() %></td>
                    <td><%= stationery.getName() %></td>
                    <td><%= stationery.getPrice() %></td>
                    <td><%= stationery.getQuantity() %></td>
                    <td><%= stationery.getManufacturer() %></td>
                    <td>
                        <a href="items?action=edit&id=<%= stationery.getId() %>&type=stationery">Edit</a> |
                        <a href="items?action=delete&id=<%= stationery.getId() %>&type=stationery"
                           onclick="return confirm('Delete stationery <%= stationery.getName() %>?')">Delete</a>
                    </td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr><td colspan="6">No stationery found.</td></tr>
            <%
                }
            %>
        </tbody>
    </table>
</div>
</body>
</html>