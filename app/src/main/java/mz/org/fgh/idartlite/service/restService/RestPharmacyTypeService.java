package mz.org.fgh.idartlite.service.restService;

import android.app.Application;
import android.util.Log;

import androidx.collection.ArrayMap;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.RESTServiceHandler;
import mz.org.fgh.idartlite.service.PharmacyTypeService;

public class RestPharmacyTypeService extends BaseService {

    private static final String TAG = "RestPharmacyTypeService";
    private static PharmacyTypeService pharmacyTypeService;

    public RestPharmacyTypeService(Application application, User currentUser) {
        super(application, currentUser);

        pharmacyTypeService = new PharmacyTypeService(application,currentUser);

    }

    public static void restGetAllPharmacyType()  {

        String url = BaseService.baseUrl + "/simpledomain?description=eq.pharmacy_type";
        pharmacyTypeService = new PharmacyTypeService(getApp(),null);

        getRestServiceExecutor().execute(() -> {

            RESTServiceHandler handler = new RESTServiceHandler();

            Map<String, Object> params = new ArrayMap<String, Object>();
            handler.addHeader("Content-Type", "Application/json");

            handler.objectRequest(url, Request.Method.GET, null, Object[].class, new Response.Listener<Object[]>() {
                @Override
                public void onResponse(Object[] pharmacyTypes) {

                    if (pharmacyTypes.length > 0) {
                        for (Object pharmacyType : pharmacyTypes) {
                            Log.i(TAG, "onResponse: " + pharmacyType);
                            try {
                                if(!pharmacyTypeService.checkPharmacyType(pharmacyType)) {
                                    pharmacyTypeService.saveOnPharmacyType(pharmacyType);
                                }else{
                                    Log.i(TAG, "onResponse: "+pharmacyType+" Ja Existe");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }finally {
                                continue;
                            }
                        }
                    }else
                        Log.w(TAG, "Response Sem Info." + pharmacyTypes.length);
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
