package mz.org.fgh.idartlite.dao.patient;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.patient.Patient;
import mz.org.fgh.idartlite.model.patient.PatientAttribute;

public class PatientAttributeDaoImpl extends GenericDaoImpl<PatientAttribute, Integer> implements IPatientAttributeDao {

    public PatientAttributeDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public PatientAttributeDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public PatientAttributeDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public PatientAttribute getByAttributeOfPatient(String attr, Patient patient) throws SQLException {
        return queryBuilder().where().eq(PatientAttribute.COLUMN_ATTRIBUTE, attr).and().eq(PatientAttribute.COLUMN_PATIENT_ID, patient.getId()).queryForFirst();
    }
}
