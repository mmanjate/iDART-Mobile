package mz.org.fgh.idartlite.service;

import android.app.Application;
import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.User;

import java.sql.SQLException;

public class DispenseService extends BaseService {

    public DispenseService(Application application) {
        super(application);
    }

    public boolean login(User user) throws SQLException {
        return getDataBaseHelper().getUserDao().login(user);
    }

    public boolean checkIfUsertableIsEmpty() throws SQLException {
        return getDataBaseHelper().getUserDao().checkIfUsertableIsEmpty();
    }

    public void saveUser(User user) throws SQLException {
        getDataBaseHelper().getGenericDao(user).saveGenericObjectByClass(user);
    }
}
