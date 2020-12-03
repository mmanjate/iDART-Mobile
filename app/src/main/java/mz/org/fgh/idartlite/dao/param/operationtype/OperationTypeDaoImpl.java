package mz.org.fgh.idartlite.dao.param.operationtype;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.OperationType;

public class OperationTypeDaoImpl extends GenericDaoImpl<OperationType, Integer> implements IOperationTypeDao {
    public OperationTypeDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public OperationTypeDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public OperationTypeDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
