package mz.org.fgh.idartlite.rest.service.Dispense;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import mz.org.fgh.idartlite.base.rest.BaseRestService;
import mz.org.fgh.idartlite.base.rest.ServiceWatcher;
import mz.org.fgh.idartlite.listener.rest.RestResponseListener;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.helper.RESTServiceHandler;
import mz.org.fgh.idartlite.service.dispense.DispenseTypeService;
import mz.org.fgh.idartlite.service.dispense.IDispenseTypeService;

public class RestDispenseTypeService extends BaseRestService {

    private static final String TAG = "RestDispenseTypeService";
    private  static IDispenseTypeService dispenseTypeService;
    private static ServiceWatcher serviceWatcher;

    public RestDispenseTypeService(Application application, User currentUser) {
        super(application, currentUser);

        dispenseTypeService = new DispenseTypeService(application,currentUser);
    }

    public static void restGetAllDispenseType()  {
        getAllDispenseType(null);
    }

    public static void restGetAllDispenseType(RestResponseListener listener)  {
        getAllDispenseType(listener);
    }

    public static void getAllDispenseType(RestResponseListener listener) {

        String url = BaseRestService.baseUrl + "/simpledomain?description=eq.dispense_type";
        dispenseTypeService = new DispenseTypeService(getApp(), null);

        serviceWatcher = ServiceWatcher.fastCreate(TAG, url);

        serviceWatcher.setServiceAsRunning();

        if (listener != null) listener.registRunningService(serviceWatcher);

            getRestServiceExecutor().execute(() -> {

                RESTServiceHandler handler = new RESTServiceHandler();
                handler.addHeader("Content-Type", "Application/json");

                handler.objectRequest(url, Request.Method.GET, null, Object[].class, new Response.Listener<Object[]>() {
                    @Override
                    public void onResponse(Object[] dispenseTypes) {

                        if (dispenseTypes.length > 0) {
                            int counter = 0;

                            for (Object dispenseType : dispenseTypes) {

                                try {
                                    if(!dispenseTypeService.checkDipsenseType(dispenseType)){
                                        Log.i(TAG, "onResponse: " + dispenseType);
                                        dispenseTypeService.saveDispenseType(dispenseType);
                                        counter++;
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
                            if (counter > 0) serviceWatcher.setUpdates(counter+" novos Tipos de Dispensa");
                        }else
                            Log.w(TAG, "Response Sem Info." + dispenseTypes.length);

                        serviceWatcher.setServiceAsStopped();
                        if (listener != null) listener.updateServiceStatus(serviceWatcher);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        serviceWatcher.setServiceAsStopped();
                        if (listener != null) listener.updateServiceStatus(serviceWatcher);

                        Log.e("Response", generateErrorMsg(error));
                    }
                });
            });
    }
}
