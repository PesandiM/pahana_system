<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    model.Item item = (model.Item) request.getAttribute("item");
    String errorMessage = (String) request.getAttribute("errorMessage");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Item</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/items.css">
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
<%@ include file="/layout/header.jsp" %>
    <div class="form-container">
        <h2>Edit Item</h2>

        <% if (errorMessage != null) { %>
            <p class="error-message"><%= errorMessage %></p>
        <% } %>

        <% if (item != null) { %>
        <form action="<%= request.getContextPath() %>/items" method="post">
            <input type="hidden" name="action" value="update" />
            <input type="hidden" name="id" value="<%= item.getId() %>" />

            <label>Name:</label>
            <input type="text" name="name" value="<%= item.getName() %>" required />

            <label>Type:</label>
            <input type="text" name="type" value="<%= item.getCategory() %>" required />

            <label>Quantity:</label>
            <input type="number" name="quantity" value="<%= item.getQuantity() %>" required min="0" />

            <label>Price:</label>
            <input type="number" name="price" value="<%= item.getPrice() %>" required step="0.01" min="0" />

            <!-- Book Fields -->
            <div id="bookFields" class="conditional-field" style="display: <%= "book".equals(item.getCategory()) ? "block" : "none" %>;">
                <label>Author:</label>
                <input type="text" name="author" value="<%= item instanceof model.Book ? ((model.Book)item).getAuthor() : "" %>" />

                <label>ISBN:</label>
                <input type="text" name="isbn" value="<%= item instanceof model.Book ? ((model.Book)item).getIsbn() : "" %>" />
            </div>

            <!-- Stationery Fields -->
            <div id="stationeryFields" class="conditional-field" style="display: <%= "stationery".equals(item.getCategory()) ? "block" : "none" %>;">
                <label>Manufacturer:</label>
                <input type="text" name="manufacturer" value="<%= item instanceof model.Stationery ? ((model.Stationery)item).getManufacturer() : "" %>" />
            </div>

            <button type="submit">Update Item</button>
        </form>
        <% } else { %>
            <p>Item details not available.</p>
        <% } %>
    </div>
    <script>
            function toggleFields() {
                const type = document.getElementById('type').value;
                document.getElementById('bookFields').style.display =
                    type === 'book' ? 'block' : 'none';
                document.getElementById('stationeryFields').style.display =
                    type === 'stationery' ? 'block' : 'none';

                // Clear conditional fields when switching types
                if (type !== 'book') {
                    document.getElementById('author').value = '';
                    document.getElementById('isbn').value = '';
                }
                if (type !== 'stationery') {
                    document.getElementById('manufacturer').value = '';
                }
            }
            <% if (request.getAttribute("message") != null) { %>
                alert("<%= request.getAttribute("message") %>");
            <% } %>
        </script>
</body>
</html>