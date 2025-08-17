package factory;

import dto.CustomerDTO;
import model.Customer;

public class CustomerFactory {
    public static Customer createCustomer(CustomerDTO dto) {
        return new Customer(dto.getAccountNo(), dto.getName(), dto.getEmail(), dto.getAddress(), dto.getTelephone());
    }
}
