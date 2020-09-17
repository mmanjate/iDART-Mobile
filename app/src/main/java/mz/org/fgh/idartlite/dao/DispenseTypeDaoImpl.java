package mz.org.fgh.idartlite.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import mz.org.fgh.idartlite.model.DispenseType;

public class DispenseTypeDaoImpl extends GenericDaoImpl<DispenseType, Integer> implements DispenseTypeDao {
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
