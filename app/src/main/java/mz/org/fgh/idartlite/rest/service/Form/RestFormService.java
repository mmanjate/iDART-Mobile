package mz.org.fgh.idartlite.rest.service.Form;

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
import mz.org.fgh.idartlite.service.drug.FormService;
import mz.org.fgh.idartlite.service.drug.IFormService;

public class RestFormService extends BaseRestService {

    private static final String TAG = "RestFormService";
    private static IFormService formService;

    public RestFormService(Application application, User currentUser) {
        super(application, currentUser);

        formService = new FormService(application,currentUser);

    }

    public static void restGetAllForms()  {
        getAllForms(null);
    }

    public static void restGetAllForms(RestResponseListener listener)  {
        getAllForms(listener);
    }

    public static void getAllForms(RestResponseListener listener) {

        String url = BaseRestService.baseUrl + "/form";
        formService = new FormService(getApp(),null);

        ServiceWatcher serviceWatcher = ServiceWatcher.fastCreate(TAG, url);

        serviceWatcher.setServiceAsRunning();

        if (listener != null) listener.registRunningService(serviceWatcher);

            getRestServiceExecutor().execute(() -> {

                RESTServiceHandler handler = new RESTServiceHandler();
                handler.addHeader("Content-Type", "Application/json");

                handler.objectRequest(url, Request.Method.GET, null, Object[].class, new Response.Listener<Object[]>() {
                    @Override
                    public void onResponse(Object[] forms) {

                        if (forms.length > 0) {


                            int counter = 0;
                            for (Object form : forms) {
                                Log.i(TAG, "onResponse: " + form);
                                try {
                                    if(!formService.checkForm(form)){
                                        formService.saveOnForm(form);
                                        counter++;
                                    }else{
                                        Log.i(TAG, "onResponse: "+form+" Ja Existe");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }finally {
                                    continue;
                                }
                            }
                            if (counter > 0) serviceWatcher.setUpdates(counter +" novos Formns");
                        }else
                            Log.w(TAG, "Response Sem Info." + forms.length);

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
