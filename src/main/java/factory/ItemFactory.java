package factory;

import dto.ItemDTO;
import model.Book;
import model.Item;
import model.Stationery;

public class ItemFactory {

    public static Item createItem(String type, String name, double price, int quantity, String author, String isbn, String manufacturer) {
        if ("book".equalsIgnoreCase(type)) {
            // Correct order: (id, name, price, quantity, author, isbn)
            return new Book(0, name, price, quantity, author, isbn);
        } else if ("stationery".equalsIgnoreCase(type)) {
            // Correct order: (id, name, price, quantity, manufacturer)
            return new Stationery(0, name, price, quantity, manufacturer);
        }
        throw new IllegalArgumentException("Unknown item type: " + type);
    }
}

