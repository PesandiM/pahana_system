<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.sql.*, util.DBConn" %>
<%
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
%>
<!DOCTYPE html>
<html>
<head>
    <title>View Customers</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/customer.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
<%@ include file="/layout/header.jsp" %>
    <div class="table-container">
        <h2>Customer List</h2>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Account No</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Address</th>
                    <th>Telephone</th>
                    <th>Active</th>
                    <th>Total Purchases</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    try {
                        conn = DBConn.getConnection();
                        String sql = "SELECT * FROM customers";
                        stmt = conn.prepareStatement(sql);
                        rs = stmt.executeQuery();

                        while (rs.next()) {
                            int customerId = rs.getInt("customer_id");
                            String accountNo = rs.getString("account_no");
                %>
                    <tr>
                        <td><%= customerId %></td>
                        <td><%= accountNo %></td>
                        <td><%= rs.getString("name") %></td>
                        <td><%= rs.getString("email") %></td>
                        <td><%= rs.getString("address") %></td>
                        <td><%= rs.getString("telephone") %></td>
                        <td><%= rs.getBoolean("is_active") ? "Yes" : "No" %></td>
                        <td><%= rs.getInt("total_purchases") %></td>
                        <td>
                            <a href="<%= request.getContextPath() %>/edit-customer?accountNo=<%= accountNo %>" title="Edit">
                                &#9998; <!-- Pencil icon -->
                            </a>
                            &nbsp;&nbsp;
                            <a href="<%= request.getContextPath() %>/delete-customer?accountNo=<%= accountNo %>"
                               title="Delete"
                               onclick="return confirm('Are you sure you want to delete customer <%= accountNo %>?');">
                               &#128465; <!-- Trash bin icon -->
                            </a>
                        </td>
                    </tr>
                <%
                        }
                    } catch (Exception e) {
                        out.println("<tr><td colspan='9'>Error: " + e.getMessage() + "</td></tr>");
                    } finally {
                        if (rs != null) rs.close();
                        if (stmt != null) stmt.close();
                        if (conn != null) conn.close();
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>