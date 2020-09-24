package mz.org.fgh.idartlite.service;

import android.app.Application;

import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class StockService extends BaseService {

    public StockService(Application application, User currUser) {
        super(application, currUser);
    }

    public void saveStock(Stock stock) throws SQLException {
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
}
