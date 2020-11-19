package mz.org.fgh.idartlite.service.stock;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.Iventory;
import mz.org.fgh.idartlite.model.User;

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

    @Override
    public void deleteRecord(Iventory selectedRecord) throws SQLException {
        super.deleteRecord(selectedRecord);

        getDataBaseHelper().getIventoryDao().delete(selectedRecord);
    }


}
