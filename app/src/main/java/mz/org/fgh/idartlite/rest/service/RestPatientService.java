package mz.org.fgh.idartlite.rest.service;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.internal.LinkedTreeMap;

import mz.org.fgh.idartlite.base.rest.BaseRestService;
import mz.org.fgh.idartlite.listener.rest.RestResponseListener;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.helper.RESTServiceHandler;
import mz.org.fgh.idartlite.service.clinic.ClinicService;
import mz.org.fgh.idartlite.service.clinic.IClinicService;
import mz.org.fgh.idartlite.service.patient.IPatientService;
import mz.org.fgh.idartlite.service.patient.PatientService;

public class RestPatientService extends BaseRestService {

    private static final String TAG = "RestPatientService";
    private static IClinicService clinicService ;
    private static IPatientService patientService;

    public RestPatientService(Application application, User currentUser) {
        super(application, currentUser);

        clinicService = new ClinicService(getApplication(),null);
        patientService = new PatientService(getApplication(),null);
    }

    public static void restGetAllPatient(RestResponseListener listener) {

        try {
            clinicService = new ClinicService(getApp(),null);

            Clinic clinic = clinicService.getAllClinics().get(0);

        patientService = new PatientService(getApp(),null);

        String url = BaseRestService.baseUrl + "/sync_temp_patients?clinicuuid=eq."+clinic.getUuid()+"&syncstatus=eq.P&uuidopenmrs=not.in.(null,\"NA\")";

        if (RESTServiceHandler.getServerStatus(BaseRestService.baseUrl)) {
            getRestServiceExecutor().execute(() -> {

                RESTServiceHandler handler = new RESTServiceHandler();
                handler.addHeader("Content-Type", "Application/json");

                handler.objectRequest(url, Request.Method.GET, null, Object[].class, new Response.Listener<Object[]>() {
                    @Override
                    public void onResponse(Object[] patients) {
                        Log.d("Response", String.valueOf(patients.length));

                        if (patients.length > 0) {
                            for (Object patient : patients) {
                                Log.i(TAG, "onResponse: " + patient);
                                try {
                                    LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) patient;
                                    if(!patientService.checkPatient(itemresult)){
                                        patientService.saveOnPatient(itemresult);
                                    }else{
                                        Log.i(TAG, "onResponse: "+patient+" Ja Existe");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }finally {
                                    continue;
                                }
                            }
                        }else {
                            Log.w(TAG, "Response Sem Info." + patients.length);
                        }

                        if(listener != null){
                            listener.doOnRestSucessResponse(null);
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Response", generateErrorMsg(error));
                    }
                });
            });

        } else {
            Log.e(TAG,"Response Servidor Offline");
        }
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }
}
