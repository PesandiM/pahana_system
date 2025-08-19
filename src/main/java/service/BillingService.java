package service;

import dao.BillDAO;
import dao.BookDAO;
import dao.CustomerDAO;
import dao.StationeryDAO;

import model.*;
import util.DBConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BillingService {

    private final BillDAO billDAO;
    private final BookDAO bookDAO;
    private final StationeryDAO stationeryDAO;

    public BillingService(BillDAO billDAO, BookDAO bookDAO, StationeryDAO stationeryDAO) {
        this.billDAO = billDAO;
        this.bookDAO = bookDAO;
        this.stationeryDAO = stationeryDAO;
    }

    public double getItemPrice(String itemType, int itemId) throws SQLException {
        if ("book".equalsIgnoreCase(itemType)) {
            Book b = bookDAO.getItem(itemId);
            return b != null ? b.getPrice() : 0;
        } else if ("stationery".equalsIgnoreCase(itemType)) {
            Stationery s = stationeryDAO.getItem(itemId);
            return s != null ? s.getPrice() : 0;
        }
        return 0;
    }

    public void enrichBillItems(List<BillItem> items) throws SQLException {
        for (BillItem item : items) {
            if ("book".equalsIgnoreCase(item.getItemType())) {
                Book b = bookDAO.getItem(item.getItemId());
                item.setItemName(b != null ? b.getName() : "Unknown Book");
                item.setItemPrice(b != null ? b.getPrice() : 0);
            } else if ("stationery".equalsIgnoreCase(item.getItemType())) {
                Stationery s = stationeryDAO.getItem(item.getItemId());
                item.setItemName(s != null ? s.getName() : "Unknown Stationery");
                item.setItemPrice(s != null ? s.getPrice() : 0);
            }
        }
    }

    public Bill finalizeBill(int customerId, int userId, List<BillItem> items) throws SQLException {
        double totalAmount = items.stream().mapToDouble(BillItem::getItemTotal).sum();

        Bill bill = new Bill();
        bill.setCustomerId(customerId);
        bill.setUserId(userId);
        bill.setTotalAmount(totalAmount);

        // Persist bill
        int billId = billDAO.createBill(bill);
        bill.setBillId(billId);

        for (BillItem item : items) {
            item.setBillId(billId);
        }

        billDAO.addBillItems(billId, items);

        // Enrich items
        for (BillItem item : items) {
            if ("book".equalsIgnoreCase(item.getItemType())) {
                Book b = bookDAO.getItem(item.getItemId());
                item.setItemName(b != null ? b.getName() : "Unknown Book");
                item.setItemPrice(b != null ? b.getPrice() : 0);
            } else if ("stationery".equalsIgnoreCase(item.getItemType())) {
                Stationery s = stationeryDAO.getItem(item.getItemId());
                item.setItemName(s != null ? s.getName() : "Unknown Stationery");
                item.setItemPrice(s != null ? s.getPrice() : 0);
            }
        }

        bill.setItems(items);

        // Populate customer info
        Customer customer = CustomerDAO.getCustomerById(customerId);
        if (customer != null) {
            bill.setCustomerName(customer.getName());
            bill.setCustomerEmail(customer.getEmail());
            bill.setCustomerPhone(customer.getTelephone());
            bill.setCustomerAddress(customer.getAddress());
        }

        updateStock(items);

        CustomerDAO customerDAO = new CustomerDAO();
        customerDAO.incrementTotalPurchases(customerId);

        bill.setBillDate(new java.util.Date());

        return bill;
    }

    private void updateStock(List<BillItem> items) throws SQLException {
        try (Connection conn = DBConn.getConnection()) {
            for (BillItem item : items) {
                String updateSql = "UPDATE items SET quantity = quantity - ? WHERE item_id = ? AND item_type = ?";
                try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                    ps.setInt(1, item.getQuantity());
                    ps.setInt(2, item.getItemId());
                    ps.setString(3, item.getItemType());
                    ps.executeUpdate();
                }
            }
        }
    }

    public Bill getBillWithItems(int billId) throws SQLException {
        Bill bill = billDAO.getBillWithItems(billId);
        if (bill != null && bill.getItems() != null) {
            enrichBillItems(bill.getItems());
        }
        return bill;
    }

    public List<Bill> getAllBillsForDashboard() throws SQLException {
        return billDAO.getAllBillsForDashboard();
    }

}