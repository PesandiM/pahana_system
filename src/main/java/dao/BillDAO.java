package dao;

import dto.CustomerSummaryDTO;
import model.Bill;
import model.BillItem;
import util.DBConn;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    public int createBill(Bill bill) throws SQLException {
        String sql = "INSERT INTO bills (customer_id, user_id, bill_date, total_amount) VALUES (?, ?, NOW(), ?)";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setObject(1, bill.getCustomerId());
            ps.setObject(2, bill.getUserId());
            ps.setDouble(3, bill.getTotalAmount());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // return bill_id
                }
            }
        }
        return -1;
    }

    // add items to a bill
    public void addBillItems(int billId, List<BillItem> items) throws SQLException {
        String sql = "INSERT INTO bill_items (bill_id,item_type, item_id, quantity, item_price, item_total) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (BillItem item : items) {
                ps.setInt(1, billId);
                ps.setString(2, item.getItemType());
                ps.setInt(3, item.getItemId());
                ps.setInt(4, item.getQuantity());
                ps.setDouble(5, item.getItemPrice());
                ps.setDouble(6, item.getItemTotal());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    // fetch all bills (without items)
    public List<Bill> getAllBills() throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bills ORDER BY bill_date DESC";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setCustomerId(rs.getInt("customer_id"));
                bill.setUserId(rs.getInt("user_id"));
                bill.setBillDate(rs.getTimestamp("bill_date"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bills.add(bill);
            }
        }
        return bills;
    }

    public Bill getBillWithItems(int billId) throws SQLException {
        String sql = "SELECT b.bill_id, b.customer_id, b.bill_date, b.total_amount, " +
                "c.name AS customer_name, c.email AS customer_email, " +
                "c.telephone AS customer_phone, c.address AS customer_address " +
                "FROM bills b " +
                "LEFT JOIN customers c ON b.customer_id = c.customer_id " +
                "WHERE b.bill_id = ?";

        Bill bill = null;

        try (Connection conn = DBConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, billId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    bill = new Bill();
                    bill.setBillId(rs.getInt("bill_id"));
                    bill.setCustomerId(rs.getInt("customer_id"));
                    bill.setBillDate(rs.getTimestamp("bill_date"));
                    bill.setTotalAmount(rs.getDouble("total_amount"));

                    bill.setCustomerName(rs.getString("customer_name"));
                    bill.setCustomerEmail(rs.getString("customer_email"));
                    bill.setCustomerPhone(rs.getString("customer_phone"));
                    bill.setCustomerAddress(rs.getString("customer_address"));
                }
            }
        }

        // --- Fetch bill items separately ---
        if (bill != null) {
            String itemSql = "SELECT bi.item_type, bi.item_id, bi.quantity, bi.item_price, bi.item_total, " +
                    "i.item_name " +
                    "FROM bill_items bi " +
                    "LEFT JOIN items i ON bi.item_id = i.item_id " +
                    "WHERE bi.bill_id = ?";
            try (Connection conn = DBConn.getConnection();
                 PreparedStatement ps = conn.prepareStatement(itemSql)) {
                ps.setInt(1, billId);
                try (ResultSet rs = ps.executeQuery()) {
                    List<BillItem> items = new ArrayList<>();
                    while (rs.next()) {
                        BillItem item = new BillItem();
                        item.setItemType(rs.getString("item_type"));
                        item.setItemId(rs.getInt("item_id"));
                        item.setItemName(rs.getString("item_name"));
                        item.setQuantity(rs.getInt("quantity"));
                        item.setItemPrice(rs.getDouble("item_price"));
                        item.setItemTotal(rs.getDouble("item_total"));
                        items.add(item);
                    }
                    bill.setItems(items);
                }
            }
        }

        return bill;
    }

    // fetch bill items for a bill (helpful for bill details page)
    public List<BillItem> getBillItems(int billId) throws SQLException {
        List<BillItem> items = new ArrayList<>();
        String sql = "SELECT bill_item_id, bill_id, item_id, quantity, item_price, item_total " +
                "FROM bill_items WHERE bill_id = ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, billId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BillItem item = new BillItem();
                    item.setBillItemId(rs.getInt("bill_item_id"));
                    item.setBillId(rs.getInt("bill_id"));
                    item.setItemId(rs.getInt("item_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setItemPrice(rs.getDouble("item_price"));
                    item.setItemTotal(rs.getDouble("item_total"));
                    items.add(item);
                }
            }
        }
        return items;
    }

    public List<Bill> getAllBillsForDashboard() throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT b.bill_id, b.customer_id, c.name AS customer_name, b.bill_date, b.total_amount " +
                "FROM bills b LEFT JOIN customers c ON b.customer_id = c.customer_id " +
                "ORDER BY b.bill_date DESC";

        try (Connection conn = DBConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setCustomerId(rs.getInt("customer_id"));
                bill.setBillDate(rs.getTimestamp("bill_date"));
                bill.setTotalAmount(rs.getDouble("total_amount"));

                // temporary holder for display
                bill.setCustomerName(rs.getString("customer_name"));

                bills.add(bill);
            }
        }
        return bills;
    }
    public List<CustomerSummaryDTO> getCustomerPurchases(String accountNo) throws SQLException {
        List<CustomerSummaryDTO> purchases = new ArrayList<>();

        String sql = "{CALL getCustomerPurchasesByAccount(?)}";

        try (Connection conn = DBConn.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, accountNo);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    CustomerSummaryDTO dto = new CustomerSummaryDTO();
                    dto.setBillId(rs.getInt("bill_id"));
                    dto.setDate(rs.getDate("bill_date"));
                    dto.setItemName(rs.getString("item_name"));
                    dto.setItemType(rs.getString("item_type"));
                    dto.setQuantity(rs.getInt("quantity"));
                    dto.setPrice(rs.getDouble("item_price"));
                    dto.setTotal(rs.getDouble("item_total"));
                    purchases.add(dto);
                }
            }
        }

        return purchases;
    }
}
