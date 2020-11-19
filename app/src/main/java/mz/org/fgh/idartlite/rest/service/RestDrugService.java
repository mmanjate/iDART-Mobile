package mz.org.fgh.idartlite.rest.service;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import mz.org.fgh.idartlite.base.rest.BaseRestService;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.helper.RESTServiceHandler;
import mz.org.fgh.idartlite.service.drug.DrugService;
import mz.org.fgh.idartlite.service.drug.IDiseaseTypeService;
import mz.org.fgh.idartlite.service.drug.IDrugService;
import mz.org.fgh.idartlite.service.drug.IFormService;

public class RestDrugService extends BaseRestService {

    private static final String TAG = "RestDrugService";
    private static IDrugService drugService;
    private static IFormService formService;
    private static IDiseaseTypeService diseaseTypeService;

    public RestDrugService(Application application, User currentUser) {
        super(application, currentUser);

        drugService = new DrugService(application,currentUser);
    }

    public static void restGetAllDrugs() {

        String url = BaseRestService.baseUrl + "/drug?select=*,form(*)&active=eq."+Boolean.TRUE;
        drugService = new DrugService(getApp(),null);

            getRestServiceExecutor().execute(() -> {

                RESTServiceHandler handler = new RESTServiceHandler();
                handler.addHeader("Content-Type", "Application/json");

                handler.objectRequest(url, Request.Method.GET, null, Object[].class, new Response.Listener<Object[]>() {
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
                        Log.e("Response", generateErrorMsg(error));
                    }
                });
            });
    }
}
