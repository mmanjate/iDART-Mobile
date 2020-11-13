package mz.org.fgh.idartlite.service.dispense;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Stock;


public interface IDispenseDrugService extends IBaseService {

    public void createDispensedDrug(DispensedDrug dispenseDrug) throws SQLException;

    public List<DispensedDrug> findDispensedDrugByDispenseId(int id) throws SQLException;

    public boolean checkStockIsDispensedDrug(Stock stock) throws SQLException;

}
