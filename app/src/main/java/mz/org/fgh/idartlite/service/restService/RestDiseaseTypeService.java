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
import mz.org.fgh.idartlite.service.DiseaseTypeService;

public class RestDiseaseTypeService extends BaseService {

    private static final String TAG = "RestDiseaseTypeService";
    private static DiseaseTypeService diseaseTypeService;

    public RestDiseaseTypeService(Application application, User currentUser) {
        super(application, currentUser);

        diseaseTypeService = new DiseaseTypeService(application,currentUser);
    }

    public static void restGetAllDiseaseType()  {

        String url = BaseService.baseUrl + "/simpledomain?description=eq.disease_type";
        diseaseTypeService = new DiseaseTypeService(getApp(),null);

            getRestServiceExecutor().execute(() -> {

                RESTServiceHandler handler = new RESTServiceHandler();

                Map<String, Object> params = new ArrayMap<String, Object>();
                handler.addHeader("Content-Type", "Application/json");

                handler.objectRequest(url, Request.Method.GET, null, Object[].class, new Response.Listener<Object[]>() {
                    @Override
                    public void onResponse(Object[] diseases) {

                        if (diseases.length > 0) {
                            for (Object disease : diseases) {
                                Log.i(TAG, "onResponse: " + disease);
                                try {
                                    if(!diseaseTypeService.checkDisease(disease)){
                                        diseaseTypeService.saveDisease(disease);
                                    }else{
                                        Log.i(TAG, "onResponse: "+disease+" Ja Existe");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }finally {
                                    continue;
                                }
                            }
                        }else
                            Log.w(TAG, "Response Sem Info." + diseases.length);
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
