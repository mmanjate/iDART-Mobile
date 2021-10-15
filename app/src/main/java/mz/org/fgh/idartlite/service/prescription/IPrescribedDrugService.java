package mz.org.fgh.idartlite.service.prescription;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.PrescribedDrug;
import mz.org.fgh.idartlite.model.Prescription;


public interface IPrescribedDrugService extends IBaseService<PrescribedDrug> {

    public void createPrescribedDrug(PrescribedDrug prescribedDrug) throws SQLException ;

    public void savePrescribedDrug(Prescription prescription, String drugs) ;

    public List<PrescribedDrug> getAllByPrescription(Prescription prescription) throws SQLException;

    public void deletePrescribedDrugs(List<PrescribedDrug> prescribedDrugs)throws SQLException;

}
