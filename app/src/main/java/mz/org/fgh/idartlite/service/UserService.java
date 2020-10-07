package mz.org.fgh.idartlite.service;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.util.Util;

import java.sql.SQLException;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.base.RestResponseListener;
import mz.org.fgh.idartlite.dao.PatientDao;
import mz.org.fgh.idartlite.dao.UserDao;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.RESTServiceHandler;
import mz.org.fgh.idartlite.util.Utilities;

public class UserService extends BaseService {

    private static final String TAG = "UserService";

    private UserDao userDao;

    public UserService(Application application, User currUser) {
        super(application, currUser);
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
        userDao.saveGenericObjectByClass(user);
    }


}
