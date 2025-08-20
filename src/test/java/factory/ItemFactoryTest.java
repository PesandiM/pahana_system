package factory;

import model.Book;
import model.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemFactoryTest {
    @Test
    void testCreateBook() {
        Item item = ItemFactory.createItem(
                "book",
                "The Book",
                200.00,
                10,
                "Robert C. Martin",
                "9780132350884",
                null // manufacturer not needed for books
        );

        assertTrue(item instanceof Book);
        Book book = (Book) item;

        assertEquals("The Book", book.getName());
        assertEquals(200.00, book.getPrice());
        assertEquals(10, book.getQuantity());
        assertEquals("Robert C. Martin", book.getAuthor());
        assertEquals("9780132350884", book.getIsbn());
    }

    @Test
    void testUnknownTypeThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ItemFactory.createItem(
                    "toy",      // invalid type
                    "Lego",
                    50.0,
                    5,
                    null,
                    null,
                    null
            );
        });

        assertEquals("Unknown item type: toy", exception.getMessage());
    }

}