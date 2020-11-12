package mz.org.fgh.idartlite.dao;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.util.DateUtilities;

import java.sql.SQLException;
import java.util.List;

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

    @Override
    public List<Prescription> getAllByPatient(Patient patient) throws SQLException {
        return queryBuilder().where().eq(Episode.COLUMN_PATIENT_ID, patient.getId()).query();
    }

    @Override
    public Prescription getLastPatientPrescription(Patient patient) throws SQLException {

        QueryBuilder<Prescription, Integer> prescriptionQb = queryBuilder();

        prescriptionQb.where().eq(Prescription.COLUMN_PATIENT_ID, patient.getId());

        return prescriptionQb.orderBy("id", false).limit(1L).query().get(0);

    }

    @Override
    public void closePrescription(Prescription prescription) throws SQLException {
        prescription.setExpiryDate(DateUtilities.getCurrentDate());
        update(prescription);
    }
}
