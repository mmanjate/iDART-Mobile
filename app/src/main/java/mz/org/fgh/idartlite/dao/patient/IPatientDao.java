package mz.org.fgh.idartlite.dao.patient;

import android.app.Application;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.patient.Patient;

public interface IPatientDao extends IGenericDao<Patient, Integer> {

    public List<Patient> searchPatientByParamAndClinic(String param, Clinic clinic, long offset, long limit) throws SQLException;

    public int countNewPatientsByPeriod(Date start, Date end, String sanitaryUnit, Application application) throws SQLException;

    public Patient getPatientByUuid(String uuid) throws SQLException ;

    public Patient checkExistsPatientWithNID(String nid) throws SQLException ;

    public List<Patient> searchPatientByNidOrNameOrSurname(String nid,String name,String surname, long offset, long limit) throws SQLException;

    public List<Patient> getAllPatientsBetweenStartDateAndEndDate(Application application,Date start, Date end, long offset, long limit) throws SQLException;

    public List<Patient> get(Application application, long offset, long limit) throws SQLException;

}
