<h2>Customer Report</h2>
<table border="1">
  <tr>
    <th>Customer</th><th>Total Purchases</th><th>Total Spent</th><th>Actions</th>
  </tr>
  <c:forEach var="c" items="${customers}">
    <tr>
      <td>${c.customerName}</td>
      <td>${c.totalPurchases}</td>
      <td>${c.totalSpent}</td>
      <td>
        <a href="reports?action=purchases&customerId=${c.customerId}">View Purchases</a>
      </td>
    </tr>
  </c:forEach>
</table>
