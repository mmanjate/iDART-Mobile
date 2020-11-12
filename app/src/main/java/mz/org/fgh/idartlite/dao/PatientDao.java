package mz.org.fgh.idartlite.dao;

import android.app.Application;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Patient;

public interface PatientDao extends GenericDao<Patient, Integer>{

    public List<Patient> searchPatientByParamAndClinic(String param, Clinic clinic, long offset, long limit) throws SQLException;

    public int countNewPatientsByPeriod(Date start, Date end, Application application) throws SQLException;


}
