package mz.org.fgh.idartlite.rest.service;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.collection.ArrayMap;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.Map;

import mz.org.fgh.idartlite.base.rest.BaseRestService;
import mz.org.fgh.idartlite.listener.rest.RestResponseListener;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.helper.RESTServiceHandler;
import mz.org.fgh.idartlite.util.Utilities;

public class RestUserService extends BaseRestService {

    private static final String TAG = "RestUserService";
    public static final String auth = "USER_AUTHENTIC";
    public static final String[] nonauth = {"NOT_AUTHENTIC"};

    public RestUserService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public void restGetUser(String clinicuuid){

        String url = BaseRestService.baseUrl + "/users?username=eq."+currentUser.getUserName()+"&password=eq."+currentUser.getPassword()+"&clinicuuid=eq."+clinicuuid;

        getRestServiceExecutor().execute(() -> {

            RESTServiceHandler handler = new RESTServiceHandler();
            handler.setUser(getCurrentUser());
            JSONObject object = new JSONObject();
            Map<String, Object> params = new ArrayMap<String, Object>();
            params.put("username", "eq."+currentUser.getUserName());
            params.put("password", "eq."+currentUser.getPassword());
            params.put("clinicuuid", "eq."+clinicuuid);
            handler.addHeader("Content-Type","Application/json");

            handler.objectRequest(url, Request.Method.GET, null, JSONObject.class, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
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

        if (RESTServiceHandler.getServerStatus(BaseRestService.baseUrl)) {
            getRestServiceExecutor().execute(() -> {
                String url = BaseRestService.baseUrl + "/users?select=*,clinic(*)&cl_username=eq." +
                        username + "&cl_password=eq." + Utilities.MD5Crypt(password) +
                        "&clinic.uuid=eq." + clinicuuid;

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
                        Log.e("Response", generateErrorMsg(error));
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
