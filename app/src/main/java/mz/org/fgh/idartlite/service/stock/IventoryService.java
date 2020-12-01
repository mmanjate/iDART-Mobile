package mz.org.fgh.idartlite.service.stock;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.base.service.ServiceProvider;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.StockAjustment;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.model.inventory.Iventory;
import mz.org.fgh.idartlite.service.drug.DrugService;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;

public class IventoryService extends BaseService<Iventory> implements IIventoryService {

    public IventoryService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public IventoryService(Application application) {
        super(application);
    }

    @Override
    public void save(Iventory record) throws SQLException {
        getDataBaseHelper().getIventoryDao().create(record);
    }

    @Override
    public void update(Iventory record) throws SQLException {
        super.update(record);
        getDataBaseHelper().getIventoryDao().update(record);
    }

    @Override
    public List<Iventory> getAllWithPagination(long offset, long limit) throws SQLException {
        return getDataBaseHelper().getIventoryDao().searchRecords(offset, limit);
    }

    public boolean isLastInventoryOpen() throws SQLException {
        Iventory lastInventoryRecord = getDataBaseHelper().getIventoryDao().getLastInventory();

        if (lastInventoryRecord != null) return lastInventoryRecord.isOpen();

        return false;
    }

    @Override
    public List<StockAjustment> getAllStockAjustmentsOfInventory(Iventory selectedRecord) throws SQLException {
        return ((IStockAjustmentService) ServiceProvider.getInstance(getApplication()).get(StockAjustementService.class)).getAllOfInventory(selectedRecord);
    }

    @Override
    public List<Stock> getAllOfDrug(Drug selectedDrug) throws SQLException {
        return ((StockService) ServiceProvider.getInstance(getApplication()).get(StockService.class)).getAll(selectedDrug);
    }

    public List<Drug> getAllDrugsWithExistingLote() throws SQLException{
        return ((DrugService) ServiceProvider.getInstance(getApplication()).get(DrugService.class)).getAllWithLote();
    }

    @Override
    public void saveAjustment(StockAjustment ajustment) throws SQLException {
        if (ajustment.getId() > 0){
            ServiceProvider.getInstance(getApplication()).get(StockAjustementService.class).update(ajustment);
        }else {
            ServiceProvider.getInstance(getApplication()).get(StockAjustementService.class).save(ajustment);
        }
    }

    @Override
    public void initInventory(Iventory record) throws SQLException {
        Iventory lastInventoryRecord = getDataBaseHelper().getIventoryDao().getLastInventory();

        if (lastInventoryRecord != null && lastInventoryRecord.isOpen()) {
            Utilities.displayAlertDialog(getApplication(), "O inventário "+lastInventoryRecord.getSequence()+" encontra-se aberto, por favor finalizar antes de iniciar novo.").show();
        }else {
            record.setStartDate(DateUtilities.getCurrentDate());
            record.setOpen(true);
            record.setSyncStatus(BaseModel.SYNC_SATUS_READY);
            record.setSequence(getLastGeneratedSequence(lastInventoryRecord) + 1);

            save(record);
        }
    }

    private int getLastGeneratedSequence(Iventory record) throws SQLException {
        if (record != null){
            return record.getSequence();
        }

        return 0;
    }

    @Override
    public void closeInventory(Iventory record) throws SQLException {
        if (record.isOpen()) {
            updateAndProcessesAjustments(record);

            record.setEndDate(DateUtilities.getCurrentDate());
            record.setOpen(false);
            update(record);
        }else {
            rebuildPreviousStock(record);

            updateAndProcessesAjustments(record);
        }
    }

    private void updateAndProcessesAjustments(Iventory record) throws SQLException {
        ((IStockAjustmentService) ServiceProvider.getInstance(getApplication()).get(StockAjustementService.class)).saveOrUpdateMany(record.getAjustmentList());
        processAjustments(record);
    }

    private void rebuildPreviousStock(Iventory record) throws SQLException {
        List<StockAjustment> ajustmentsOnDb =  ((IStockAjustmentService) ServiceProvider.getInstance(getApplication()).get(StockAjustementService.class)).getAllOfInventory(record);

        for (StockAjustment ajustment : ajustmentsOnDb){
            ajustment.getStock().setStockMoviment(ajustment.getStock().getStockMoviment() - ajustment.getAdjustedValue());
            ((IStockService) ServiceProvider.getInstance(getApplication()).get(StockService.class)).updateStock(ajustment.getStock());
        }
    }

    private void processAjustments(Iventory record) throws SQLException {
        for (StockAjustment ajustment : record.getAjustmentList()) {
            if (ajustment.getStock().getStockMoviment() != ajustment.getStockCount()) {
                ajustment.getStock().setStockMoviment(ajustment.getStockCount());
                ((IStockService) ServiceProvider.getInstance(getApplication()).get(StockService.class)).updateStock(ajustment.getStock());
            }
        }
    }

    @Override
    public void deleteRecord(Iventory record) throws SQLException {
        super.deleteRecord(record);

        List<StockAjustment> ajustmentsOnDb =  ((IStockAjustmentService) ServiceProvider.getInstance(getApplication()).get(StockAjustementService.class)).getAllOfInventory(record);

        for (StockAjustment ajustment : ajustmentsOnDb){
            ServiceProvider.getInstance(getApplication()).get(StockAjustementService.class).deleteRecord(ajustment);
        }

        getDataBaseHelper().getIventoryDao().delete(record);
    }
}
