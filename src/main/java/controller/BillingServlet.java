package controller;
import dao.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;
import service.BillingService;
import util.DBConn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/billing")
public class BillingServlet extends HttpServlet {

    private BillingService billingService;

    @Override
    public void init() throws ServletException {
        try (Connection conn = DBConn.getConnection()) {
            BookDAO bookDAO = new BookDAO(conn);
            StationeryDAO stationeryDAO = new StationeryDAO(conn);
            BillDAO billDAO = new BillDAO();
            billingService = new BillingService(billDAO, bookDAO, stationeryDAO);
        } catch (SQLException e) {
            throw new ServletException("Failed to initialize BillingService", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        List<BillItem> currentBill = (List<BillItem>) session.getAttribute("currentBill");
        if (currentBill == null) {
            currentBill = new ArrayList<>();
            session.setAttribute("currentBill", currentBill);
        }

        if ("addItem".equals(action)) {
            handleAddItem(request, response, currentBill);
        } else if ("finalize".equals(action)) {
            handleFinalize(request, response, currentBill, session);
        } else if ("viewBill".equals(action)) {
            handleViewBill(request, response);
        }
    }

    private void handleAddItem(HttpServletRequest request, HttpServletResponse response, List<BillItem> currentBill) throws IOException {
        String itemType = request.getParameter("itemType");
        String itemIdParam = "book".equalsIgnoreCase(itemType) ? request.getParameter("bookDropdown")
                : "stationery".equalsIgnoreCase(itemType) ? request.getParameter("stationeryDropdown") : null;

        if (itemIdParam == null || itemIdParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/billing?error=noitem");
            return;
        }

        int itemId = Integer.parseInt(itemIdParam);
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        try {
            double price = billingService.getItemPrice(itemType, itemId);
            double itemTotal = price * quantity;

            BillItem billItem = new BillItem();
            billItem.setItemType(itemType);
            billItem.setItemId(itemId);
            billItem.setQuantity(quantity);
            billItem.setItemPrice(price);
            billItem.setItemTotal(itemTotal);

            currentBill.add(billItem);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching item price", e);
        }

        response.sendRedirect(request.getContextPath() + "/billing");
    }

    private void handleFinalize(HttpServletRequest request, HttpServletResponse response, List<BillItem> currentBill, HttpSession session) throws ServletException, IOException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        int userId = 1;

        try {
            Bill bill = billingService.finalizeBill(customerId, userId, currentBill);

            request.setAttribute("bill", bill);
            session.removeAttribute("currentBill");
            request.getRequestDispatcher("bills/printBill.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void handleViewBill(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int billId = Integer.parseInt(request.getParameter("billId"));
        try {
            Bill bill = billingService.getBillWithItems(billId);
            request.setAttribute("bill", bill);
            request.getRequestDispatcher("bills/printBill.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("listBills".equals(action)) {
            try {
                List<Bill> bills = billingService.getAllBillsForDashboard();
                request.setAttribute("bills", bills);
                request.getRequestDispatcher("bills/viewBills.jsp").forward(request, response);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
            return;
        }

        try (Connection conn = DBConn.getConnection()) {
            BookDAO bookDAO = new BookDAO(conn);
            StationeryDAO stationeryDAO = new StationeryDAO(conn);

            request.setAttribute("books", bookDAO.getAllItems());
            request.setAttribute("stationeries", stationeryDAO.getAllItems());
            request.setAttribute("customers", CustomerDAO.getAllCustomers());

            HttpSession session = request.getSession();
            request.setAttribute("currentBill", session.getAttribute("currentBill"));

            request.getRequestDispatcher("bills/billing.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
