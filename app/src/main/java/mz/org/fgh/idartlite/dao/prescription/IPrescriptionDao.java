package mz.org.fgh.idartlite.dao.prescription;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;

import java.sql.SQLException;
import java.util.List;

public interface IPrescriptionDao extends IGenericDao<Prescription, Integer> {

    public List<Prescription> getAllByPatient(Patient patient) throws SQLException;

    public Prescription getLastPatientPrescription(Patient patient) throws SQLException;

    public void closePrescription(Prescription prescription) throws SQLException;
}
