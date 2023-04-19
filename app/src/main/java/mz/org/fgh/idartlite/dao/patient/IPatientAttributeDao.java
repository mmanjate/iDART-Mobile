package mz.org.fgh.idartlite.dao.patient;

import java.sql.SQLException;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.patient.Patient;
import mz.org.fgh.idartlite.model.patient.PatientAttribute;

public interface IPatientAttributeDao extends IGenericDao<PatientAttribute, Integer> {
    PatientAttribute getByAttributeOfPatient(String attr, Patient patient) throws SQLException;
}
