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
        customerDAO.updatePurchaseStats(customerId, totalAmount);

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

//    public void sendInvoiceEmail(int billId) {
//        Bill bill = billDAO.getBillById(billId);
//        if (bill == null) return;
//        Customer customer = customerDAO.getCustomerById(bill.getCustomerId());
//        if (customer == null || customer.getEmail() == null || customer.getEmail().isEmpty()) return;
//
//        List<BillItem> items = billDAO.getBillItems(billId);
//        StringBuilder content = new StringBuilder();
//        content.append("<h2>Pahana Bookshop Invoice</h2>")
//                .append("<p>Invoice #: ").append(billId)
//                .append("<br>Date: ").append(bill.getBillDate())
//                .append("<br>Customer: ").append(customer.getName())
//                .append("<br>Total: $").append(String.format("%.2f", bill.getTotal())).append("</p>")
//                .append("<table border='1' style='border-collapse: collapse; width: 100%; max-width: 600px;'>")
//                .append("<tr><th>Item ID</th><th>Name</th><th>Quantity</th><th>Price</th><th>Total</th></tr>");
//        for (BillItem item : items) {
//            content.append("<tr>")
//                    .append("<td>").append(item.getItemId()).append("</td>")
//                    .append("<td>").append(item.getItemName()).append("</td>")
//                    .append("<td>").append(item.getQuantity()).append("</td>")
//                    .append("<td>$").append(String.format("%.2f", item.getItemPrice())).append("</td>")
//                    .append("<td>$").append(String.format("%.2f", item.getItemTotal())).append("</td>")
//                    .append("</tr>");
//        }
//        content.append("</table>");
//
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//
//        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
//            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
//                return new javax.mail.PasswordAuthentication("your-email@gmail.com", "your-app-specific-password");
//            }
//        });
//
//        try {
//            MimeMessage message = new MimeMessage(session);
//            message.setFrom(new InternetAddress("your-email@gmail.com"));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(customer.getEmail()));
//            message.setSubject("Pahana Bookshop Invoice #" + billId);
//            message.setContent(content.toString(), "text/html");
//            Transport.send(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}