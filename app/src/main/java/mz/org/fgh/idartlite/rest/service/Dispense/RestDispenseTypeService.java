package mz.org.fgh.idartlite.rest.service.Dispense;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.rest.BaseRestService;
import mz.org.fgh.idartlite.base.rest.ServiceWatcher;
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

    public static void restGetAllDispenseType(ServiceWatcher watcher)  {
        getAllDispenseType(watcher);
    }

    public static void getAllDispenseType(ServiceWatcher watcher) {

        String url = BaseRestService.baseUrl + "/simpledomain?description=eq.dispense_type";
        dispenseTypeService = new DispenseTypeService(getApp(), null);

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
                            if (watcher != null && counter > 0) watcher.addUpdates(counter + " "+getApp().getString(R.string.new_dispense_types));
                        }else
                            Log.w(TAG, "Response Sem Info." + dispenseTypes.length);
                    }
                }, error -> Log.e("Response", generateErrorMsg(error)));
            });
    }
}
