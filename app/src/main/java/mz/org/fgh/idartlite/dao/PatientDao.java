package mz.org.fgh.idartlite.dao;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Patient;

public interface PatientDao extends GenericDao<Patient, Integer>{

    public List<Patient> searchPatientByParamAndClinic(String param, Clinic clinic, long offset, long limit) throws SQLException;
}
