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
    public void update(Iventory relatedRecord) throws SQLException {
        super.update(relatedRecord);
        getDataBaseHelper().getIventoryDao().update(relatedRecord);
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
        return ((IStockAjustmentService) ServiceProvider.getInstance(getApplication()).get(IStockAjustmentService.class)).getAllOfInventory(selectedRecord);
    }

    @Override
    public List<Stock> getAllOfDrug(Drug selectedDrug) throws SQLException {
        return ((StockService) ServiceProvider.getInstance(getApplication()).get(StockService.class)).getAll(selectedDrug);
    }

    public List<Drug> getAllDrugsWithExistingLote() throws SQLException{
        return ((DrugService) ServiceProvider.getInstance(getApplication()).get(DrugService.class)).getAllWithLote();
    }

    @Override
    public void initInventory(Iventory record) throws SQLException {
        Iventory lastInventoryRecord = getDataBaseHelper().getIventoryDao().getLastInventory();

        if (lastInventoryRecord != null && lastInventoryRecord.isOpen()) {
            Utilities.displayAlertDialog(getApplication().getApplicationContext(), "O inventário "+lastInventoryRecord.getSequence()+" encontra-se aberto, por favor finalizar antes de iniciar novo.").show();
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
        record.setEndDate(DateUtilities.getCurrentDate());
        record.setOpen(false);
        update(record);
    }

    @Override
    public void deleteRecord(Iventory selectedRecord) throws SQLException {
        super.deleteRecord(selectedRecord);

        getDataBaseHelper().getIventoryDao().delete(selectedRecord);
    }
}
