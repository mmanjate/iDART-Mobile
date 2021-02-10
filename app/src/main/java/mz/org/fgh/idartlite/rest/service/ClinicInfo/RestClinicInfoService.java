package mz.org.fgh.idartlite.rest.service.ClinicInfo;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.util.ContentLengthInputStream;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.rest.BaseRestService;
import mz.org.fgh.idartlite.listener.rest.RestResponseListener;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.SyncClinicInformation;
import mz.org.fgh.idartlite.model.SyncDispense;
import mz.org.fgh.idartlite.model.SyncEpisode;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.helper.RESTServiceHandler;
import mz.org.fgh.idartlite.service.clinic.ClinicService;
import mz.org.fgh.idartlite.service.clinic.IClinicService;
import mz.org.fgh.idartlite.service.clinicInfo.ClinicInfoService;
import mz.org.fgh.idartlite.service.clinicInfo.IClinicInfoService;
import mz.org.fgh.idartlite.service.episode.EpisodeService;
import mz.org.fgh.idartlite.service.episode.IEpisodeService;

public class RestClinicInfoService extends BaseRestService {

    private static final String TAG = "RestClinicInfoService";

    private static IClinicService clinicService ;
    private static IClinicInfoService episodeService ;

    public RestClinicInfoService(Application application, User currentUser) {
        super(application, currentUser);
        clinicService = new ClinicService(application,currentUser);
    }


    public static void restPostClinicInfo(ClinicInformation clinicInformation) {

        String url = BaseRestService.baseUrl + "/sync_temp_clinic_information";

        ClinicInfoService clinicInfoService = new ClinicInfoService(getApp(), null);
            getRestServiceExecutor().execute(() -> {
                RESTServiceHandler handler = new RESTServiceHandler();


                SyncClinicInformation syncClinicInformation = setSyncClinicInfo(clinicInformation);
                Gson g = new Gson();
                String restObject = g.toJson(syncClinicInformation);

                handler.addHeader("Content-Type", "application/json");
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(restObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                handler.objectRequest(url, Request.Method.POST, jsonObject, Object.class, new Response.Listener<Object>() {

                    @Override
                    public void onResponse(Object response) {
                        Log.d(TAG, "onResponse: Informacao Clinica enviado" + response);
                        try {
                            clinicInformation.setSyncStatus(BaseModel.SYNC_SATUS_SENT);
                            clinicInfoService.updateClinicInfo(clinicInformation);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d(TAG, "onErrorResponse: Erro no POST :" +generateErrorMsg(error));
                    }
                });


    });
    }

    private static SyncClinicInformation setSyncClinicInfo(ClinicInformation clinicInformation) {


        //episodeService = new EpisodeService(getApp(), null);
        SyncClinicInformation syncClinicInformation = new SyncClinicInformation();

        syncClinicInformation.setRegisterdate(clinicInformation.getRegisterDate());
        syncClinicInformation.setWeight(clinicInformation.getWeight());
        syncClinicInformation.setHeight(clinicInformation.getHeight());
        syncClinicInformation.setImc(clinicInformation.getImc());
        syncClinicInformation.setSystole(clinicInformation.getSystole());
        syncClinicInformation.setDistort(clinicInformation.getDistort());
        syncClinicInformation.setIstreatmenttb(clinicInformation.isTreatmentTB());
        syncClinicInformation.setIstreatmenttpi(clinicInformation.isTreatmentTPI());
        syncClinicInformation.setIscough(clinicInformation.isCough());
        syncClinicInformation.setIsfever(clinicInformation.isFever());
        syncClinicInformation.setIslostweight(clinicInformation.isLostWeight());
        syncClinicInformation.setIssweating(clinicInformation.isSweating());
        syncClinicInformation.setHasparenttbtreatment(clinicInformation.isHasParentTBTreatment());
        syncClinicInformation.setIsreferedtoustb(clinicInformation.isReferedToUsTB());
        syncClinicInformation.setHaspatientcamecorrectdate(clinicInformation.isHasPatientCameCorrectDate());
        syncClinicInformation.setLatedays(clinicInformation.getLateDays());
        syncClinicInformation.setPatientforgotmedicine(clinicInformation.isPatientForgotMedicine());
        syncClinicInformation.setDayswithoutmedicine(clinicInformation.getDaysWithoutMedicine());
        syncClinicInformation.setLatemotives(clinicInformation.getLateMotives());
        syncClinicInformation.setAdversereactionofmedicine(clinicInformation.isAdverseReactionOfMedicine());
        syncClinicInformation.setAdversereaction(clinicInformation.getAdverseReaction());
        syncClinicInformation.setIsreferedtousram(clinicInformation.isReferedToUsRAM());
        syncClinicInformation.setPatientuuid(clinicInformation.getPatient().getUuid());
        syncClinicInformation.setUuid(clinicInformation.getUuid());
        syncClinicInformation.setSyncstatus('S');
        syncClinicInformation.setId(clinicInformation.getId());
        syncClinicInformation.setHasfatigueortiredneslasttwoweeks(clinicInformation.isHasFatigueOrTirednesLastTwoWeeks());
        syncClinicInformation.setHashadmenstruationlasttwomonths(clinicInformation.isHasHadMenstruationLastTwoMonths());
        syncClinicInformation.setIspregnant(clinicInformation.isPregnant());

        if(clinicInformation.getStartTreatmentDate()!=null){
            syncClinicInformation.setStarttreatmentdate(clinicInformation.getStartTreatmentDate());
        }


        return syncClinicInformation;

    }



}
