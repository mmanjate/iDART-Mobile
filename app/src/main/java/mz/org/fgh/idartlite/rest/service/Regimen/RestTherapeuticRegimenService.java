package mz.org.fgh.idartlite.rest.service.Regimen;

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
import mz.org.fgh.idartlite.service.drug.ITherapheuticRegimenService;
import mz.org.fgh.idartlite.service.drug.TherapheuticRegimenService;

public class RestTherapeuticRegimenService extends BaseRestService {

    private static final String TAG = "RestTherapeuticRegimenS";
    private static ITherapheuticRegimenService therapeuticRegimenService;

    public RestTherapeuticRegimenService(Application application, User currentUser) {
        super(application, currentUser);

         therapeuticRegimenService = new TherapheuticRegimenService(application,currentUser);

    }

    public static void restGetAllTherapeuticRegimen()  {
        getAllTherapeuticRegimen(null);
    }

    public static void restGetAllTherapeuticRegimen(RestResponseListener listener)  {
        getAllTherapeuticRegimen(listener);
    }


    public static void getAllTherapeuticRegimen(RestResponseListener listener) {

        String url = BaseRestService.baseUrl + "/regimeterapeutico?select=*,drug(*)&active=eq.true";
        therapeuticRegimenService = new TherapheuticRegimenService(getApp(),null);

        ServiceWatcher serviceWatcher = ServiceWatcher.fastCreate(TAG, url);

        serviceWatcher.setServiceAsRunning();

        if (listener != null) listener.registRunningService(serviceWatcher);

            getRestServiceExecutor().execute(() -> {

                RESTServiceHandler handler = new RESTServiceHandler();
                handler.addHeader("Content-Type", "Application/json");

                handler.objectRequest(url, Request.Method.GET, null, Object[].class, new Response.Listener<Object[]>() {
                    @Override
                    public void onResponse(Object[] regimesterapeuticos) {

                        if (regimesterapeuticos.length > 0) {
                            int counter = 0;

                            for (Object regimen : regimesterapeuticos) {
                                Log.i(TAG, "onResponse: " + regimen);
                                try {
                                    if(!therapeuticRegimenService.checkRegimen(regimen)){
                                        therapeuticRegimenService.saveRegimen(regimen);
                                        counter++;
                                    }else{
                                        Log.i(TAG, "onResponse: "+regimen+" Ja Existe");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }finally {
                                    continue;
                                }
                            }
                            if (counter > 0) serviceWatcher.setUpdates(counter +" novos Regimes Terapéuticos");
                        }else
                            Log.w(TAG, "Response Sem Info." + regimesterapeuticos.length);

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
