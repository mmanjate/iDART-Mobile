package mz.org.fgh.idartlite.dao.stock;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.StockAjustment;

public class StockAjustmentDaoImpl extends GenericDaoImpl<StockAjustment, Integer> implements IStockAjustmentDao {

    public StockAjustmentDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public StockAjustmentDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public StockAjustmentDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
