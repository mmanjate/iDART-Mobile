package mz.org.fgh.idartlite.service;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.sql.SQLException;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.base.RestResponseListener;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.RESTServiceHandler;
import mz.org.fgh.idartlite.util.Utilities;

public class UserService extends BaseService {

    private static final String TAG = "UserService";

    public UserService(Application application, User currUser) {
        super(application, currUser);
    }

    public boolean login(User user) throws SQLException {
        return getDataBaseHelper().getUserDao().login(user);
    }

    public boolean checkIfUsertableIsEmpty() throws SQLException {
        return getDataBaseHelper().getUserDao().checkIfUsertableIsEmpty();
    }

    public void saveUser(User user) throws SQLException {
        getDataBaseHelper().getUserDao().saveGenericObjectByClass(user);
    }


}
