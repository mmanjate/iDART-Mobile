package mz.org.fgh.idartlite.service.patient;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;

import static mz.org.fgh.idartlite.model.Patient.COLUMN_UUID;


public interface IPatientService extends IBaseService {

    public List<Patient> searchPatientByParamAndClinic(String param, Clinic clinic, long offset, long limit) throws SQLException ;

    public List<Patient> getALLPatient() throws  SQLException ;

    public void  savePatient(Patient patient) throws SQLException ;

    public int countNewPatientsByPeriod(Date start, Date end) throws SQLException ;

    public Patient getPatient(String uuid) throws SQLException ;

    public boolean checkPatient(LinkedTreeMap<String, Object> patient) ;

    public void saveOnPatient(LinkedTreeMap<String, Object> patient) ;

}
