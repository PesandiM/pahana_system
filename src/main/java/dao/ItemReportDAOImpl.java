package dao;

import dto.StockReportDTO;
import util.DBConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemReportDAOImpl implements ItemReportDAO {

    @Override
    public List<StockReportDTO> getStockSummary() throws SQLException {
        String sql = "SELECT item_id, item_name, item_type, quantity, price " +
                "FROM items ORDER BY item_id DESC";

        List<StockReportDTO> list = new ArrayList<>();

        try (Connection conn = DBConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                StockReportDTO dto = new StockReportDTO();
                dto.setItemId(rs.getInt("item_id"));
                dto.setItemName(rs.getString("item_name"));
                dto.setItemType(rs.getString("item_type"));
                dto.setQuantity(rs.getInt("quantity"));
                dto.setPrice(rs.getDouble("price"));
                list.add(dto);
            }
        }
        return list;
    }
}

