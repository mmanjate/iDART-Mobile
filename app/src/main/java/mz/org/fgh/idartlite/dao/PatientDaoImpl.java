package mz.org.fgh.idartlite.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

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

    public List<Patient> searchPatientByParamAndClinic(String param, Clinic clinic) throws SQLException {
        if (!checkIsEmpty(param,clinic)){
         return   queryBuilder().where().like(Patient.COLUMN_NID, "%" + param + "%").or().like(Patient.COLUMN_FIRST_NAME, "%" + param + "%").and().eq(Patient.COLUMN_CLINIC_ID, clinic.getId()).query();
        }
        else {
         return   Collections.emptyList();
        }
    }

    public boolean checkIsEmpty(String param, Clinic clinic) throws SQLException {
       return  queryBuilder().where().like(Patient.COLUMN_NID, "%" + param + "%").or().like(Patient.COLUMN_FIRST_NAME, "%" + param + "%").and().eq(Patient.COLUMN_CLINIC_ID, clinic.getId()).query().isEmpty();
    }
}
