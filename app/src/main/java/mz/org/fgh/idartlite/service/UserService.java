package mz.org.fgh.idartlite.service;

import android.app.Application;
import android.util.Log;

import androidx.collection.ArrayMap;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.sql.SQLException;
import java.util.Map;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.RESTServiceHandler;

public class UserService extends BaseService {

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
        getDataBaseHelper().getGenericDao(user).saveGenericObjectByClass(user);
    }


    public void getUsers(){

        getRestServiceExecutor().execute(() -> {

            RESTServiceHandler handler = new RESTServiceHandler();
            handler.setUser(getCurrentUser());

            Map<String, String> params = new ArrayMap<String, String>();
            params.put("id", "eq.213077");
            handler.addHeader("Content-Type","Application/json");

            handler.objectRequest("http://10.10.2.133:3001/users?id=eq.213077", Request.Method.GET, null, User[].class, new Response.Listener<User[]>() {
                @Override
                public void onResponse(User[] response) {
                    Log.d("Response", response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Response", error.getMessage());
                }
            });
        });
    }
}
