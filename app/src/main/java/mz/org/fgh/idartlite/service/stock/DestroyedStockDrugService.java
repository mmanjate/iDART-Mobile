package mz.org.fgh.idartlite.service.stock;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.base.service.ServiceProvider;
import mz.org.fgh.idartlite.dao.stock.IDestroyedDrugDao;
import mz.org.fgh.idartlite.model.DestroyedDrug;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.service.drug.DrugService;
import mz.org.fgh.idartlite.service.drug.IDrugService;

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

        getDestroyedStockDrugDao().create(record);

        record.getStock().setStockMoviment(record.getStock().getStockMoviment() - record.getQtyToModify());

        ((IStockService) ServiceProvider.getInstance(getApplication()).get(StockService.class)).updateStock(record.getStock());
    }

    public List<Drug> getDestroyedDrugs() throws SQLException {
        return ((IDrugService) ServiceProvider.getInstance(getApplication()).get(DrugService.class)).getAllDestroyedDrugs();
    }

    @Override
    public List<DestroyedDrug> getAllRelatedDestroyedStocks(DestroyedDrug relatedRecord) throws SQLException {
        return getDestroyedStockDrugDao().getByDateAndDrug(getApplication(), relatedRecord.getDate(), relatedRecord.getStock().getDrug());
    }

    @Override
    public List<Drug> getAllDrugsWithExistingLote() throws SQLException{
        return ((DrugService) ServiceProvider.getInstance(getApplication()).get(DrugService.class)).getAllWithLote();
    }

    @Override
    public void update(DestroyedDrug record) throws SQLException {
        super.update(record);

        DestroyedDrug oldDestruction = getDestroyedStockDrugDao().queryForId(record.getId());

        if (oldDestruction.getQtyToModify() != record.getQtyToModify()) {
            record.getStock().setStockMoviment(record.getStock().getStockMoviment() + oldDestruction.getQtyToModify() - record.getQtyToModify());

            ((IStockService) ServiceProvider.getInstance(getApplication()).get(StockService.class)).updateStock(record.getStock());
        }

        getDestroyedStockDrugDao().update(record);
    }

    @Override
    public void saveAll(List<DestroyedDrug> stocksToDestroy) throws SQLException {

        for (DestroyedDrug destroyedDrug : stocksToDestroy){
            if (destroyedDrug.getId() > 0){
                update(destroyedDrug);
            }else {
                save(destroyedDrug);
            }
        }
    }

    public List<DestroyedDrug> getRecords(long offset, long limit) throws SQLException {
        return getDestroyedStockDrugDao().searchDestructions(getApplication(),offset, limit);
    }

    private IDestroyedDrugDao getDestroyedStockDrugDao() throws SQLException {
        return getDataBaseHelper().getDestroyedStockDrugDao();
    }


    @Override
    public void delete(DestroyedDrug record) throws SQLException {
        super.delete(record);

        getDestroyedStockDrugDao().delete(record);

        doAfterDelete(record);
    }

    private void doAfterDelete(DestroyedDrug record) throws SQLException {
        record.getStock().setStockMoviment(record.getStock().getStockMoviment() + record.getQtyToModify());
        ((IStockService) ServiceProvider.getInstance(getApplication()).get(StockService.class)).updateStock(record.getStock());
    }
}
