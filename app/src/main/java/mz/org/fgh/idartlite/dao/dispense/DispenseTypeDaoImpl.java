package mz.org.fgh.idartlite.dao.dispense;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.DispenseType;

public class DispenseTypeDaoImpl extends GenericDaoImpl<DispenseType, Integer> implements IDispenseTypeDao {
    public DispenseTypeDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public DispenseTypeDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public DispenseTypeDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
