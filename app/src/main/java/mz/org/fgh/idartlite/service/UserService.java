package mz.org.fgh.idartlite.service;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.User;

public class UserService extends BaseService {


    public UserService(Application application) {
        super(application);
    }

    public List<User> getAll() throws SQLException {
        return getDataBaseHelper().getDao(User.class).queryForAll();
    }

    public boolean login(User user) throws SQLException {
        return getDataBaseHelper().getDao(User.class).queryBuilder().where().eq(User.COLUMN_USER_NAME, user.getUserName()).and().eq(User.COLUMN_PASSWORD, user.getPassword()) != null;
    }

}
