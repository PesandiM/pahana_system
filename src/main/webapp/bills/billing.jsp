<%@ page import="java.util.List, model.Book, model.Stationery, model.BillItem, model.Customer" %>
<%
    List<Book> books = (List<Book>) request.getAttribute("books");
    List<Stationery> stationeries = (List<Stationery>) request.getAttribute("stationeries");
    List<BillItem> currentBill = (List<BillItem>) request.getAttribute("currentBill");
    List<Customer> customers = (List<Customer>) request.getAttribute("customers");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Item</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/bills.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
<%@ include file="/layout/header.jsp" %>
<h2>Create Bill</h2>
<div class="form-container">
<!-- ADD ITEM FORM -->
<form method="post" action="<%= request.getContextPath() %>/billing?action=addItem">
    <h3>Add Item</h3>

    <label>Item Type:</label>
    <select name="itemType" id="itemType" onchange="toggleDropdowns()">
        <option value="book">Book</option>
        <option value="stationery">Stationery</option>
    </select>

    <!-- Hidden input will always carry the actual itemId -->
    <input type="hidden" name="itemId" id="hiddenItemId"/>

    <!-- Book Dropdown -->
    <select name="bookDropdown" id="bookDropdown">
        <% if (books != null) {
               for (Book b : books) { %>
            <option value="<%= b.getId() %>"><%= b.getName() %> (Rs.<%= b.getPrice() %>)</option>
        <% }} %>
    </select>

    <!-- Stationery Dropdown -->
    <select name="stationeryDropdown" id="stationeryDropdown" style="display:none;">
        <% if (stationeries != null) {
               for (Stationery s : stationeries) { %>
            <option value="<%= s.getId() %>"><%= s.getName() %> (Rs.<%= s.getPrice() %>)</option>
        <% }} %>
    </select>

    <input type="number" name="quantity" value="1" min="1" />
    <button type="submit">Add Item</button>
</form>

<!-- FINALIZE FORM -->
    <form method="post" action="<%= request.getContextPath() %>/billing?action=finalize" onsubmit="return validateCustomerSelection()">
        <label>Customer:</label>
        <input type="text" id="customerSearch" placeholder="Search customer..." onkeyup="filterCustomers()" autocomplete="off"/>

        <!-- Dropdown (just for showing matches) -->
        <select id="customerDropdown" size="5" style="width:200px;" onclick="setCustomerId(this)">
            <% if (customers != null) {
                   for (Customer c : customers) { %>
               <option value="<%= c.getCustomerId() %>"><%= c.getName() %></option>
            <% }} %>
        </select>

        <!-- Hidden field (actual value submitted to backend) -->
        <input type="hidden" name="customerId" id="selectedCustomerId"/>

        <button type="submit">Finalize Bill</button>
    </form>
</div>

<h3>Current Bill</h3>
<% if (currentBill != null && !currentBill.isEmpty()) { %>
    <table border="1" cellpadding="5">
        <tr>
            <th>Type</th><th>Item</th><th>Qty</th><th>Total</th>
        </tr>
        <% double total = 0;
           for (BillItem item : currentBill) {
               total += item.getItemTotal();
               String name = "";
               if ("book".equalsIgnoreCase(item.getItemType())) {
                   for(Book b : books){
                       if(b.getId() == item.getItemId()){ name = b.getName(); break; }
                   }
               } else if ("stationery".equalsIgnoreCase(item.getItemType())) {
                   for(Stationery s : stationeries){
                       if(s.getId() == item.getItemId()){ name = s.getName(); break; }
                   }
               }
        %>
        <tr>
            <td><%= item.getItemType() %></td>
            <td><%= name %></td>
            <td><%= item.getQuantity() %></td>
            <td><%= item.getItemTotal() %></td>
        </tr>
        <% } %>
        <tr><td colspan="3"><strong>Grand Total</strong></td><td><%= total %></td></tr>
    </table>

<% } else { %>
    <p>No items yet.</p>
<% } %>

<script>
function toggleDropdowns() {
    var type = document.getElementById("itemType").value;
    var bookDD = document.getElementById("bookDropdown");
    var statDD = document.getElementById("stationeryDropdown");

    if (type === "book") {
        bookDD.style.display = "inline";
        statDD.style.display = "none";
        if (bookDD.options.length > 0) {
            updateItemId(bookDD.value);
        }
    } else {
        statDD.style.display = "inline";
        bookDD.style.display = "none";
        if (statDD.options.length > 0) {
            updateItemId(statDD.value);
        }
    }
}

function updateItemId(val) {
    document.getElementById("hiddenItemId").value = val;
}

// run once on load to initialize hidden value
window.onload = function() {
    toggleDropdowns();
};
let allCustomers = Array.from(document.querySelectorAll("#customerDropdown option"));

function filterCustomers() {
    let input = document.getElementById("customerSearch").value.toLowerCase();
    let dropdown = document.getElementById("customerDropdown");

    dropdown.innerHTML = ""; // clear
    allCustomers.forEach(opt => {
        if (opt.text.toLowerCase().includes(input)) {
            dropdown.appendChild(opt.cloneNode(true));
        }
    });
}

// when clicking an option in the dropdown, set hidden input
function setCustomerId(selectBox) {
    let selected = selectBox.options[selectBox.selectedIndex];
    if (selected) {
        document.getElementById("selectedCustomerId").value = selected.value;
        document.getElementById("customerSearch").value = selected.text; // show name in input
    }
}

// block submit if no customer chosen
function validateCustomerSelection() {
    let val = document.getElementById("selectedCustomerId").value;
    if (!val) {
        alert("Please select a customer.");
        return false;
    }
    return true;
}
</script>

</body>
</html>