package mz.org.fgh.idartlite.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import mz.org.fgh.idartlite.model.Prescription;

import java.sql.SQLException;

public class PrescriptionDaoImpl extends GenericDaoImpl<Prescription, Integer> implements PrescriptionDao {

    public PrescriptionDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public PrescriptionDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public PrescriptionDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
