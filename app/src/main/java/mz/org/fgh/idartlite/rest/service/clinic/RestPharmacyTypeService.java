package mz.org.fgh.idartlite.rest.service.clinic;

import android.app.Application;
import android.util.Log;

import androidx.collection.ArrayMap;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Map;

import mz.org.fgh.idartlite.base.rest.BaseRestService;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.helper.RESTServiceHandler;
import mz.org.fgh.idartlite.service.clinic.IPharmacyTypeService;
import mz.org.fgh.idartlite.service.clinic.PharmacyTypeService;

public class RestPharmacyTypeService extends BaseRestService {

    private static final String TAG = "RestPharmacyTypeService";
    private static IPharmacyTypeService pharmacyTypeService;

    public RestPharmacyTypeService(Application application, User currentUser) {
        super(application, currentUser);

        pharmacyTypeService = (IPharmacyTypeService) getServiceFactory().get(PharmacyTypeService.class);

    }

    public static void restGetAllPharmacyType()  {

        String url = BaseRestService.baseUrl + "/simpledomain?description=eq.pharmacy_type";

        pharmacyTypeService = (IPharmacyTypeService) getServiceFactory().get(PharmacyTypeService.class);

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
                    Log.e("Response", generateErrorMsg(error));
                }
            });
        });
    }
}
