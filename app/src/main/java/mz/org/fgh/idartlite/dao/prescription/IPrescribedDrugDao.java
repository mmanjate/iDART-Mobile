package mz.org.fgh.idartlite.dao.prescription;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.PrescribedDrug;
import mz.org.fgh.idartlite.model.Prescription;

public interface IPrescribedDrugDao extends IGenericDao<PrescribedDrug, Integer> {

    public List<PrescribedDrug> getAllByPrescription(Prescription prescription) throws SQLException;

}
