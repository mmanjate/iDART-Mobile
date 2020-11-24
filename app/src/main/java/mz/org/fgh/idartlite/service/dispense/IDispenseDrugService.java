package mz.org.fgh.idartlite.service.dispense;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Stock;


public interface IDispenseDrugService extends IBaseService<DispensedDrug> {

    public void createDispensedDrug(DispensedDrug dispenseDrug) throws SQLException;

    public List<DispensedDrug> findDispensedDrugByDispenseId(int id) throws SQLException;

    public List<Drug> findDrugsOnDispensedDrugsByDispense(Dispense dispense) throws SQLException;

    public boolean checkStockIsDispensedDrug(Stock stock) throws SQLException;

}
