package mz.org.fgh.idartlite.service.stock;

import android.app.Application;

import java.sql.SQLException;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.StockAjustment;
import mz.org.fgh.idartlite.model.User;

public class StockAjustementService extends BaseService<StockAjustment> implements IStockAjustmentService {

    public StockAjustementService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public StockAjustementService(Application application) {
        super(application);
    }

    @Override
    public void save(StockAjustment record) throws SQLException {
        super.save(record);
        getDataBaseHelper().getStockAjustmentDao().create(record);
    }

    @Override
    public void update(StockAjustment relatedRecord) throws SQLException {
        super.update(relatedRecord);
        getDataBaseHelper().getStockAjustmentDao().update(relatedRecord);
    }

    @Override
    public void deleteRecord(StockAjustment selectedRecord) throws SQLException {
        super.deleteRecord(selectedRecord);

        getDataBaseHelper().getStockAjustmentDao().delete(selectedRecord);
    }
}
