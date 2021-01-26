package mz.org.fgh.idartlite.dao.clinicInfo;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;

import static mz.org.fgh.idartlite.model.Dispense.COLUMN_SYNC_STATUS;

public class ClinicInfoDaoImpl extends GenericDaoImpl<ClinicInformation, Integer> implements IClinicInfoDao {



    public ClinicInfoDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public ClinicInfoDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public ClinicInfoDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }


    @Override
    public List<ClinicInformation> getAllByPatient(Patient patient) throws SQLException {
        return queryBuilder().orderBy(ClinicInformation.COLUMN_REGISTER_DATE,true).where().eq(ClinicInformation.COLUMN_PATIENT_ID, patient.getId()).query();
    }

}
