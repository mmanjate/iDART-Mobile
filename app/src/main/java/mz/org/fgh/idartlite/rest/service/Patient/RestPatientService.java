package mz.org.fgh.idartlite.rest.service.Patient;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.TreeMap;
import java.util.UUID;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.rest.BaseRestService;
import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.listener.rest.RestResponseListener;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.ClinicSector;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.SyncMobilePatient;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.helper.RESTServiceHandler;
import mz.org.fgh.idartlite.service.clinic.ClinicSectorService;
import mz.org.fgh.idartlite.service.clinic.ClinicService;
import mz.org.fgh.idartlite.service.clinic.IClinicService;
import mz.org.fgh.idartlite.service.episode.EpisodeService;
import mz.org.fgh.idartlite.service.episode.IEpisodeService;
import mz.org.fgh.idartlite.service.patient.IPatientService;
import mz.org.fgh.idartlite.service.patient.PatientService;

public class RestPatientService extends BaseRestService {

    private static final String TAG = "RestPatientService";
    private static IClinicService clinicService;
    private static ClinicSectorService clinicSectorService;
    private static IEpisodeService episodeService;
    private static IPatientService patientService;

    public RestPatientService(Application application, User currentUser) {
        super(application, currentUser);

        clinicService = new ClinicService(getApplication(), null);

        patientService = new PatientService(getApplication(), null);
    }

    public static void restGetAllPatient(RestResponseListener listener) {

        try {
            clinicService = new ClinicService(getApp(), null);

            Clinic clinic = clinicService.getAllClinics().get(0);

            patientService = new PatientService(getApp(), null);

            String url = BaseRestService.baseUrl + "/sync_temp_patients?clinicuuid=eq." + clinic.getUuid() + "&syncstatus=eq.P&uuidopenmrs=not.in.(null,\"NA\")";

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
                                        if (!patientService.checkPatient(itemresult)) {
                                            patientService.saveOnPatient(itemresult);
                                        } else {
                                            Log.i(TAG, "onResponse: " + patient + " Ja Existe");
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } finally {
                                        continue;
                                    }
                                }
                            } else {
                                Log.w(TAG, "Response Sem Info." + patients.length);
                            }

                            if (listener != null) {
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
                Log.e(TAG, "Response Servidor Offline");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void restPostPatientSector(Patient patient) throws SQLException {

        String url = BaseRestService.baseUrl + "/sync_mobile_patient";

        clinicService = new ClinicService(BaseService.getApp(), null);
        clinicSectorService = new ClinicSectorService(getApp(), null);
        episodeService = new EpisodeService(getApp(), null);

        if (patient == null)


            try {
                Clinic finalClinic = clinicService.getAllClinics().get(0);
                ClinicSector clinicSector = (ClinicSector) clinicSectorService.getClinicSectorsByClinic(finalClinic);
                Episode episode = episodeService.getAllEpisodesByPatient(patient).get(0);

                if (episode.getSyncStatus().equalsIgnoreCase(BaseModel.SYNC_SATUS_READY))
                    getRestServiceExecutor().execute(() -> {

                        RESTServiceHandler handler = new RESTServiceHandler();

                        try {

                            SyncMobilePatient syncPatient = setSyncPatient(patient, episode, clinicSector, finalClinic);

                            Gson g = new Gson();
                            String restObject = g.toJson(syncPatient);

                            handler.addHeader("Content-Type", "application/json");
                            JSONObject jsonObject = new JSONObject(restObject);
                            handler.objectRequest(url, Request.Method.POST, jsonObject, Object[].class, new Response.Listener<TreeMap<String, Object>>() {

                                @Override
                                public void onResponse(TreeMap<String, Object> response) {
                                    Log.d(TAG, "onResponse: Paciente enviado : " + response);

                                    try {
                                        episode.setSyncStatus(BaseModel.SYNC_SATUS_SENT);
                                        episodeService.udpateEpisode(episode);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d(TAG, "onErrorResponse: Erro no POST :" + generateErrorMsg(error));
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
            } catch (Exception e) {
                e.printStackTrace();
            }
    }


    private static SyncMobilePatient setSyncPatient(Patient patient, Episode episode, ClinicSector clinicSector, Clinic clinic) {

        SyncMobilePatient syncMobilePatient = new SyncMobilePatient();

        syncMobilePatient.setAddress1(patient.getDistrict().getName());
        syncMobilePatient.setAddress2(patient.getAddress());
        syncMobilePatient.setAddress3("");
        syncMobilePatient.setCellphone(patient.getPhone());
        syncMobilePatient.setDateofbirth(patient.getBirthDate());
        syncMobilePatient.setNextofkinname("");
        syncMobilePatient.setNextofkinphone("");
        syncMobilePatient.setFirstnames(patient.getFirstName());
        syncMobilePatient.setHomephone("");
        syncMobilePatient.setLastname(patient.getLastName());
        syncMobilePatient.setPatientid(patient.getNid());
        syncMobilePatient.setProvince(patient.getProvince().getName());
        if (patient.getGender().startsWith("F"))
            syncMobilePatient.setSex('F');
        else
            syncMobilePatient.setSex('M');
        syncMobilePatient.setWorkphone("");
        syncMobilePatient.setRace("");
        syncMobilePatient.setUuidopenmrs("");
        syncMobilePatient.setSex('F');
        syncMobilePatient.setSyncstatus(BaseModel.SYNC_SATUS_SENT);
        syncMobilePatient.setSyncuuid(UUID.randomUUID().toString());
        syncMobilePatient.setClinicsectoruuid(clinicSector.getUuid());
        syncMobilePatient.setClinicuuid(clinic.getUuid());
        syncMobilePatient.setArvstartdate(patient.getStartARVDate());
        syncMobilePatient.setEnrolldate(episode.getEpisodeDate());

        return syncMobilePatient;

    }

}
