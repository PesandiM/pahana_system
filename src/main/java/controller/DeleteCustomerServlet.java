package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CustomerService;

import java.io.IOException;
import java.net.URLEncoder;

@WebServlet({"/delete-customer"})
public class DeleteCustomerServlet extends HttpServlet {
    private final CustomerService customerService = new CustomerService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accountNo = request.getParameter("accountNo");
        boolean success = false;
        String message;
        if (accountNo != null && !accountNo.trim().isEmpty()) {
            success = this.customerService.deleteCustomerByAccountNo(accountNo);
            message = success ? "Customer deleted successfully" : "Failed to delete customer";
        } else {
            message = "Invalid account number";
        }

        response.sendRedirect(request.getContextPath() + "/customers/viewCustomer.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
    }
}
