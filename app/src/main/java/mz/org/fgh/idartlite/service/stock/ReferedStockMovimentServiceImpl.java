package mz.org.fgh.idartlite.service.stock;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.base.service.ServiceProvider;
import mz.org.fgh.idartlite.dao.stock.IReferedStockMovimentDao;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.OperationType;
import mz.org.fgh.idartlite.model.ReferedStockMoviment;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.util.Utilities;

public class ReferedStockMovimentServiceImpl extends BaseService<ReferedStockMoviment> implements IReferedStockService {

    public ReferedStockMovimentServiceImpl(Application application, User currentUser) {
        super(application, currentUser);
    }

    public ReferedStockMovimentServiceImpl(Application application) {
        super(application);
    }

    private IReferedStockMovimentDao getDao() throws SQLException {
        return getDataBaseHelper().getReferedStockMovimentDao();
    }

    @Override
    public void save(ReferedStockMoviment record) throws SQLException {
        super.save(record);

        updateStock(record);

        getDao().create(record);
    }

    private void updateStock(ReferedStockMoviment record) throws SQLException {
        Stock stock = getDataBaseHelper().getStockDao().getByBatchNumber(record.getStock());

        if (stock != null){
            stock.setStockMoviment(stock.getStockMoviment()+record.getQuantity());
            getStockService().update(stock);
            record.setStock(stock);
        }else {
            record.getStock().setUnitsReceived(record.getQuantity());
            record.getStock().setStockMoviment(record.getQuantity());
            record.getStock().setUuid(Utilities.getNewUUID().toString());
            getStockService().save(record.getStock());
        }
    }

    private IStockService getStockService() {
        return (IStockService) ServiceProvider.getInstance(getApplication()).get(StockService.class);
    }

    @Override
    public void update(ReferedStockMoviment record) throws SQLException {
        super.update(record);

        ReferedStockMoviment r = getDao().queryForId(record.getId());

        r.getStock().setStockMoviment(r.getStock().getStockMoviment()-r.getQuantity()+record.getQuantity());
        getStockService().updateStock(r.getStock());


        getDao().update(record);
    }

    @Override
    public void delete(ReferedStockMoviment record) throws SQLException {
        super.delete(record);

        ReferedStockMoviment r = getDao().queryForId(record.getId());

        r.getStock().setStockMoviment(r.getStock().getStockMoviment()-r.getQuantity());
        getStockService().updateStock(r.getStock());

        getDao().delete(record);
    }

    @Override
    public List<ReferedStockMoviment> getAllWithPagination(long offset, long limit) throws SQLException {
        return getDao().searchRecords(offset, limit);
    }

    @Override
    public List<Drug> getallDrugs() throws SQLException {
        return getDataBaseHelper().getDrugDao().getAll();
    }

    @Override
    public List<OperationType> getAllOperationTypes() throws SQLException {
        return getDataBaseHelper().getOperationTypeDao().queryForAll();
    }

    @Override
    public void saveMany(List<ReferedStockMoviment> referedStockMovimentList) throws SQLException {

        for (ReferedStockMoviment referedStockMoviment : referedStockMovimentList){
            if (referedStockMoviment.getId() > 0){
                update(referedStockMoviment);
            }else {
                save(referedStockMoviment);
            }
        }
    }

    @Override
    public void tempCreateOperationTypes() throws SQLException {
        OperationType o1 = new OperationType("Entrada");
        OperationType o2 = new OperationType("Saída");

        if (!Utilities.listHasElements(getDataBaseHelper().getOperationTypeDao().queryForAll())) {
            getDataBaseHelper().getOperationTypeDao().create(o1);
            //getDataBaseHelper().getOperationTypeDao().create(o2);
        }

    }

    @Override
    public List<ReferedStockMoviment> getAllRelatedReferedStockMoviments(ReferedStockMoviment relatedRecord) throws SQLException {
        return getDao().queryBuilder().orderBy(ReferedStockMoviment.COLUMN_ID, true).where().eq(ReferedStockMoviment.COLUMN_ORDER_NUMBER, relatedRecord.getOrderNumber()).query();
    }
}
