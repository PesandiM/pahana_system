package dao;

import dto.StockReportDTO;

import java.sql.SQLException;
import java.util.List;

public interface ItemReportDAO {
    List<StockReportDTO> getStockSummary() throws SQLException;
}
