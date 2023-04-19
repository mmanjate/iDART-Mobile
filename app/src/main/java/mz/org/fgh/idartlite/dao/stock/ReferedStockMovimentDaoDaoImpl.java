package mz.org.fgh.idartlite.dao.stock;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.ReferedStockMoviment;

public class ReferedStockMovimentDaoDaoImpl extends GenericDaoImpl<ReferedStockMoviment, Integer> implements IReferedStockMovimentDao {

    public ReferedStockMovimentDaoDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public ReferedStockMovimentDaoDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public ReferedStockMovimentDaoDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public List<ReferedStockMoviment> searchRecords(long offset, long limit) throws SQLException {
        QueryBuilder<ReferedStockMoviment, Integer> queryBuilder = queryBuilder();
        if (offset > 0 && limit > 0) queryBuilder.offset(offset).limit(limit);
        return queryBuilder.orderBy(ReferedStockMoviment.COLUMN_ORDER_NUMBER, true).groupBy(ReferedStockMoviment.COLUMN_ORDER_NUMBER).query();
    }
}
