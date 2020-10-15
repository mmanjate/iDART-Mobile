package mz.org.fgh.idartlite.service.restService;

import android.app.Application;
import android.util.Log;

import androidx.collection.ArrayMap;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.RESTServiceHandler;
import mz.org.fgh.idartlite.service.TherapheuticRegimenService;
import mz.org.fgh.idartlite.util.Utilities;

public class RestTherapeuticRegimenService extends BaseService {

    private static final String TAG = "RestTherapeuticRegimenS";
    private static TherapheuticRegimenService therapeuticRegimenService;

    public RestTherapeuticRegimenService(Application application, User currentUser) {
        super(application, currentUser);

         therapeuticRegimenService = new TherapheuticRegimenService(application,currentUser);

    }

    public static void restGetAllTherapeuticRegimen() {

        String url = BaseService.baseUrl + "/regimeterapeutico?select=*,drug(*)&active=true";
        therapeuticRegimenService = new TherapheuticRegimenService(getApp(),null);

            getRestServiceExecutor().execute(() -> {

                RESTServiceHandler handler = new RESTServiceHandler();
                handler.addHeader("Content-Type", "Application/json");

                handler.objectRequest(url, Request.Method.GET, null, Object[].class, new Response.Listener<Object[]>() {
                    @Override
                    public void onResponse(Object[] regimesterapeuticos) {

                        if (regimesterapeuticos.length > 0) {
                            for (Object regimen : regimesterapeuticos) {
                                Log.i(TAG, "onResponse: " + regimen);
                                try {
                                    if(!therapeuticRegimenService.checkRegimen(regimen)){
                                        therapeuticRegimenService.saveRegimen(regimen);
                                    }else{
                                        Log.i(TAG, "onResponse: "+regimen+" Ja Existe");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }finally {
                                    continue;
                                }
                            }
                        }else
                            Log.w(TAG, "Response Sem Info." + regimesterapeuticos.length);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Response", Utilities.stringHasValue(error.getMessage()) ? error.getMessage() : error instanceof TimeoutError ? "Time out" : "Unkown error");
                    }
                });
            });
    }

}
