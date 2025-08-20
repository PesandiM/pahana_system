<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Help - Staff Dashboard</title>

    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
    <style>
    .main-content { margin-left: 60px; flex-grow: 1; padding: 20px; }
        .help-section { margin-bottom: 20px; margin-left: 20px;}
        .main-content h2 { text-align:center; color: #8c5444; margin-bottom: 50px;}
        .help-section h3 { margin-bottom: 10px; color: #333; }
        .help-section p { margin-bottom: 10px; line-height: 1.6; }
        .help-section ul { margin-left: 20px; }
        .help-section li { margin-bottom: 5px; }
    </style>
</head>
<body>
<%@ include file="/layout/header.jsp" %>
    <div class="main-content">
        <h2>Help - System Usage Guidelines</h2>
        <p>Welcome to the Staff Dashboard Help Section. This guide provides step-by-step instructions for using the system effectively.
        Use the sidebar to navigate to different functionalities.</p>

        <div class="help-section">
            <h3>1. User Authentication (Login)</h3>
            <p>Access the system by logging in with your credentials:</p>
            <ul>
                <li>Navigate to the login page from the dashboard.</li>
                <li>Enter your username and password provided by the administrator.</li>
                <li>Click "Login" to access the dashboard. Contact the administrator if you encounter issues.</li>
            </ul>
        </div> <hr/>

        <div class="help-section">
            <h3>2. Managing Customer Accounts</h3>
            <p>Add or edit customer information to keep records up to date:</p>
            <ul>
                <li><strong>Add New</strong>: Go to Customers > Add New. Fill in details like account number, name, address, and telephone number, then submit.</li>
                <li><strong>View Customers</strong>: Go to Customers > View customers, from the customer details table click on edit/delete button according to the name you want to edit
                <br>and update their details or delete the record as needed.</li>
            </ul>
        </div> <hr/>

        <div class="help-section">
            <h3>3. Managing Inventory</h3>
            <p>Manage items (books and stationery) in the inventory:</p>
            <ul>
                <li><strong>Add Items</strong>: Navigate to Inventory > Add Items. Enter item details (name, type, quantity, price) and submit.</li>
                <li><strong>Update/Delete Items</strong>: Go to Inventory > Current Stock to view items. Select an item to update its details or delete it.</li>
                <li><strong>Low Stock Alert</strong>: Check Inventory > Low Stock Alert to identify items with low quantities.</li>
            </ul>
        </div> <hr/>

        <div class="help-section">
            <h3>4. Billing</h3>
            <p>Create and manage customer bills:</p>
            <ul>
                <li><strong>Add Bill</strong>: Go to Bills > Add Bill. Select a customer, add items with quantities, and compute the total amount.</li>
                <li><strong>View Bills</strong>: Navigate to Bills > View Bills to see all bills or print specific ones.</li>
                <li><strong>Print Bill</strong>: From View Bills, select a bill and choose the print option to generate a printable receipt.</li>
            </ul>
        </div> <hr/>

        <div class="help-section">
            <h3>5. Troubleshooting</h3>
            <p>Common issues and solutions:</p>
            <ul>
                <li><strong>Login Failure</strong>: Ensure your username and password are correct. Contact the administrator to reset credentials if needed.</li>
                <li><strong>Data Not Displaying</strong>: Refresh the page or check your internet connection. Report persistent issues to the administrator.</li>
                <li><strong>Error Messages</strong>: If an error appears, note the message and contact the administrator for assistance.</li>
            </ul>
        </div> <hr/>

        <div class="help-section">
            <h3>6. Exiting the System</h3>
            <p>To exit, click the "Logout" button (if available) or close the browser. Ensure all changes are saved before exiting.</p>
        </div>

        <p style="color: red;">For further assistance, contact the system administrator.</p>
    </div>
</body>
</html>