package mz.org.fgh.idartlite.service;

import android.app.Application;
import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.User;

import java.sql.SQLException;

public class StockService extends BaseService {

    public StockService(Application application, User currUser) {
        super(application, currUser);
    }

    public void createStock(Stock stock) throws SQLException {
        getDataBaseHelper().getGenericDao(stock).saveGenericObjectByClass(stock);
    }

    public void udpateStock(Stock stock) throws SQLException {
        getDataBaseHelper().getGenericDao(stock).updateGenericObjectByClass(stock);
    }

    public void deleteStock(Stock stock) throws SQLException {
        getDataBaseHelper().getGenericDao(stock).deleteGenericObjectByClass(stock);
    }

}
