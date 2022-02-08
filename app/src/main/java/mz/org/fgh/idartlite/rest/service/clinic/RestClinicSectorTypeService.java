package mz.org.fgh.idartlite.rest.service.clinic;

import android.app.Application;
import android.provider.Settings;
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
import mz.org.fgh.idartlite.service.clinic.ClinicSectorTypeService;
import mz.org.fgh.idartlite.service.clinic.IClinicSectorTypeService;
import mz.org.fgh.idartlite.service.dispense.DispenseTypeService;

public class RestClinicSectorTypeService extends BaseRestService {
    private static final String TAG = "RestClinicSectorTypeService";
    private static IClinicSectorTypeService clinicSectorTypeService;

    public RestClinicSectorTypeService(Application application, User currentUser) {
        super(application, currentUser);
        clinicSectorTypeService = (IClinicSectorTypeService) getServiceFactory().get(ClinicSectorTypeService.class);
    }

    public static void restGetAllClinicSectorType()  {
        getAllClinicSectorType(null);
    }

    public static void restGetAllClinicSectorType(ServiceWatcher watcher)  {
        getAllClinicSectorType(watcher);
    }

    private static void getAllClinicSectorType(ServiceWatcher watcher)  {

        String url = BaseRestService.baseUrl + "/clinic_sector_type";
        clinicSectorTypeService = new ClinicSectorTypeService(getApp(), null);

        //clinicSectorTypeService = (IClinicSectorTypeService) getServiceFactory().get(ClinicSectorTypeService.class);

        getRestServiceExecutor().execute(() -> {

            RESTServiceHandler handler = new RESTServiceHandler();

            Map<String, Object> params = new ArrayMap<String, Object>();
            handler.addHeader("Content-Type", "Application/json");

            handler.objectRequest(url, Request.Method.GET, null, Object[].class, new Response.Listener<Object[]>() {
                @Override
                public void onResponse(Object[] clinicSectorTypes) {

                    if (clinicSectorTypes.length > 0) {

                        int counter = 0;

                        for (Object clinicSectorType : clinicSectorTypes) {
                            System.out.println("onResponse: " + clinicSectorType);
                            // Log.i(TAG, "onResponse: " + clinicSectorType);
                            try {
                                if(!clinicSectorTypeService.checkClinicSectorType(clinicSectorType)) {
                                    clinicSectorTypeService.saveOnClinicSectorType(clinicSectorType);
                                    counter++;
                                }else{
                                    System.out.println("onResponse: "+clinicSectorType+" Ja Existe");
                                    // Log.i(TAG, "onResponse: "+clinicSectorType+" Ja Existe");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }finally {
                                continue;
                            }
                        }
                        if (watcher != null && counter > 0) watcher.addUpdates(counter + " " +getApp().getString(R.string.new_pharmacy_types));

                    }else
                        System.out.println("Response Sem Info." + clinicSectorTypes.length);
                        // Log.w(TAG, "Response Sem Info." + clinicSectorTypes.length);
                }
            }, error -> Log.e("Response", generateErrorMsg(error)));
        });
    }
}
