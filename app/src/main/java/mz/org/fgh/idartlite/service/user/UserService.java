package mz.org.fgh.idartlite.service.user;

import android.app.Application;

import java.sql.SQLException;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.dao.user.IUserDao;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.util.Utilities;

public class UserService extends BaseService<User> implements IUserService {

    private static final String TAG = "UserService";

    private IUserDao userDao;

    public UserService(Application application, User currUser) {
        super(application, currUser);
        initDao();
    }

    private void initDao() {
        try {
            userDao = getDataBaseHelper().getUserDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public UserService(Application application) {
        super(application);
        initDao();
    }

    @Override
    public void save(User record) throws SQLException {

    }

    @Override
    public void update(User record) throws SQLException {

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
