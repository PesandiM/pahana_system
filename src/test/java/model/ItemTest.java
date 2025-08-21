package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    // A simple concrete subclass of Item for testing
    static class TestItem extends Item {
        public TestItem(int id, String name, double price, int quantity, String category) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.category = category;
        }

        @Override
        public void applyDiscount() {

        }
    }

    @Test
    void testGettersAndSetters() {
        TestItem item = new TestItem(1, "Test Item", 9.99, 5, "General");

        // Test initial values
        assertEquals(1, item.getId());
        assertEquals("Test Item", item.getName());
        assertEquals(9.99, item.getPrice());
        assertEquals(5, item.getQuantity());
        assertEquals("General", item.getCategory());

        // Update using setters
        item.setId(2);
        item.setName("Updated Item");
        item.setPrice(19.99);
        item.setQuantity(10);

        assertEquals(2, item.getId());
        assertEquals("Updated Item", item.getName());
        assertEquals(19.99, item.getPrice());
        assertEquals(10, item.getQuantity());
    }

}