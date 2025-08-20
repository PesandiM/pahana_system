package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    @Test
    void testItemThroughBookSubclass() {
        Book book = new Book(1, "Clean Code", 45.99, 10, "Robert C. Martin", "9780132350884");

        assertEquals(1, book.getId());
        assertEquals("Clean Code", book.getName());
        assertEquals(45.99, book.getPrice());
        assertEquals(10, book.getQuantity());
        assertEquals("book", book.getCategory());
    }
}