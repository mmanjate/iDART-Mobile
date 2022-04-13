package mz.org.fgh.idartlite.dao.stock;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.StockReportData;

public class StockAlertDaoImpl extends GenericDaoImpl<StockReportData, Integer> implements IStockAlertDao{
    public StockAlertDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public StockAlertDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public StockAlertDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
