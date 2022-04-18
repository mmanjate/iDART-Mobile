package mz.org.fgh.idartlite.service.stock;

import android.app.Application;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.StockReportData;
import mz.org.fgh.idartlite.model.User;

public class StockAlertService extends BaseService<StockReportData> implements IStockAlertService{
    public StockAlertService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public StockAlertService(Application application) {
        super(application);
    }

    @Override
    public void save(StockReportData record) throws SQLException {
        super.save(record);
        getDataBaseHelper().getStockAlertDao().create(record);
    }

    @Override
    public void delete(StockReportData record) throws SQLException {
        super.delete(record);
        getDataBaseHelper().getStockAlertDao().delete(record);
    }

    public void clearOldData() throws SQLException {
        List<StockReportData> oldReport = new ArrayList<>();
        oldReport = getDataBaseHelper().getStockAlertDao().queryForAll();

        getDataBaseHelper().getStockAlertDao().delete(oldReport);
    }

    @Override
    public List<StockReportData> getAll() throws SQLException {
        return getDataBaseHelper().getStockAlertDao().queryForAll();
    }
}
