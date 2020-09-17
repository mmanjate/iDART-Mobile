package mz.org.fgh.idartlite.dao;

import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Prescription;

import java.sql.SQLException;
import java.util.List;

public interface DispenseDao extends GenericDao<Dispense, Integer> {

    List<Dispense> getAllByPrescription(Prescription prescription) throws SQLException;
}
