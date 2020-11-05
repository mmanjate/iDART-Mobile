package mz.org.fgh.idartlite.dao;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Stock;

public interface DispensedDrugDao extends GenericDao<DispensedDrug, Integer> {
    public boolean checkStockIsDispensedDrug(Stock stock) throws SQLException;

    public List<DispensedDrug> getDispensedDrugsByDispenses(List<Dispense> dispenses) throws SQLException;
}
