package mz.org.fgh.idartlite.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.DestroyedDrug;

public class DestroyedDrugDaoImpl extends GenericDaoImpl<DestroyedDrug, Integer> implements DestroyedDrugDao{

    public DestroyedDrugDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public DestroyedDrugDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public DestroyedDrugDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
