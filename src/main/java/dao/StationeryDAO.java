package dao;

import model.Stationery;
import util.DBConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.*;

public class StationeryDAO implements ItemDAO<Stationery> {
    private final Map<Integer, Stationery> cache = new HashMap<>();
    private final Connection connection;

    public StationeryDAO(Connection connection) {
        this.connection = connection;
        loadFromDatabase(); // Load existing stationery into cache at startup
    }

    @Override
    public void addItem(Stationery stationery) {
        saveToDatabase(stationery); // after this, stationery.id will be set
        cache.put(stationery.getId(), stationery);
    }

    @Override
    public void saveToDatabase(Stationery stationery) {
        String sql = "INSERT INTO items (item_type, item_name, price, quantity, manufacturer) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConn.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, "stationery");
            stmt.setString(2, stationery.getName());
            stmt.setDouble(3, stationery.getPrice());
            stmt.setInt(4, stationery.getQuantity());
            stmt.setString(5, stationery.getManufacturer());

            stmt.executeUpdate();

            // Retrieve generated ID
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    stationery.setId(keys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Stationery getItem(int id) {
        return cache.get(id); // Fetch from cache
    }

    @Override
    public List<Stationery> getAllItems() {
        return new ArrayList<>(cache.values()); // Return all cached stationery
    }

    @Override
    public void updateItem(Stationery stationery) {
        // Update cache
        cache.put(stationery.getId(), stationery);

        // Update DB
        String sql = "UPDATE items SET item_name = ?, price = ?, quantity = ?, manufacturer = ? WHERE item_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, stationery.getName());
            stmt.setDouble(2, stationery.getPrice());
            stmt.setInt(3, stationery.getQuantity());
            stmt.setString(4, stationery.getManufacturer());
            stmt.setInt(5, stationery.getId());
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
    public List<Stationery> loadFromDatabase() {
        List<Stationery> stationeries = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE item_type = 'stationery'";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Stationery stationery = new Stationery(
                        rs.getInt("item_id"),
                        rs.getString("item_name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("manufacturer")
                );
                stationeries.add(stationery); // Use add() instead of List.of()
                cache.put(stationery.getId(), stationery); // Populate cache
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database load failed", e);
        }
        return stationeries;
    }

    @Override
    public void syncToDatabase() {

    }
}
