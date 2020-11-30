package mz.org.fgh.idartlite.service.stock;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.User;

public class StockService extends BaseService<Stock> implements IStockService {

    public StockService(Application application, User currUser) {
        super(application, currUser);
    }

    public StockService(Application application) {
        super(application);
    }

    @Override
    public void save(Stock record) throws SQLException {
        super.save(record);
        getDataBaseHelper().getStockDao().create(record);
    }

    @Override
    public void update(Stock record) throws SQLException {
        super.update(record);
        getDataBaseHelper().getStockDao().update(record);
    }

    public void saveOrUpdateStock(Stock stock) throws SQLException {
        getDataBaseHelper().getStockDao().createOrUpdate(stock);
    }

    public void deleteStock(Stock stock) throws SQLException {
        getDataBaseHelper().getStockDao().delete(stock);
    }

    public List<Stock> getStockByClinic(Clinic clinic, long offset, long limit) throws SQLException {
        return getDataBaseHelper().getStockDao().getStockByClinic(clinic, offset, limit);
    }

    public List<Stock> getStockByOrderNumber(String orderNumber, Clinic clinic) throws SQLException {
        return getDataBaseHelper().getStockDao().getStockByOrderNumber(orderNumber, clinic);
    }


    public boolean checkStockExist(String orderNumber, Clinic clinic) throws SQLException {
        return getDataBaseHelper().getStockDao().checkStockExist(orderNumber, clinic);
    }

    public List<Stock> getStockByStatus(String status) throws SQLException {
        return getDataBaseHelper().getStockDao().queryBuilder().where().eq(Stock.COLUMN_SYNC_STATUS,status).query();
    }

    public List<Stock> getAllStocksByClinicAndDrug(Clinic clinic, Drug drug) throws SQLException {
        return getDataBaseHelper().getStockDao().getAllStocksByClinicAndDrug(clinic, drug);
    }

    public List<Stock> getAllStocksByDrug(Drug drug) throws SQLException {
        return getDataBaseHelper().getStockDao().getAllStocksByDrug(drug);
    }

    public List<Stock> getAll(Drug drug) throws SQLException {
        return getDataBaseHelper().getStockDao().getAll(drug);
    }

    public void updateStock(Stock stock) throws SQLException {
        getDataBaseHelper().getStockDao().update(stock);
    }

}
