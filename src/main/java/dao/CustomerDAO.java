package dao;

import dto.CustomerDTO;
import model.Customer;
import util.DBConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public static boolean saveCustomer(Customer customer) {
        boolean result = false;

        try {
            Connection conn = DBConn.getConnection();

            try {
                String sql = "INSERT INTO customers (account_no, name, email, address, telephone) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, customer.getAccountNo());
                stmt.setString(2, customer.getName());
                stmt.setString(3, customer.getEmail());
                stmt.setString(4, customer.getAddress());
                stmt.setString(5, customer.getTelephone());
                result = stmt.executeUpdate() > 0;
            } catch (Throwable var6) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable var5) {
                        var6.addSuppressed(var5);
                    }
                }

                throw var6;
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String getNextAccountNo() {
        String nextAccountNo = "PEDU001";

        try {
            Connection conn = DBConn.getConnection();

            try {
                String sql = "SELECT MAX(account_no) FROM customers";
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                if (rs.next() && rs.getString(1) != null) {
                    String lastAccountNo = rs.getString(1);
                    int num = Integer.parseInt(lastAccountNo.substring(4));
                    nextAccountNo = "PEDU" + String.format("%03d", num + 1);
                }
            } catch (Throwable var8) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable var7) {
                        var8.addSuppressed(var7);
                    }
                }

                throw var8;
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nextAccountNo;
    }

    public static List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList();
        String sql = "SELECT * FROM customers";

        try {
            Connection conn = DBConn.getConnection();

            try {
                PreparedStatement stmt = conn.prepareStatement(sql);

                try {
                    ResultSet rs = stmt.executeQuery();

                    try {
                        while(rs.next()) {
                            Customer c = new Customer();
                            c.setCustomerId(rs.getInt("customer_id"));
                            c.setAccountNo(rs.getString("account_no"));
                            c.setName(rs.getString("name"));
                            c.setEmail(rs.getString("email"));
                            c.setAddress(rs.getString("address"));
                            c.setTelephone(rs.getString("telephone"));
                            c.setActive(rs.getBoolean("is_active"));
                            c.setTotalPurchases(rs.getInt("total_purchases"));
                            customers.add(c);
                        }
                    } catch (Throwable var10) {
                        if (rs != null) {
                            try {
                                rs.close();
                            } catch (Throwable var9) {
                                var10.addSuppressed(var9);
                            }
                        }

                        throw var10;
                    }

                    if (rs != null) {
                        rs.close();
                    }
                } catch (Throwable var11) {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (Throwable var8) {
                            var11.addSuppressed(var8);
                        }
                    }

                    throw var11;
                }

                if (stmt != null) {
                    stmt.close();
                }
            } catch (Throwable var12) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable var7) {
                        var12.addSuppressed(var7);
                    }
                }

                throw var12;
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public static Customer getCustomerByAccountNo(String accountNo) {
        Customer customer = null;
        String sql = "SELECT * FROM customers WHERE account_no = ?";

        try {
            Connection conn = DBConn.getConnection();

            try {
                PreparedStatement stmt = conn.prepareStatement(sql);

                try {
                    stmt.setString(1, accountNo);
                    ResultSet rs = stmt.executeQuery();

                    try {
                        if (rs.next()) {
                            customer = new Customer();
                            customer.setCustomerId(rs.getInt("customer_id"));
                            customer.setAccountNo(rs.getString("account_no"));
                            customer.setName(rs.getString("name"));
                            customer.setEmail(rs.getString("email"));
                            customer.setAddress(rs.getString("address"));
                            customer.setTelephone(rs.getString("telephone"));
                            customer.setActive(rs.getBoolean("is_active"));
                            customer.setTotalPurchases(rs.getInt("total_purchases"));
                        }
                    } catch (Throwable var11) {
                        if (rs != null) {
                            try {
                                rs.close();
                            } catch (Throwable var10) {
                                var11.addSuppressed(var10);
                            }
                        }

                        throw var11;
                    }

                    if (rs != null) {
                        rs.close();
                    }
                } catch (Throwable var12) {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (Throwable var9) {
                            var12.addSuppressed(var9);
                        }
                    }

                    throw var12;
                }

                if (stmt != null) {
                    stmt.close();
                }
            } catch (Throwable var13) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable var8) {
                        var13.addSuppressed(var8);
                    }
                }

                throw var13;
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    public static boolean updateCustomer(CustomerDTO customerDTO) {
        boolean result = false;
        String sql = "UPDATE customers SET email = ?, address = ?, telephone = ? WHERE account_no = ?";

        try {
            Connection conn = DBConn.getConnection();

            try {
                PreparedStatement stmt = conn.prepareStatement(sql);

                try {
                    stmt.setString(1, customerDTO.getEmail());
                    stmt.setString(2, customerDTO.getAddress());
                    stmt.setString(3, customerDTO.getTelephone());
                    stmt.setString(4, customerDTO.getAccountNo());
                    result = stmt.executeUpdate() > 0;
                } catch (Throwable var9) {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (Throwable var8) {
                            var9.addSuppressed(var8);
                        }
                    }

                    throw var9;
                }

                if (stmt != null) {
                    stmt.close();
                }
            } catch (Throwable var10) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable var7) {
                        var10.addSuppressed(var7);
                    }
                }

                throw var10;
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean deleteCustomerByAccountNo(String accountNo) {
        boolean result = false;
        String sql = "DELETE FROM customers WHERE account_no = ?";

        try {
            Connection conn = DBConn.getConnection();

            try {
                PreparedStatement stmt = conn.prepareStatement(sql);

                try {
                    stmt.setString(1, accountNo);
                    result = stmt.executeUpdate() > 0;
                } catch (Throwable var9) {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (Throwable var8) {
                            var9.addSuppressed(var8);
                        }
                    }

                    throw var9;
                }

                if (stmt != null) {
                    stmt.close();
                }
            } catch (Throwable var10) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable var7) {
                        var10.addSuppressed(var7);
                    }
                }

                throw var10;
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static Customer getCustomerById(int customerId) throws SQLException {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer customer = new Customer();
                    customer.setCustomerId(rs.getInt("customer_id"));
                    customer.setName(rs.getString("name"));
                    customer.setEmail(rs.getString("email"));
                    customer.setTelephone(rs.getString("telephone"));
                    customer.setAddress(rs.getString("address"));
                    return customer;
                } else {
                    return null;
                }
            }
        }
    }
}
