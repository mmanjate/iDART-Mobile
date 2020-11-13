package mz.org.fgh.idartlite.rest.service;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.helper.RESTServiceHandler;
import mz.org.fgh.idartlite.service.dispense.DispenseTypeService;
import mz.org.fgh.idartlite.service.dispense.IDispenseTypeService;

public class RestDispenseTypeService extends BaseService {

    private static final String TAG = "RestDispenseTypeService";
    private  static IDispenseTypeService dispenseTypeService;
    
    public RestDispenseTypeService(Application application, User currentUser) {
        super(application, currentUser);

        dispenseTypeService = new DispenseTypeService(application,currentUser);
    }

    public static void restGetAllDispenseType() {

        String url = BaseService.baseUrl + "/simpledomain?description=eq.dispense_type";
        dispenseTypeService = new DispenseTypeService(getApp(), null);

            getRestServiceExecutor().execute(() -> {

                RESTServiceHandler handler = new RESTServiceHandler();
                handler.addHeader("Content-Type", "Application/json");

                handler.objectRequest(url, Request.Method.GET, null, Object[].class, new Response.Listener<Object[]>() {
                    @Override
                    public void onResponse(Object[] dispenseTypes) {

                        if (dispenseTypes.length > 0) {
                            for (Object dispenseType : dispenseTypes) {

                                try {
                                    if(!dispenseTypeService.checkDipsenseType(dispenseType)){
                                        Log.i(TAG, "onResponse: " + dispenseType);
                                        dispenseTypeService.saveDispenseType(dispenseType);
                                    }
                                    else{
                                        Log.i(TAG, "onResponse: "+dispenseType+" Ja Existe");
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.e(TAG, "onResponse: Error",e.getCause());
                                }finally {
                                    continue;
                                }
                            }
                        }else
                            Log.w(TAG, "Response Sem Info." + dispenseTypes.length);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Response", generateErrorMsg(error));
                    }
                });
            });
    }
}
