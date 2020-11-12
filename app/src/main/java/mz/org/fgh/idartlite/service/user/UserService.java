package mz.org.fgh.idartlite.service.user;

import android.app.Application;

import java.sql.SQLException;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.dao.user.IUserDao;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.util.Utilities;

public class UserService extends BaseService implements IUserService {

    private static final String TAG = "UserService";

    private IUserDao userDao;

    public UserService(Application application, User currUser) {
        super(application, currUser);
        try {
            userDao = getDataBaseHelper().getUserDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserService(Application application) {
        super(application);
        try {
            userDao = getDataBaseHelper().getUserDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean login(User user) throws SQLException {
        return userDao.login(user);
    }

    public boolean checkIfUsertableIsEmpty() throws SQLException {
        return userDao.checkIfUsertableIsEmpty();
    }

    public void saveUser(User user) throws SQLException {
        user.setPassword(Utilities.MD5Crypt(user.getPassword()));
        userDao.create(user);
    }


}
