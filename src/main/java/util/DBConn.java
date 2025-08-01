package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
    private static DBConn instance;
    private static final String URL = "jdbc:mysql://localhost:3306/pahana_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public DBConn() {}

    public static synchronized DBConn getInstance() {
        if (instance == null) {
            instance = new DBConn();
        }
        return instance;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
