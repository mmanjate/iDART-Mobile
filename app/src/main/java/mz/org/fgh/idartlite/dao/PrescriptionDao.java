package mz.org.fgh.idartlite.dao;

import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;

import java.sql.SQLException;
import java.util.List;

public interface PrescriptionDao extends GenericDao<Prescription, Integer>{

    public List<Prescription> getAllByPatient(Patient patient) throws SQLException;
}
