package mz.org.fgh.idartlite.service;

import android.app.Application;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.User;

import java.sql.SQLException;
import java.util.List;

public class StockService extends BaseService {

    public StockService(Application application, User currUser) {
        super(application, currUser);
    }

    public void saveOrUpdateStock(Stock stock) throws SQLException {
        getDataBaseHelper().getStockDao().createOrUpdate(stock);
    }

    public void updateStock(Stock stock) throws SQLException {
        getDataBaseHelper().getStockDao().update(stock);
    }

    public void deleteStock(Stock stock) throws SQLException {
        getDataBaseHelper().getStockDao().delete(stock);
    }

    public List<Stock> getStockByClinic(Clinic clinic) throws SQLException {
        return getDataBaseHelper().getStockDao().queryBuilder().where().eq(Stock.COLUMN_CLINIC,clinic.getId()).query();
    }

    public List<Stock> getAllStocksByClinicAndDrug(Clinic clinic, Drug drug) throws SQLException {
        return getDataBaseHelper().getStockDao().queryBuilder().where().eq(Stock.COLUMN_CLINIC,clinic.getId()).and().eq(Stock.COLUMN_DRUG, drug.getId()).query();
    }
}
