package mz.org.fgh.idartlite.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import mz.org.fgh.idartlite.model.Patient;

import java.sql.SQLException;

public class PatientDaoImpl extends GenericDaoImpl<Patient, Integer> implements PatientDao{

    public PatientDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public PatientDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public PatientDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
