package mz.org.fgh.idartlite.rest.service.Disease;

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
import mz.org.fgh.idartlite.service.drug.DiseaseTypeService;
import mz.org.fgh.idartlite.service.drug.IDiseaseTypeService;

public class RestDiseaseTypeService extends BaseRestService {

    private static final String TAG = "RestDiseaseTypeService";
    private static IDiseaseTypeService diseaseTypeService;

    public RestDiseaseTypeService(Application application, User currentUser) {
        super(application, currentUser);

        diseaseTypeService = new DiseaseTypeService(application,currentUser);
    }

    public static void restGetAllDiseaseType()  {
        getAllDiseaseType(null);
    }

    public static void restGetAllDiseaseType(RestResponseListener listener)  {
        getAllDiseaseType(listener);
    }

    public static void getAllDiseaseType(RestResponseListener listener)  {

        String url = BaseRestService.baseUrl + "/simpledomain?description=eq.disease_type";
        diseaseTypeService = new DiseaseTypeService(getApp(),null);

        ServiceWatcher serviceWatcher = ServiceWatcher.fastCreate(TAG, url);

        serviceWatcher.setServiceAsRunning();

        if (listener != null) listener.registRunningService(serviceWatcher);

            getRestServiceExecutor().execute(() -> {

                RESTServiceHandler handler = new RESTServiceHandler();
                handler.addHeader("Content-Type", "Application/json");

                handler.objectRequest(url, Request.Method.GET, null, Object[].class, new Response.Listener<Object[]>() {
                    @Override
                    public void onResponse(Object[] diseases) {

                        if (diseases.length > 0) {
                            int counter = 0;

                            for (Object disease : diseases) {
                                Log.i(TAG, "onResponse: " + disease);
                                try {
                                    if(!diseaseTypeService.checkDisease(disease)){
                                        diseaseTypeService.saveDisease(disease);
                                        counter++;
                                    }else{
                                        Log.i(TAG, "onResponse: "+disease+" Ja Existe");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }finally {
                                    continue;
                                }
                            }
                            if (counter > 0) serviceWatcher.setUpdates(counter +" novos Diseases");
                        }else
                            Log.w(TAG, "Response Sem Info." + diseases.length);

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
