package mz.org.fgh.idartlite.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import mz.org.fgh.idartlite.model.PharmacyType;

import java.sql.SQLException;

public class PharmacyTypeDaoImpl extends GenericDaoImpl<PharmacyType, Integer> implements PharmacyTypeDao{

    public PharmacyTypeDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public PharmacyTypeDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public PharmacyTypeDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
