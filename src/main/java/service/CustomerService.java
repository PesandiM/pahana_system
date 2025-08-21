package service;

import dao.CustomerDAO;
import dto.CustomerDTO;
import factory.CustomerFactory;
import model.Customer;

import java.util.List;

public class CustomerService {
    public boolean addCustomer(CustomerDTO customerDTO) {
        String generatedAccountNo = CustomerDAO.getNextAccountNo();
        customerDTO.setAccountNo(generatedAccountNo);
        Customer customer = CustomerFactory.createCustomer(customerDTO);
        System.out.println("Generated Account No: " + generatedAccountNo);
        return CustomerDAO.saveCustomer(customer);
    }

    public String generateNextAccountNo() {
        return CustomerDAO.getNextAccountNo();
    }

    public List<Customer> getAllCustomers() {
        return CustomerDAO.getAllCustomers();
    }

    public Customer getCustomerByAccountNo(String accountNo) {
        return CustomerDAO.getCustomerByAccountNo(accountNo);
    }

    public boolean updateCustomer(CustomerDTO customerDTO) {
        return CustomerDAO.updateCustomer(customerDTO);
    }

    public boolean deleteCustomerByAccountNo(String accountNo) {
        return CustomerDAO.deleteCustomerByAccountNo(accountNo);
    }
}
