package mz.org.fgh.idartlite.service.patient;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.patient.Patient;
import mz.org.fgh.idartlite.model.patient.PatientAttribute;

public interface IPatientAttributeService extends IBaseService<PatientAttribute> {
    List<PatientAttribute> getAllOfPatient(Patient patient) throws SQLException;

    PatientAttribute getByAttributeOfPatient(String attr, Patient patient) throws SQLException;
}
