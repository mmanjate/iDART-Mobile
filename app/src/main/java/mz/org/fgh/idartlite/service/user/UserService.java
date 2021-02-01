package mz.org.fgh.idartlite.service.user;

import android.app.Application;

import java.sql.SQLException;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.dao.user.IUserDao;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.service.User.RestUserService;
import mz.org.fgh.idartlite.util.Utilities;

public class UserService extends BaseService<User> implements IUserService {

    private static final String TAG = "UserService";

    private IUserDao userDao;

    private RestUserService restUserService;

    public UserService(Application application, User currUser) {
        super(application, currUser);
        init();
    }

    private void init() {
        try {
            userDao = getDataBaseHelper().getUserDao();
            restUserService = new RestUserService(getApplication());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public UserService(Application application) {
        super(application);
        init();
    }

    @Override
    public void save(User record) throws SQLException {
        saveUser(record);
    }

    @Override
    public void authenticate(User record) throws SQLException {

    }

    @Override
    public void update(User record) throws SQLException {
        userDao.update(record);
    }

    public boolean login(User user) throws SQLException {
        return userDao.login(user);
    }

    public boolean checkIfUsertableIsEmpty() throws SQLException {
        return userDao.checkIfUsertableIsEmpty();
    }

    public void saveUser(User user) throws SQLException {
        user.setPassword(Utilities.MD5Crypt(user.getPassword()));
        if (user.getId() <= 0) {
            userDao.create(user);
        }else update(user);
    }

}
