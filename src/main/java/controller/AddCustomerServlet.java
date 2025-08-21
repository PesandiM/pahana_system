package controller;

import dto.CustomerDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CustomerService;

import java.io.IOException;

@WebServlet({"/add-customer"})
public class AddCustomerServlet extends HttpServlet {
    private final CustomerService customerService = new CustomerService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName(request.getParameter("name"));
        customerDTO.setEmail(request.getParameter("email"));
        customerDTO.setAddress(request.getParameter("address"));
        customerDTO.setTelephone(request.getParameter("telephone"));
        boolean success = this.customerService.addCustomer(customerDTO);
        if (success) {
            request.setAttribute("accountNo", customerDTO.getAccountNo());
            request.getRequestDispatcher("/success.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }

    }
}
