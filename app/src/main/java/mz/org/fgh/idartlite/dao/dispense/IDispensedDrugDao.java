package mz.org.fgh.idartlite.dao.dispense;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Stock;

public interface IDispensedDrugDao extends IGenericDao<DispensedDrug, Integer> {
    public boolean checkStockIsDispensedDrug(Stock stock) throws SQLException;

    public List<DispensedDrug> getDispensedDrugsByDispenses(List<Dispense> dispenses) throws SQLException;

    public List<DispensedDrug> findDispensedDrugByDispenseId(int id) throws SQLException;

    public List<DispensedDrug> getDispensedDrugsByStockAndStock(Drug drug, Stock stock) throws SQLException;

}
