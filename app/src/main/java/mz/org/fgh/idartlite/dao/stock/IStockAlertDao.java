package mz.org.fgh.idartlite.dao.stock;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.StockReportData;

public interface IStockAlertDao extends IGenericDao<StockReportData, Integer> {


    List<StockReportData> getAllWithStock(Application application) throws SQLException;
}
