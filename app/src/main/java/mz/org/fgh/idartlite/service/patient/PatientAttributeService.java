package mz.org.fgh.idartlite.service.patient;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.dao.patient.IPatientAttributeDao;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.model.patient.Patient;
import mz.org.fgh.idartlite.model.patient.PatientAttribute;

public class PatientAttributeService extends BaseService<PatientAttribute> implements IPatientAttributeService {
    
    IPatientAttributeDao patientAttributeDao;
    
    public PatientAttributeService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public PatientAttributeService(Application application) {
        super(application);
    }

    @Override
    public void init(Application application, User currentUser) {
        super.init(application, currentUser);
        try {
            patientAttributeDao = getDataBaseHelper().getPatientAttributeDao();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void save(PatientAttribute record) throws SQLException {
        super.save(record);
        patientAttributeDao.create(record);
    }

    @Override
    public void update(PatientAttribute record) throws SQLException {
        super.update(record);
        patientAttributeDao.update(record);
    }

    @Override
    public List<PatientAttribute> getAllOfPatient(Patient patient) throws SQLException {
        return patientAttributeDao.queryForEq(PatientAttribute.COLUMN_PATIENT_ID, patient.getId());
    }

    @Override
    public PatientAttribute getByAttributeOfPatient(String attr, Patient patient) throws SQLException {
        return patientAttributeDao.getByAttributeOfPatient(attr, patient);
    }
}
