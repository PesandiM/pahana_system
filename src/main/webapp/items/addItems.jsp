<%@ page contentType="text/html; charset=UTF-8" language="java" %>
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
        <h2>Add New Item</h2>

        <form action="<%= request.getContextPath() %>/items" method="post">
            <div class="form-group">
                <label for="type">Item Type:</label>
                <select id="type" name="type" required onchange="toggleFields()">
                    <option value="">Select Item Type</option>
                    <option value="book">Book</option>
                    <option value="stationery">Stationery</option>
                </select>
            </div>

            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" required>
            </div>

            <div class="form-group">
                <label for="price">Price:</label>
                <input type="number" id="price" name="price" step="0.01" min="0" required>
            </div>

            <div class="form-group">
                <label for="quantity">Quantity:</label>
                <input type="number" id="quantity" name="quantity" min="1" required>
            </div>

            <!-- Book Fields -->
            <div id="bookFields" class="conditional-field">
                <div class="form-group">
                    <label for="author">Author:</label>
                    <input type="text" id="author" name="author">
                </div>
                <div class="form-group">
                    <label for="isbn">ISBN:</label>
                    <input type="text" id="isbn" name="isbn">
                </div>
            </div>

            <!-- Stationery Fields -->
            <div id="stationeryFields" class="conditional-field">
                <div class="form-group">
                    <label for="manufacturer">Manufacturer:</label>
                    <input type="text" id="manufacturer" name="manufacturer">
                </div>
            </div>

            <button type="submit">Add Item</button>
        </form>
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