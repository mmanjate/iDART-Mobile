package mz.org.fgh.idartlite.service.drug;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.model.inventory.Iventory;


public interface IDrugService extends IBaseService<Drug> {

    public void saveDrug(Drug drug) throws SQLException;

    public List<Drug> getAll() throws SQLException;

    public List<Drug> getAllByTherapeuticRegimen(TherapeuticRegimen therapeuticRegimen) throws SQLException;

    public Drug getDrugByFNMCode(String code) throws SQLException;

    public Drug getDrugByDescription(String description) throws SQLException ;

    public Drug getDrugByRestID(int restId) throws SQLException;

    public boolean checkDrug(Object drug) ;

    public void saveOnDrug(Object drug);

    public List<Drug> getDrugsWithoutRectParanthesis(List<Drug> drugs) throws SQLException;

    List<Drug> getAllDestroyedDrugs() throws SQLException;


    List<Drug> getAllOnInventory(Iventory iventory) throws SQLException;
}
