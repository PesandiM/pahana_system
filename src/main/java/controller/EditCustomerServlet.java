package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;
import service.CustomerService;

import java.io.IOException;

@WebServlet({"/edit-customer"})
public class EditCustomerServlet extends HttpServlet {
    private final CustomerService customerService = new CustomerService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("accountNo");
        if (accountNo != null && !accountNo.trim().isEmpty()) {
            Customer customer = this.customerService.getCustomerByAccountNo(accountNo);
            if (customer == null) {
                request.setAttribute("errorMessage", "Customer not found.");
                request.getRequestDispatcher("/customers/viewCustomer.jsp").forward(request, response);
            } else {
                request.setAttribute("customer", customer);
                request.getRequestDispatcher("/customers/editCustomer.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/customers/viewCustomer.jsp");
        }
    }
}
