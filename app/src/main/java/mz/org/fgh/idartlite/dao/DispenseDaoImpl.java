package mz.org.fgh.idartlite.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import mz.org.fgh.idartlite.model.*;

import java.sql.SQLException;
import java.util.List;

public class DispenseDaoImpl extends GenericDaoImpl<Dispense, Integer> implements DispenseDao {

    public DispenseDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public DispenseDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public DispenseDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public List<Dispense> getAllByPrescription(Prescription prescription) throws SQLException {
        return queryBuilder().where().eq(Dispense.COLUMN_PRESCRIPTION, prescription.getId()).query();
    }
}
