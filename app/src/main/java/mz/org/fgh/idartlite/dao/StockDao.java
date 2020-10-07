package mz.org.fgh.idartlite.dao;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Stock;

public interface StockDao extends GenericDao<Stock, Integer> {
    public List<Stock> getStockByClinic(Clinic clinic) throws SQLException;

    public List<Stock> getStockByOrderNumber(String orderNumber, Clinic clinic) throws SQLException;

    public List<Stock> getAllStocksByClinicAndDrug(Clinic clinic, Drug drug) throws SQLException;

    public void updateStock(DispensedDrug dispensedDrug) throws SQLException;
}
