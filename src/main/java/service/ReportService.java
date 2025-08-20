package service;

import dao.BillDAO;
import dao.CustomerDAO;
import dao.ItemReportDAO;
import dao.ItemReportDAOImpl;
import dto.CustomerSummaryDTO;
import dto.StockReportDTO;
import model.Customer;

import java.sql.SQLException;
import java.util.List;

public class ReportService {
    private final ItemReportDAO itemReportDAO = new ItemReportDAOImpl();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final BillDAO billDAO = new BillDAO();

    public List<StockReportDTO> getStockSummary() throws SQLException {
        return itemReportDAO.getStockSummary();
    }

    public List<Customer> getAllCustomers() throws SQLException {
        return customerDAO.getAllCustomers();
    }

    public List<CustomerSummaryDTO> getCustomerPurchases(String customerAccNo) throws SQLException {
        return billDAO.getCustomerPurchases(customerAccNo);
    }
}

