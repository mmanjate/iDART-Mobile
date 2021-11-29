package mz.org.fgh.idartlite.dao.prescription;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IPrescriptionDao extends IGenericDao<Prescription, Integer> {

    public List<Prescription> getAllByPatient(Patient patient) throws SQLException;

    public Prescription getLastPatientPrescription(Patient patient) throws SQLException;

    public Prescription getLastClosedPrescriptionByPatient(Patient patient) throws SQLException;

    public void closePrescription(Prescription prescription) throws SQLException;

    public boolean checkIfPatientHasPrescriptions(Patient patient) throws SQLException ;

    public List<Prescription> getAllPrescriptionToRemoveByDate(Date dateToRemove)throws SQLException;

    public List<Prescription> getAllPrescriptionToRemoveByDateAndPatient(Patient patient,Date dateToRemove) throws SQLException;
}
