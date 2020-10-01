package mz.org.fgh.idartlite.service.restService;

import android.app.Application;
import android.util.Log;

import androidx.collection.ArrayMap;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Map;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.RESTServiceHandler;

public class RestUserService extends BaseService {


    public RestUserService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public void restGetUser(String clinicuuid){

        String url = BaseService.baseUrl + "/users?username=eq."+currentUser.getUserName()+"&password=eq."+currentUser.getPassword()+"&clinicuuid=eq."+clinicuuid;

        getRestServiceExecutor().execute(() -> {

            RESTServiceHandler handler = new RESTServiceHandler();
            handler.setUser(getCurrentUser());

            Map<String, Object> params = new ArrayMap<String, Object>();
            params.put("username", "eq."+currentUser.getUserName());
            params.put("password", "eq."+currentUser.getPassword());
            params.put("clinicuuid", "eq."+clinicuuid);
            handler.addHeader("Content-Type","Application/json");

            handler.objectRequest(url, Request.Method.GET, null, User[].class, new Response.Listener<User[]>() {
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
