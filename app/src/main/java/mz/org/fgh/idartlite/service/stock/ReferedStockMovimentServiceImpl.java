package mz.org.fgh.idartlite.service.stock;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.dao.stock.IReferedStockMovimentDao;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.OperationType;
import mz.org.fgh.idartlite.model.ReferedStockMoviment;
import mz.org.fgh.idartlite.model.User;

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

        getDao().create(record);
    }

    @Override
    public void update(ReferedStockMoviment record) throws SQLException {
        super.update(record);

        getDao().update(record);
    }

    @Override
    public void delete(ReferedStockMoviment record) throws SQLException {
        super.delete(record);

        getDao().delete(record);
    }

    @Override
    public List<ReferedStockMoviment> getAllWithPagination(long offset, long limit) throws SQLException {
        return getDao().searchRecords(offset, limit);
    }

    @Override
    public List<Drug> getAllDrugsWithStock() throws SQLException {
        return getDataBaseHelper().getDrugDao().getAllWithLote(getApplication());
    }

    @Override
    public List<OperationType> getAllOperationTypes() throws SQLException {
        return getDataBaseHelper().getOperationTypeDao().queryForAll();
    }

    @Override
    public void saveMany(List<ReferedStockMoviment> referedStockMovimentList) throws SQLException {

        for (ReferedStockMoviment referedStockMoviment : referedStockMovimentList){
            save(referedStockMoviment);
        }
    }
}
