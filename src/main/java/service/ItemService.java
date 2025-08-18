package service;

import dao.BookDAO;
import dao.ItemDAO;
import dao.StationeryDAO;
import dto.ItemDTO;
import factory.ItemFactory;
import model.Book;
import model.Item;
import model.Stationery;

import java.util.ArrayList;
import java.util.List;

public class ItemService {
    private final ItemDAO<Book> bookDAO;
    private final ItemDAO<Stationery> stationeryDAO;

    public ItemService(ItemDAO<Book> bookDAO, ItemDAO<Stationery> stationeryDAO) {
        this.bookDAO = bookDAO;
        this.stationeryDAO = stationeryDAO;
    }

    public void addItem(ItemDTO dto) {
        Item item = ItemFactory.createItem(
                dto.getType(),
                dto.getName(),
                dto.getPrice(),
                dto.getQuantity(),
                dto.getAuthor(),
                dto.getIsbn(),
                dto.getManufacturer()
        );

        if (item instanceof Book){
            Book book = (Book) item;
            bookDAO.addItem(book);
        } else if (item instanceof Stationery) {
            Stationery  stationery = (Stationery) item;
            stationeryDAO.addItem(stationery);
        }
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllItems();
    }

    public List<Stationery> getAllStationery() {
        return stationeryDAO.getAllItems();
    }

    public Book getBook(int id) {
        return bookDAO.getItem(id);
    }

    public Stationery getStationery(int id) {
        return stationeryDAO.getItem(id);
    }

    public void deleteItem(String type, int id) {
        if ("book".equalsIgnoreCase(type)) {
            bookDAO.deleteItem(id);
        } else if ("stationery".equalsIgnoreCase(type)) {
            stationeryDAO.deleteItem(id);
        } else {
            throw new IllegalArgumentException("Unknown item type for deletion: " + type);
        }
    }
}

