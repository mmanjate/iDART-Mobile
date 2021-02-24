package mz.org.fgh.idartlite.rest.service.Episode;

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

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.rest.BaseRestService;
import mz.org.fgh.idartlite.base.rest.ServiceWatcher;
import mz.org.fgh.idartlite.listener.rest.RestResponseListener;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.SyncEpisode;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.helper.RESTServiceHandler;
import mz.org.fgh.idartlite.service.clinic.ClinicService;
import mz.org.fgh.idartlite.service.clinic.IClinicService;
import mz.org.fgh.idartlite.service.episode.EpisodeService;
import mz.org.fgh.idartlite.service.episode.IEpisodeService;

public class RestEpisodeService extends BaseRestService {

    private static final String TAG = "RestEpisodeService";

    private static IClinicService clinicService ;
    private static IEpisodeService episodeService ;

    public RestEpisodeService(Application application, User currentUser) {
        super(application, currentUser);
        clinicService = new ClinicService(application,currentUser);
    }


    public static void restPostEpisode(Episode episode) {

        String url = BaseRestService.baseUrl + "/sync_temp_episode";

        EpisodeService episodeService = new EpisodeService(getApp(), null);
            getRestServiceExecutor().execute(() -> {
                RESTServiceHandler handler = new RESTServiceHandler();

                Gson g = new Gson();
                String restObject = g.toJson(setSyncEpisode(episode));

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
                        Log.d(TAG, "onResponse: Episodio enviado" + response);
                        try {
                            episode.setSyncStatus(BaseModel.SYNC_SATUS_SENT);
                            episodeService.udpateEpisode(episode);
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

    private static SyncEpisode setSyncEpisode(Episode episode) {


        episodeService = new EpisodeService(getApp(), null);
        SyncEpisode syncEpisode = new SyncEpisode();
        Episode firstEpisode = null;
        try {
             firstEpisode = episodeService.getAllEpisodesByPatient(episode.getPatient()).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (episode.getStopReason()!= null && episode.getStopReason().length()>0)
        {
            syncEpisode.setStopDate(episode.getEpisodeDate());
            syncEpisode.setStopReason(episode.getStopReason());
            syncEpisode.setStopNotes(episode.getNotes());
            syncEpisode.setStartDate(firstEpisode.getEpisodeDate());

        }
        else {
            syncEpisode.setStartDate(episode.getEpisodeDate());
            syncEpisode.setStartReason(episode.getStopReason());
            syncEpisode.setStartNotes(episode.getNotes());
        }

        syncEpisode.setPatientuuid(episode.getPatient().getUuid());
        syncEpisode.setUsuuid(episode.getUsUuid());
        syncEpisode.setClinicuuid(episode.getPatient().getClinic().getUuid());
        syncEpisode.setSyncStatus('S');
        return syncEpisode;

    }

    public static void restGetAllReadyEpisodes()  {
        getAllReadyEpisodes(null, null);
    }

    public static void restGetAllReadyEpisodes(ServiceWatcher watcher)  {
        getAllReadyEpisodes(null, watcher);
    }

    public static void restGetAllReadyEpisodes(RestResponseListener listener)  {
        getAllReadyEpisodes(listener, null);
    }


    public static void getAllReadyEpisodes(RestResponseListener listener, ServiceWatcher watcher) {

        try {
            clinicService = new ClinicService(getApp(),null);
             episodeService = new EpisodeService(getApp(), null);
            Clinic clinic = clinicService.getAllClinics().get(0);

            String url = BaseRestService.baseUrl + "/sync_temp_episode?clinicuuid=eq."+clinic.getUuid()+"&syncstatus=eq.R";

            if (RESTServiceHandler.getServerStatus(BaseRestService.baseUrl)) {
                getRestServiceExecutor().execute(() -> {

                    RESTServiceHandler handler = new RESTServiceHandler();
                    handler.addHeader("Content-Type", "Application/json");

                    handler.objectRequest(url, Request.Method.GET, null, Object[].class, new Response.Listener<Object[]>() {
                        @Override
                        public void onResponse(Object[] episodes) {
                            Log.d("Response", String.valueOf(episodes.length));

                            if (episodes.length > 0) {
                                int counter = 0;

                                for (Object episode : episodes) {
                                    Log.i(TAG, "onResponse: " + episode);
                                    try {
                                        LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) episode;
                                        if(!episodeService.checkEpisodeExists(itemresult)){
                                            episodeService.saveOnEpisodeEnding(itemresult);
                                            counter++;
                                        }else{
                                            restPatchEpisode(itemresult);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }finally {
                                        continue;
                                    }
                                }
                                if (watcher != null && counter > 0) watcher.addUpdates(counter + " "+getApp().getString(R.string.new_episodes));
                            }else {
                                Log.w(TAG, "Response Sem Info." + episodes.length);
                            }


                            if(listener != null){
                                listener.doOnRestSucessResponse(null);
                            }
                        }

                    }, error -> Log.e("Response", generateErrorMsg(error)));
                });

            } else {
                Log.e(TAG,"Response Servidor Offline");
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }



    public static void restPatchEpisode( LinkedTreeMap<String, Object> episode) {

        Double myDouble = Double.valueOf((Double) episode.get("id"));
        int id= myDouble.intValue();
        String url = BaseRestService.baseUrl + "/sync_temp_episode?id=eq." + id ;

        EpisodeService episodeService = new EpisodeService(getApp(), null);
        getRestServiceExecutor().execute(() -> {
            RESTServiceHandler handler = new RESTServiceHandler();
            episode.put("syncstatus","U");
            Gson g = new Gson();
            String restObject = g.toJson(episode);

            handler.addHeader("Content-Type", "application/json");
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(restObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            handler.objectRequest(url, Request.Method.PATCH, jsonObject, Object.class, new Response.Listener<Object>() {

                @Override
                public void onResponse(Object response) {
                    Log.d(TAG, "onResponse: Episodio actualizado" + response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d(TAG, "onErrorResponse: Erro no Patch :" +generateErrorMsg(error));
                }
            });


        });
    }

    public static void restGetAllEpisodes()  {
        getAllEpisodes(null, null);
    }

    public static void restGetAllEpisodes(RestResponseListener listener)  {
        getAllEpisodes(listener, null);
    }

    public static void restGetAllEpisodes(ServiceWatcher watcher)  {
        getAllEpisodes(null, watcher);
    }

    public static void getAllEpisodes(RestResponseListener listener, ServiceWatcher watcher) {

        try {
            clinicService = new ClinicService(getApp(),null);
            episodeService = new EpisodeService(getApp(), null);
            Clinic clinic = clinicService.getAllClinics().get(0);

            String url = BaseRestService.baseUrl + "/sync_temp_episode?clinicuuid=eq."+clinic.getUuid();

            if (RESTServiceHandler.getServerStatus(BaseRestService.baseUrl)) {
                getRestServiceExecutor().execute(() -> {

                    RESTServiceHandler handler = new RESTServiceHandler();
                    handler.addHeader("Content-Type", "Application/json");

                    handler.objectRequest(url, Request.Method.GET, null, Object[].class, new Response.Listener<Object[]>() {
                        @Override
                        public void onResponse(Object[] episodes) {
                            Log.d("Response", String.valueOf(episodes.length));

                            if (episodes.length > 0) {
                                int counter = 0;
                                for (Object episode : episodes) {
                                    Log.i(TAG, "onResponse: " + episode);
                                    try {
                                        LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) episode;
                                        if(!episodeService.checkEpisodeExists(itemresult)){
                                            episodeService.saveOnEpisodeEnding(itemresult);
                                            counter++;
                                            Log.w(TAG, "Numero de Episodios Criados" + episodes.length);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }finally {
                                        continue;
                                    }
                                }
                                if (watcher != null && counter > 0) watcher.addUpdates(counter + " "+getApp().getString(R.string.new_episodes));

                            }else {
                                Log.w(TAG, "Response Sem Info." + episodes.length);
                            }

                            if(listener != null){
                                listener.doOnRestSucessResponse(null);
                            }
                        }

                    }, error -> Log.e("Response", generateErrorMsg(error)));
                });

            } else {
                Log.e(TAG,"Response Servidor Offline");
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }
}
