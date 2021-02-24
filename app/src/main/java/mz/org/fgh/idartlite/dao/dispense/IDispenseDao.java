package mz.org.fgh.idartlite.dao.dispense;

import android.app.Application;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IDispenseDao extends IGenericDao<Dispense, Integer> {

    List<Dispense> getAllByPrescription(Prescription prescription) throws SQLException;

    long countAllOfPrescription(Prescription prescription) throws SQLException;

    public List<Dispense> getAllOfPatient(Application application, Patient patient) throws SQLException;

    public Dispense getLastDispensePrescription(Prescription prescription)  throws SQLException;

    public List<Dispense> getDispensesBetweenStartDateAndEndDateWithLimit(Date startDate, Date endDate,long offset, long limit) throws SQLException;

    public List<Dispense> getDispensesBetweenStartDateAndEndDate(Date startDate, Date endDate) throws SQLException;

    public List<Dispense> getAllDispensesByStatus(String status) throws SQLException;

    public List<Dispense> getDispensesBetweenNextPickppDateStartDateAndEndDateWithLimit(Date startDate, Date endDate, long offset, long limit) throws SQLException;

    public List<Dispense> getDispensesBetweenNextPickppDateStartDateAndEndDateWithLimit(Application application,Date startDate, Date endDate, long offset, long limit) throws SQLException;

    public List<Dispense> getAbsentPatientsBetweenNextPickppDateStartDateAndEndDateWithLimit(Application application,Date startDate, Date endDate, long offset, long limit) throws SQLException;

}
