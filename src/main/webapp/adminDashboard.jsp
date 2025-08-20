<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="model.User" %>
<%
    User user = (User ) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    String username = user.getUsername();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="css/dashboard.css">
</head>
<body data-context-path="<%= request.getContextPath() %>">
    <div class="sidebar">
      <div class="menu">
                  <div class="menu-item">
                      <div class="menu-title"><i class="icon-customers"></i> Customers</div>
                      <div class="sub-menu">
                          <div class="sub-menu-item"><a href="customers/addCustomer.jsp">Add New</a></div>
                          <div class="sub-menu-item"><a href="customers/viewCustomer.jsp">View All</a></div>
                      </div>
                  </div>

                  <div class="menu-item">
                      <div class="menu-title"><i class="icon-inventory"></i> Inventory</div>
                      <div class="sub-menu">
                          <div class="sub-menu-item"><a href="<%= request.getContextPath() %>/items?action=addForm">Add Items</a></div>
                          <div class="sub-menu-item"><a href="<%= request.getContextPath() %>/items?action=list">Current Stock</a></div>
                      </div>
                  </div>

                  <div class="menu-item">
                      <div class="menu-title"><i class="icon-reports"></i> Reports</div>
                      <div class="sub-menu">
                          <div class="sub-menu-item"><a href="<%= request.getContextPath() %>/reports?action=stock">Stock Report</a></div>
                          <div class="sub-menu-item"><a href="<%= request.getContextPath() %>/reports?action=customer">Customer purchases</a></div>
                      </div>
                  </div>

                  <div class="menu-item">
                      <div class="menu-title"><i class="icon-users"></i> User Management</div>
                      <div class="sub-menu">
                          <div class="sub-menu-item"><a href="<%= request.getContextPath() %>/users?action=new">Add New User</a></div>
                          <div class="sub-menu-item"><a href="<%= request.getContextPath() %>/users?action=list">User Accounts</a></div>
                      </div>
                  </div>
                  <div class="menu-item">
                      <a href="<%= request.getContextPath() %>/logout" class="menu-title"><i class="icon-logout"></i> Logout</a>
                  </div>
              </div>
    </div>

    <div id="mainContent" class="main-content">
        <h2>Pahana Edu Bookshop - Admin Dashboard</h2>
        <p>Select an option from the sidebar</p>
    </div>

    <script>
            const menuTitles = document.querySelectorAll('.menu-title');

            menuTitles.forEach(title => {
                title.addEventListener('click', () => {
                    const parent = title.parentElement; // menu-item
                    parent.classList.toggle('active');

                    // Close other open menus (optional)
                    menuTitles.forEach(otherTitle => {
                        if (otherTitle !== title) {
                            otherTitle.parentElement.classList.remove('active');
                        }
                    });
                });
            });
        </script>
</body>
</html>