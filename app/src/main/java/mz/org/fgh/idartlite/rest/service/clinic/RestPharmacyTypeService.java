package mz.org.fgh.idartlite.rest.service.clinic;

import android.app.Application;
import android.util.Log;

import androidx.collection.ArrayMap;

import com.android.volley.Request;
import com.android.volley.Response;

import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.rest.BaseRestService;
import mz.org.fgh.idartlite.base.rest.ServiceWatcher;
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
        getAllPharmacyType(null);
    }

    public static void restGetAllPharmacyType(ServiceWatcher watcher)  {
        getAllPharmacyType(watcher);
    }

    private static void getAllPharmacyType(ServiceWatcher watcher)  {

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


                        int counter = 0;

                        for (Object pharmacyType : pharmacyTypes) {
                            Log.i(TAG, "onResponse: " + pharmacyType);
                            try {
                                if(!pharmacyTypeService.checkPharmacyType(pharmacyType)) {
                                    pharmacyTypeService.saveOnPharmacyType(pharmacyType);
                                    counter++;
                                }else{
                                    Log.i(TAG, "onResponse: "+pharmacyType+" Ja Existe");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }finally {
                                continue;
                            }
                        }
                         if (watcher != null && counter > 0) watcher.addUpdates(counter + " " +getApp().getString(R.string.new_pharmacy_types));

                    }else
                        Log.w(TAG, "Response Sem Info." + pharmacyTypes.length);
                }
            }, error -> Log.e("Response", generateErrorMsg(error)));
        });
    }
}
