package controller;

import dao.BookDAO;
import dao.ItemDAO;
import dao.StationeryDAO;
import dto.ItemDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Book;
import model.Stationery;
import service.ItemService;
import util.DBConn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/items")
public class ItemServlet extends HttpServlet {
    private ItemService service;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DBConn.getConnection();
            ItemDAO<Book> bookDAO = new BookDAO(connection);
            ItemDAO<Stationery> stationeryDAO = new StationeryDAO(connection);
            service = new ItemService(bookDAO, stationeryDAO);
        } catch (SQLException e) {
            throw new ServletException("DB connection init failed", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list"; // default action

        switch (action) {
            case "list":
                showItems(req, res);
                break;
            case "delete":
                deleteItem(req, res);
                break;
            case "edit":
                showEditForm(req, res);
                break;
            case "addForm":
                showAddItemForm(req, res);
                break;
            default:
                showItems(req, res);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "add"; // default post action

        switch (action) {
            case "add":
                addItem(req, res);
                break;
            case "update":
                updateItem(req, res);
                break;
            case "loadForBilling":
                loadItemsForBilling(req, res);
                break;
            default:
                res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown POST action");
        }
    }

    // === Action methods ===

    private void showItems(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get separate lists
        List<Book> books = service.getAllBooks();
        System.out.println("DEBUG books loaded: " + books.size());
        List<Stationery> stationeries = service.getAllStationery();
        System.out.println("DEBUG stationeries loaded: " + stationeries.size());

        req.setAttribute("books", books);
        req.setAttribute("stationeries", stationeries);

        req.getRequestDispatcher("/items/manageItems.jsp").forward(req, res);
    }

    private void showAddItemForm(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getRequestDispatcher("/items/addItems.jsp").forward(req, res);
    }

    private void addItem(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String itemType = req.getParameter("type");
        String itemName = req.getParameter("name");
        double price;
        int quantity;
        String author = req.getParameter("author");
        String isbn = req.getParameter("isbn");
        String manufacturer = req.getParameter("manufacturer");

        try {
            price = Double.parseDouble(req.getParameter("price"));
            quantity = Integer.parseInt(req.getParameter("quantity"));
        } catch (NumberFormatException e) {
            req.setAttribute("message", "Price must be a valid decimal number and quantity must be a valid integer.");
            req.setAttribute("status", "error");
            showItems(req, res);
            return;
        }

        ItemDTO dto = new ItemDTO(itemType, itemName, price, quantity, author, isbn, manufacturer);

        try {
            service.addItem(dto);
            req.setAttribute("message", "Item added successfully!");
            req.setAttribute("status", "success");
        } catch (Exception e) {
            req.setAttribute("message", "Failed to add item: " + e.getMessage());
            req.setAttribute("status", "error");
        }
        showItems(req, res);
    }
    private void loadItemsForBilling(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<Book> books = service.getAllBooks();
        List<Stationery> stationeries = service.getAllStationery();

        req.setAttribute("books", books);
        req.setAttribute("stationeries", stationeries);

        req.getRequestDispatcher("/billing/selectItems.jsp").forward(req, res);
    }

    private void deleteItem(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Example: delete based on item_id and type parameters
        try {
            int itemId = Integer.parseInt(req.getParameter("id"));
            String itemType = req.getParameter("type");
            service.deleteItem(itemType, itemId);
            req.setAttribute("message", "Item deleted successfully.");
            req.setAttribute("status", "success");
        } catch (Exception e) {
            req.setAttribute("message", "Failed to delete item: " + e.getMessage());
            req.setAttribute("status", "error");
        }
        showItems(req, res);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Implementation depends on your edit logic (fetch item by id/type and forward to form)
        // Not detailed here but follows similar pattern
    }

    private void updateItem(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Implementation depends on your update logic (process form, update DB, redirect/show)
        // Not detailed here but follows similar pattern
    }
}



