package mz.org.fgh.idartlite.service.stock;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.base.service.ServiceProvider;
import mz.org.fgh.idartlite.model.DestroyedDrug;
import mz.org.fgh.idartlite.model.User;

public class DestroyedStockDrugService extends BaseService<DestroyedDrug> implements IDestroyedStockDrug {


    public DestroyedStockDrugService(Application application, User currentUser) {
        super(application, currentUser);

    }

    public DestroyedStockDrugService(Application application) {
        super(application);
    }

    @Override
    public void save(DestroyedDrug record) throws SQLException {
        super.save(record);

        getDataBaseHelper().getDestroyedStockDrugDao().create(record);

        record.getStock().setStockMoviment(record.getStock().getStockMoviment() - record.getQtyToModify());

        ((StockService) ServiceProvider.getInstance(getApplication()).get(StockService.class)).updateStock(record.getStock());
    }

    @Override
    public void update(DestroyedDrug relatedRecord) throws SQLException {
        super.update(relatedRecord);

        getDataBaseHelper().getDestroyedStockDrugDao().update(relatedRecord);
    }

    @Override
    public void saveAll(List<DestroyedDrug> stocksToDestroy) throws SQLException {

        for (DestroyedDrug destroyedDrug : stocksToDestroy){
            save(destroyedDrug);
        }
    }

    public List<DestroyedDrug> getRecords(long offset, long limit) throws SQLException {
        return getDataBaseHelper().getDestroyedStockDrugDao().searchRecords(offset, limit);
    }


    @Override
    public void deleteRecord(DestroyedDrug selectedRecord) throws SQLException {
        super.deleteRecord(selectedRecord);

        getDataBaseHelper().getDestroyedStockDrugDao().delete(selectedRecord);

        doAfterDelete(selectedRecord);
    }

    private void doAfterDelete(DestroyedDrug selectedRecord) {
        /* TO-DO repor stock */
    }
}
