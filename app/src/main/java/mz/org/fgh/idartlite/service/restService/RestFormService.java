package mz.org.fgh.idartlite.service.restService;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.RESTServiceHandler;
import mz.org.fgh.idartlite.service.FormService;

public class RestFormService extends BaseService {

    private static final String TAG = "RestFormService";
    private static FormService formService;

    public RestFormService(Application application, User currentUser) {
        super(application, currentUser);

        formService = new FormService(application,currentUser);

    }

    public static void restGetAllForms() {

        String url = BaseService.baseUrl + "/form";
        formService = new FormService(getApp(),null);

            getRestServiceExecutor().execute(() -> {

                RESTServiceHandler handler = new RESTServiceHandler();
                handler.addHeader("Content-Type", "Application/json");

                handler.objectRequest(url, Request.Method.GET, null, Object[].class, new Response.Listener<Object[]>() {
                    @Override
                    public void onResponse(Object[] forms) {

                        if (forms.length > 0) {
                            for (Object form : forms) {
                                Log.i(TAG, "onResponse: " + form);
                                try {
                                    if(!formService.checkForm(form)){
                                        formService.saveOnForm(form);
                                    }else{
                                        Log.i(TAG, "onResponse: "+form+" Ja Existe");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }finally {
                                    continue;
                                }
                            }
                        }else
                            Log.w(TAG, "Response Sem Info." + forms.length);
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
