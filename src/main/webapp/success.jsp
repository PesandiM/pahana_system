<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Success</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/items.css">
</head>
<body>
    <h1><%= request.getAttribute("itemName") != null ? "Item " + request.getAttribute("itemName") : "Customer " + request.getAttribute("accountNo") %> added successfully!</h1>
    <form action="<%= request.getContextPath() %>/staffDashboard.jsp" method="get">
        <button type="submit">Back to Main Page</button>
    </form>
</body>
</html>
