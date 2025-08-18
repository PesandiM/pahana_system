package dao;

import model.Book;
import util.DBConn;

import java.sql.*;
import java.util.*;

public class BookDAO implements ItemDAO<Book> {
    private final Map<Integer, Book> cache = new HashMap<>();
    private final Connection connection;

    public BookDAO(Connection connection) {
        this.connection = connection;
        loadFromDatabase();
        System.out.println("Books loaded in cache: " + cache.size());
    }

    @Override
    public void addItem(Book book) {
        saveToDatabase(book); // after this, book.id will be set
        cache.put(book.getId(), book);
    }

    @Override
    public void saveToDatabase(Book book) {
        String sql = "INSERT INTO items (item_type, item_name, price, quantity, author, isbn) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConn.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, "book");
            stmt.setString(2, book.getName());
            stmt.setDouble(3, book.getPrice());
            stmt.setInt(4, book.getQuantity());
            stmt.setString(5, book.getAuthor());
            stmt.setString(6, book.getIsbn());

            stmt.executeUpdate();

            // Retrieve generated ID
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    book.setId(keys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Book getItem(int id) {
        return cache.get(id); // Fetch from cache
    }

    @Override
    public List<Book> getAllItems() {
        return new ArrayList<>(cache.values()); // Return all cached books
    }

    @Override
    public void updateItem(Book book) {
        // Update cache
        cache.put(book.getId(), book);

        // Update DB
        String sql = "UPDATE items SET item_name = ?, price = ?, quantity = ?, author = ?, isbn = ? WHERE item_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, book.getName());
            stmt.setDouble(2, book.getPrice());
            stmt.setInt(3, book.getQuantity());
            stmt.setString(4, book.getAuthor());
            stmt.setString(5, book.getIsbn());
            stmt.setInt(6, book.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Database update failed", e);
        }
    }

    @Override
    public void deleteItem(int id) {
        cache.remove(id); // Remove from cache

        // Remove from DB
        String sql = "DELETE FROM items WHERE item_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Database delete failed", e);
        }
    }

    @Override
    public List<Book> loadFromDatabase() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE item_type = 'book'";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("item_id"),
                        rs.getString("item_name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("author"),
                        rs.getString("isbn")
                );
                books.add(book);
                cache.put(book.getId(), book); // Populate cache
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database load failed", e);
        }
        return books;
    }

    @Override
    public void syncToDatabase() {

    }
}
