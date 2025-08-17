package controller;

import dto.CustomerDTO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CustomerService;

import java.io.IOException;
import java.net.URLEncoder;

@WebServlet({"/update-customer"})
public class UpdateCustomerServlet extends HttpServlet {
    private final CustomerService customerService = new CustomerService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accountNo = request.getParameter("accountNo");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");
        boolean success = false;
        String message = "";

        try {
            CustomerDTO dto = new CustomerDTO();
            dto.setAccountNo(accountNo);
            dto.setEmail(email);
            dto.setAddress(address);
            dto.setTelephone(telephone);
            success = this.customerService.updateCustomer(dto);
            message = success ? "Customer updated successfully" : "Failed to update customer";
        } catch (Exception e) {
            message = "Error: " + e.getMessage();
        }

        response.sendRedirect(request.getContextPath() + "/customers/viewCustomer.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
    }
}
