package mz.org.fgh.idartlite.dao;

import android.app.Application;

import com.j256.ormlite.stmt.QueryBuilder;

import mz.org.fgh.idartlite.common.ValorSimples;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.TherapeuticLine;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface DispenseDao extends GenericDao<Dispense, Integer> {

    List<Dispense> getAllByPrescription(Prescription prescription) throws SQLException;

    long countAllOfPrescription(Prescription prescription) throws SQLException;

    public List<Dispense> getAllOfPatient(Application application, Patient patient) throws SQLException;

    public Dispense getLastDispensePrescription(Prescription prescription)  throws SQLException;

    public List<Dispense> getDispensesBetweenStartDateAndEndDateWithLimit(Date startDate, Date endDate,long offset, long limit) throws SQLException;

    public List<Dispense> getDispensesBetweenStartDateAndEndDate(Date startDate, Date endDate) throws SQLException;
}
