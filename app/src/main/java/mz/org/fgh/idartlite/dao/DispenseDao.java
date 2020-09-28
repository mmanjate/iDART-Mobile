package mz.org.fgh.idartlite.dao;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Prescription;

public interface DispenseDao extends GenericDao<Dispense, Integer> {

    List<Dispense> getAllByPrescription(Prescription prescription) throws SQLException;

    long countAllOfPrescription(Prescription prescription) throws SQLException;
}
