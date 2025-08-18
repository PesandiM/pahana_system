<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%
    User user = (User) session.getAttribute("user");
    String homePage = "staffDashboard.jsp";

    if (user != null && "admin".equalsIgnoreCase(user.getRole())) {
        homePage = "adminDashboard.jsp";
    }
%>
<header class="main-header">
    <div class="header-content">
        <h1 class="dashboard-title">Pahana Bookshop</h1>
        <div class="header-actions">
            <a href="<%= request.getContextPath() + "/" + homePage %>" class="header-button">Home</a>
            <a href="<%=request.getContextPath()%>/logout" class="header-button">Logout</a>
        </div>
    </div>
</header>