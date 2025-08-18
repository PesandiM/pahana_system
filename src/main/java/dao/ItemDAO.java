package dao;

import java.util.List;

public interface ItemDAO <T> {
    void addItem(T item);
    T getItem(int id);
    List<T> getAllItems();
    void updateItem(T item);
    void deleteItem(int id);

    void saveToDatabase(T item);
    List<T> loadFromDatabase();
    void syncToDatabase();
}
