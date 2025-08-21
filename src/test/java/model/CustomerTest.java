package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    @Test
    void testFullConstructorAndGetters() {
        Customer customer = new Customer(
                1,
                "PEDU003",
                "Alice Smith",
                "alice.smith@example.com",
                "123 Main Street, Springfield",
                "0716536995",
                true,
                25
        );

        assertEquals(1, customer.getCustomerId());
        assertEquals("PEDU003", customer.getAccountNo());
        assertEquals("Alice Smith", customer.getName());
        assertEquals("alice.smith@example.com", customer.getEmail());
        assertEquals("123 Main Street, Springfield", customer.getAddress());
        assertEquals("0716536995", customer.getTelephone());
        assertTrue(customer.isActive());
        assertEquals(25, customer.getTotalPurchases());
    }

    @Test
    void testPartialConstructorAndSetters() {
        Customer customer = new Customer(
                "PEDU003",
                "Bob Johnson",
                "bob.johnson@example.com",
                "456 Oak Avenue, Shelbyville",
                "0716539986"
        );

        // Default values before setting
        assertEquals(0, customer.getCustomerId());
        assertEquals(0, customer.getTotalPurchases());
        assertFalse(customer.isActive()); // default false

        // Update using setters
        customer.setCustomerId(2);
        customer.setActive(true);
        customer.setTotalPurchases(10);

        assertEquals(2, customer.getCustomerId());
        assertTrue(customer.isActive());
        assertEquals(10, customer.getTotalPurchases());
    }

    @Test
    void testSettersAndGetters() {
        Customer customer = new Customer();

        customer.setCustomerId(3);
        customer.setAccountNo("ACC77777");
        customer.setName("Charlie Brown");
        customer.setEmail("charlie.brown@example.com");
        customer.setAddress("789 Pine Road, Capital City");
        customer.setTelephone("+1-555-9012");
        customer.setActive(false);
        customer.setTotalPurchases(5);

        assertEquals(3, customer.getCustomerId());
        assertEquals("ACC77777", customer.getAccountNo());
        assertEquals("Charlie Brown", customer.getName());
        assertEquals("charlie.brown@example.com", customer.getEmail());
        assertEquals("789 Pine Road, Capital City", customer.getAddress());
        assertEquals("+1-555-9012", customer.getTelephone());
        assertFalse(customer.isActive());
        assertEquals(5, customer.getTotalPurchases());
    }
}