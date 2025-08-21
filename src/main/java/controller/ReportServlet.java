package controller;

import dto.CustomerDTO;
import dto.CustomerSummaryDTO;
import dto.StockReportDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;
import service.ReportService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/reports")
public class ReportServlet extends HttpServlet {

    private final ReportService reportService = new ReportService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            // default: go to reports menu
            request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
            return;
        }

        switch (action) {
            case "stock":
                showStockReport(request, response);
                break;

            case "customer":
                showCustomerReport(request, response);
                break;
            case "sales":
                // showSalesReport(request, response);
                response.getWriter().println("Sales Report - Work in Progress");
                break;
            default:
                response.getWriter().println("Invalid report action");
        }
    }

    private void showStockReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<StockReportDTO> stockList = reportService.getStockSummary();
            request.setAttribute("stockList", stockList);
            request.getRequestDispatcher("/reports/stockReport.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error fetching stock summary", e);
        }
    }

    private void showCustomerReport(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Load all customers for dropdown
            List<Customer> customers = reportService.getAllCustomers();
            request.setAttribute("customers", customers);

            String accountNo = request.getParameter("customerId");

            if (accountNo == null || accountNo.isEmpty()) {
                request.setAttribute("purchases", null);
            } else {
                try {
                    List<CustomerSummaryDTO> purchases = reportService.getCustomerPurchases(accountNo);
                    request.setAttribute("purchases", purchases);
                } catch (SQLException e) {
                    throw new ServletException("Error fetching customer purchases", e);
                }
            }

            request.getRequestDispatcher("/reports/customerPurchases.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error fetching customer purchases", e);
        }
    }
}
