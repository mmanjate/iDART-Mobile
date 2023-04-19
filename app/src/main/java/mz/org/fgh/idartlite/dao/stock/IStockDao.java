package mz.org.fgh.idartlite.dao.stock;

import android.app.Application;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Stock;

public interface IStockDao extends IGenericDao<Stock, Integer> {
    public List<Stock> getAllOfClinic(Application application, Clinic clinic, long offset, long limit) throws SQLException;

    public List<Stock> getStockByOrderNumber(String orderNumber, Clinic clinic) throws SQLException;

    public boolean checkStockExist (String orderNumber, Clinic clinic) throws SQLException;

    public List<Stock> getAllStocksByClinicAndDrug(Clinic clinic, Drug drug) throws SQLException;

    public List<Stock> getAllStocksByDrug( Drug drug) throws SQLException;

    List<Stock> getAll(Drug drug) throws SQLException;

    Stock getByBatchNumber(Stock stock) throws SQLException;

    public List<Stock> getStockListByDates(Drug drug ,Date startDate, Date endDate) throws SQLException;
}
