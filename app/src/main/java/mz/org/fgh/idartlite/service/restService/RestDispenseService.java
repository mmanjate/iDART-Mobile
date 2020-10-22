package mz.org.fgh.idartlite.service.restService;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.SyncDispense;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.RESTServiceHandler;
import mz.org.fgh.idartlite.service.DispenseDrugService;
import mz.org.fgh.idartlite.service.DispenseService;
import mz.org.fgh.idartlite.service.EpisodeService;

public class RestDispenseService extends BaseService {

    private static final String TAG = "RestDispenseService";
    private static DispenseService dispenseService;
    private static DispenseDrugService dispenseDrugService;
    private static EpisodeService episodeService;
    private static List<Dispense> dispenseList;
    private static List<DispensedDrug> dispensedDrugList;

    public RestDispenseService(Application application, User currentUser) {
        super(application, currentUser);
        dispenseService = new DispenseService(application, currentUser);
    }

    public static void restPostDispense(Dispense dispense) {

        String url = BaseService.baseUrl + "/sync_temp_dispense";

        dispenseService = new DispenseService(getApp(), null);
        dispenseDrugService = new DispenseDrugService(getApp(), null);

        try {
            getRestServiceExecutor().execute(() -> {

                RESTServiceHandler handler = new RESTServiceHandler();

                try {
                    dispensedDrugList = dispenseDrugService.findDispensedDrugByDispenseId(dispense.getId());

                    for (DispensedDrug dispensedDrug : dispensedDrugList) {

                        SyncDispense syncDispense = setSyncDispense(dispense, dispensedDrug);

                        Gson g = new Gson();
                        String restObject = g.toJson(syncDispense);

                        handler.addHeader("Content-Type", "application/json");
                        JSONObject jsonObject = new JSONObject(restObject);

                        handler.objectRequest(url, Request.Method.POST, jsonObject, Object.class, new Response.Listener<Object>() {

                            @Override
                            public void onResponse(Object response) {
                                Log.d(TAG, "onResponse: Dispensa enviada" + response);
                                try {
                                    dispense.setSyncStatus(BaseModel.SYNC_SATUS_SENT);
                                    dispenseService.udpateDispense(dispense);
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
                    }
                } catch (SQLException | JSONException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static SyncDispense setSyncDispense(Dispense dispense, DispensedDrug dispensedDrug) {

        episodeService = new EpisodeService(getApp(), null);
        SyncDispense syncDispense = new SyncDispense();
        try {
            Episode episode = episodeService.getAllEpisodesByPatient(dispense.getPrescription().getPatient()).get(0);
            syncDispense.setDate(dispense.getPickupDate());
            syncDispense.setClinicalstage(0);
            syncDispense.setCurrent('T');
            syncDispense.setReasonforupdate("Manter");
            syncDispense.setDuration(dispense.getPrescription().getSupply());
            syncDispense.setDoctor(1);
            syncDispense.setEnddate(null);
            syncDispense.setRegimenome(dispense.getPrescription().getTherapeuticRegimen().getDescription());
            syncDispense.setLinhanome(dispense.getPrescription().getTherapeuticLine().getDescription());
            syncDispense.setNotes("Mobile Pharmacy");
            syncDispense.setMotivomudanca("");
            syncDispense.setWeight(null);
            syncDispense.setAf(Character.toUpperCase('f'));
            syncDispense.setCa(Character.toUpperCase('f'));
            syncDispense.setCcr(Character.toUpperCase('f'));
            syncDispense.setPpe(Character.toUpperCase('f'));
            syncDispense.setPtv(Character.toUpperCase('f'));
            syncDispense.setTb(Character.toUpperCase('f'));
            syncDispense.setFr(Character.toUpperCase('f'));
            syncDispense.setGaac(Character.toUpperCase('f'));
            syncDispense.setSaaj(Character.toUpperCase('f'));
            syncDispense.setTpc(Character.toUpperCase('f'));
            syncDispense.setTpi(Character.toUpperCase('f'));
            syncDispense.setPatient(dispense.getPrescription().getPatient().getId());

            syncDispense.setDispensatrimestral(0);
            syncDispense.setTipodt(null);
            syncDispense.setTipods(null);
            syncDispense.setDispensasemestral(0);

            syncDispense.setDrugtypes("ARV");
            syncDispense.setDatainicionoutroservico(null);
            syncDispense.setModified('T');
            syncDispense.setPatientid(dispense.getPrescription().getPatient().getNid());
            syncDispense.setPatientfirstname(dispense.getPrescription().getPatient().getFirstName());
            syncDispense.setPatientlastname(dispense.getPrescription().getPatient().getLastName());
            syncDispense.setPickupdate(dispense.getPickupDate());
            syncDispense.setQtyinhand("(" + dispensedDrug.getQuantitySupplied() + ")");
            syncDispense.setQtyinlastbatch("(" + dispensedDrug.getQuantitySupplied() + ")");
            syncDispense.setSummaryqtyinhand("(" + dispensedDrug.getQuantitySupplied() + ")");
            syncDispense.setTimesperday(1);
            syncDispense.setWeekssupply(dispense.getSupply());
            syncDispense.setExpirydate(dispense.getNextPickupDate());

            syncDispense.setDateexpectedstring(BaseService.getStringDateFromDate(dispense.getNextPickupDate(), "dd MMM yyyy"));
            syncDispense.setDrugname(dispensedDrug.getStock().getDrug().getDescription());
            syncDispense.setDispensedate(dispense.getPickupDate());

            syncDispense.setMainclinic(0);
            syncDispense.setMainclinicname(episode.getSanitaryUnit());
            syncDispense.setMainclinicuuid(episode.getUsUuid());

            syncDispense.setSyncstatus('P');
            syncDispense.setPrescriptionid(String.valueOf(dispense.getPrescription().getId()));

            syncDispense.setDurationsentence("");
            syncDispense.setDc(Character.toUpperCase('T'));
            syncDispense.setPrep(Character.toUpperCase('f'));
            syncDispense.setCe(Character.toUpperCase('f'));
            syncDispense.setCpn(Character.toUpperCase('f'));
            syncDispense.setPrescricaoespecial(Character.toUpperCase('f'));
            syncDispense.setMotivocriacaoespecial("");
            syncDispense.setUuidopenmrs(dispense.getPrescription().getPatient().getUuid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return syncDispense;

    }
}