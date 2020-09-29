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
import mz.org.fgh.idartlite.service.DrugService;
import mz.org.fgh.idartlite.service.FormService;

public class RestDrugService extends BaseService {

    private static final String TAG = "RestDrugService";
    private static DrugService drugService;
    private static FormService formService;
    private static DiseaseTypeService diseaseTypeService;

    public RestDrugService(Application application, User currentUser) {
        super(application, currentUser);

        drugService = new DrugService(application,currentUser);
    }

    public static void restGetAllDrugs() {

        String url = BaseService.baseUrl + "/drug?select=*,form(*)&active=eq."+Boolean.TRUE;
        drugService = new DrugService(getApp(),null);

            getRestServiceExecutor().execute(() -> {

                RESTServiceHandler handler = new RESTServiceHandler();
//                   handler.setUser(getCurrentUser());

                Map<String, Object> params = new ArrayMap<String, Object>();
                handler.addHeader("Content-Type", "Application/json");

                handler.objectRequest(url, Request.Method.GET, params, Object[].class, new Response.Listener<Object[]>() {
                    @Override
                    public void onResponse(Object[] drugs) {

                        if (drugs.length > 0) {
                            for (Object drug : drugs) {
                                try {
                                    Log.i(TAG, "onResponse: " + drug);
                                    if(!drugService.checkDrug(drug)){
                                        drugService.saveOnDrug(drug);
                                    }else{
                                        Log.i(TAG, "onResponse: "+drug+" Ja Existe");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }finally {
                                    continue;
                                }
                            }
                        }else
                            Log.w(TAG, "Response Sem Info." + drugs.length);
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
