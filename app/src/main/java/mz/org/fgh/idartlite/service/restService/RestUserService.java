package mz.org.fgh.idartlite.service.restService;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.collection.ArrayMap;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Map;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.base.RestResponseListener;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.RESTServiceHandler;
import mz.org.fgh.idartlite.util.Utilities;

public class RestUserService extends BaseService {

    private static final String TAG = "RestUserService";
    public static final String auth = "USER_AUTHENTIC";
    public static final String[] nonauth = {"NOT_AUTHENTIC"};

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

    public String getUserAuthentication(String clinicuuid, String username, String password, RestResponseListener listener) {

        final String[] result = {null};

        if (RESTServiceHandler.getServerStatus(BaseService.baseUrl)) {
            getRestServiceExecutor().execute(() -> {
                String url = BaseService.baseUrl + "/users?select=*,clinic(*)&cl_username=eq." + username + "&cl_password=eq." + Utilities.MD5Crypt(password) + "&clinic.uuid=eq." + clinicuuid;

                RESTServiceHandler handler = new RESTServiceHandler();

                handler.addHeader("Content-Type", "Application/json");

                handler.objectRequest(url, Request.Method.GET, null, Object[].class, new Response.Listener<Object[]>() {
                    @Override
                    public void onResponse(Object[] response) {

                        if (response.length > 0) {
                            result[0] = auth;
                        }else {
                            result[0] = nonauth[0];
                        }

                        listener.doOnRestSucessResponse(result[0]);

                        Log.d("Response", response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Response", error.getMessage());
                    }
                });
            });
        } else {
            Log.e(TAG, "Response Servidor Offline");
            Toast.makeText(getApplication(), "Servidor offline, por favor tente mais tarde", Toast.LENGTH_LONG).show();

        }
        return result[0];
    }
    
}
