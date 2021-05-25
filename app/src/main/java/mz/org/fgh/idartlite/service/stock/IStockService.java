package mz.org.fgh.idartlite.service.stock;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Stock;


public interface IStockService extends IBaseService<Stock> {

    public void saveOrUpdateStock(Stock stock) throws SQLException ;

    public void deleteStock(Stock stock) throws SQLException ;

    public List<Stock> getStockByClinic(Clinic clinic, long offset, long limit) throws SQLException ;

    public List<Stock> getStockByOrderNumber(String orderNumber, Clinic clinic) throws SQLException ;

    public boolean checkStockExist(String orderNumber, Clinic clinic) throws SQLException ;

    public List<Stock> getStockByStatus(String status) throws SQLException ;

    public List<Stock> getAllStocksByClinicAndDrug(Clinic clinic, Drug drug) throws SQLException ;

    public List<Stock> getAllStocksByDrug(Drug drug) throws SQLException ;

    public void updateStock(Stock stock) throws SQLException ;

    public void saveOrUpdateViaRest(Stock stock) throws SQLException ;
}
