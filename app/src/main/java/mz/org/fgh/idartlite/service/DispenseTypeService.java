package mz.org.fgh.idartlite.service;

import android.app.Application;
import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.DispenseType;
import mz.org.fgh.idartlite.model.TherapeuticLine;
import mz.org.fgh.idartlite.model.User;

import java.sql.SQLException;
import java.util.List;

public class DispenseTypeService extends BaseService {

    public DispenseTypeService(Application application, User currUser) {
        super(application, currUser);
    }

    public void createDispenseType(DispenseType dispenseType) throws SQLException {
        getDataBaseHelper().getDispenseTypeDao().create(dispenseType);
    }

    public List<DispenseType> getAll() throws SQLException {
        return getDataBaseHelper().getDispenseTypeDao().queryForAll();
    }
}
