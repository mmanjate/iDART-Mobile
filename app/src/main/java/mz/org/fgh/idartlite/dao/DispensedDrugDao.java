package mz.org.fgh.idartlite.dao;

import java.sql.SQLException;

import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Stock;

public interface DispensedDrugDao extends GenericDao<DispensedDrug, Integer> {
    public boolean checkStockIsDispensedDrug(Stock stock) throws SQLException;
}
