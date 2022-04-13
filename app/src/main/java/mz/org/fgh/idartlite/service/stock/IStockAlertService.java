package mz.org.fgh.idartlite.service.stock;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.StockReportData;

public interface IStockAlertService extends IBaseService<StockReportData> {
    void clearOldData() throws SQLException;

    List<StockReportData> getAll() throws SQLException;
}
