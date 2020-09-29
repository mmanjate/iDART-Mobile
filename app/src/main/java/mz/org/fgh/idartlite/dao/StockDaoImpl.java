package mz.org.fgh.idartlite.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import mz.org.fgh.idartlite.model.Stock;

import java.sql.SQLException;

public class StockDaoImpl extends GenericDaoImpl<Stock, Integer> implements StockDao {

    public StockDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public StockDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public StockDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
