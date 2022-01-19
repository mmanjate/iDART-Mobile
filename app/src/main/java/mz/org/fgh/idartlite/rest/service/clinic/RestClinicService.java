package mz.org.fgh.idartlite.rest.service.clinic;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.rest.BaseRestService;
import mz.org.fgh.idartlite.listener.rest.RestResponseListener;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.ClinicSector;
import mz.org.fgh.idartlite.model.ClinicSectorType;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.helper.RESTServiceHandler;
import mz.org.fgh.idartlite.service.clinic.ClinicSectorTypeService;
import mz.org.fgh.idartlite.service.clinic.ClinicService;
import mz.org.fgh.idartlite.service.clinic.IClinicSectorService;
import mz.org.fgh.idartlite.service.clinic.IClinicSectorTypeService;
import mz.org.fgh.idartlite.service.clinic.IClinicService;
import mz.org.fgh.idartlite.service.clinic.IPharmacyTypeService;
import mz.org.fgh.idartlite.service.clinic.PharmacyTypeService;
import mz.org.fgh.idartlite.service.user.IUserService;
import mz.org.fgh.idartlite.service.user.UserService;

public class RestClinicService extends BaseRestService {
    private static final String TAG = "RestClinicService";
    private static IClinicService clinicService;
    private static IUserService userService;
    private static IPharmacyTypeService pharmacyTypeService;
    private static IClinicSectorService clinicSectorService;

    // JNM_12.01.2022
    private static IClinicSectorTypeService clinicSectorTypeService;

    public RestClinicService(Application application, User currentUser) {
        super(application, currentUser);

        // clinicSectorService = (IClinicSectorService) getServiceFactory().get(ClinicSectorService.class);
    }

    //Carregamento de todas clinics no server central JNM_12.01.2022
    public List<Clinic> restGetAllClinic(RestResponseListener listener) throws SQLException {

        String url = BaseRestService.baseUrl + "/clinic?select=*,clinicsector(*)";
        // JNM_12.01.2022
        //String urlClinSecType = BaseRestService.baseUrl + "/clinic?select=*,clinicsectortype(*)";

        clinicService = (IClinicService) getServiceFactory().get(ClinicService.class);
        userService = (IUserService) getServiceFactory().get(UserService.class);
        pharmacyTypeService = (IPharmacyTypeService) getServiceFactory().get(PharmacyTypeService.class);
        // JNM_12.01.2022
        clinicSectorTypeService = (IClinicSectorTypeService) getServiceFactory().get(ClinicSectorTypeService.class);


        List<Clinic> clinicList = new ArrayList<>();

        // Verifica conexao com server. JNM_12.01.2022
        if (RESTServiceHandler.getServerStatus(BaseRestService.baseUrl)) {
            getRestServiceExecutor().execute(() -> {

                RESTServiceHandler handler = new RESTServiceHandler();
                handler.addHeader("Content-Type", "Application/json");

                // Buscar todas clinics no server
                handler.objectRequest(url, Request.Method.GET, null, Object[].class, new Response.Listener<Object[]>() {
                    @Override
                    public void onResponse(Object[] clinics) {
                        // Buscar todas clinicSectorTypes
                        List<ClinicSectorType> clinicSectorTypeList = null;
                        try {
                            clinicSectorTypeList = clinicSectorTypeService.getAllClinicSectorType();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        //FIM => Buscar todas clinicSectorTypes
                        if (clinics.length > 0) {
                            for (Object clinic : clinics) {
                                try {
                                    LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) clinic;

                                    Clinic clinicRest = new Clinic();
                                    clinicRest.setRestId((int) Float.parseFloat(Objects.requireNonNull(itemresult.get("id")).toString()));
                                    clinicRest.setCode(Objects.requireNonNull(itemresult.get("code")).toString());
                                    clinicRest.setClinicName(Objects.requireNonNull(itemresult.get("clinicname")).toString());
                                    clinicRest.setPharmacyType(pharmacyTypeService.getPharmacyTypeByCode(Objects.requireNonNull(itemresult.get("facilitytype")).toString()));
                                    if (clinicRest.getPharmacyType().getDescription().equalsIgnoreCase("Unidade Sanitária")) {// Se esta clinic é US
                                        List<Object> itemresult1 = (List<Object>) Objects.requireNonNull(itemresult.get("clinicsector")); // Pega a lista de sectores dessa US
                                        List<ClinicSector> clinicSectors = new ArrayList<>();
                                        for (Object clinicSector : itemresult1) {
                                            LinkedTreeMap<String, Object> itemresult2 = (LinkedTreeMap<String, Object>) clinicSector;
                                            ClinicSector clinicSector1 = new ClinicSector();
                                            clinicSector1.setSectorName(Objects.requireNonNull(itemresult2.get("sectorname")).toString());
                                            clinicSector1.setUuid(Objects.requireNonNull(itemresult2.get("uuid")).toString());
                                            clinicSector1.setCode(Objects.requireNonNull(itemresult2.get("code")).toString()); // Inicialmente era só paragem única este code, agora será um de 4 tipos

                                            // Atribuir Clinic a clinicSector ( Em substiuicao a linha comentada a baixo)
                                            clinicSector1.setClinic(clinicRest);
                                            // clinicSector1.setClinicId((int) Float.parseFloat(Objects.requireNonNull(itemresult2.get("clinic")).toString()));

                                            // Atribuir ClinicSectorType a clinicSector
                                            if (clinicSectorTypeList.size() > 0 && clinicSectorTypeList != null) {
                                                for (ClinicSectorType cst : clinicSectorTypeList) {
                                                    System.out.println(cst.getCode());
                                                    if (cst.getCode().equalsIgnoreCase(clinicSector1.getCode())) {
                                                        clinicSector1.setClinicSectorType(cst);
                                                    }
                                                }
                                            }

                                            clinicSectors.add(clinicSector1);
                                        }

                                        clinicRest.setClinicSectorList(clinicSectors);

                                    }


                                    clinicRest.setAddress(Objects.requireNonNull(itemresult.get("province")).toString().concat(Objects.requireNonNull(itemresult.get("district")).toString()));
                                    clinicRest.setPhone(Objects.requireNonNull(itemresult.get("telephone")).toString());
                                    clinicRest.setUuid(Objects.requireNonNull(itemresult.get("uuid")).toString());
                                    clinicList.add(clinicRest);

                                    Log.i(TAG, "onResponse: " + clinic);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                } finally {
                                    continue;
                                }
                            }
                            if (listener != null) listener.doOnRestSucessResponse("Success");
                        } else
                            Log.w(TAG, "Response Sem Info." + clinics.length);
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
            try {
                if (listener != null) {
                    String errorMsg = "";

                    if (userService.checkIfUsertableIsEmpty()) {
                        errorMsg = application.getString(R.string.error_msg_server_offline);
                    } else {
                        errorMsg = application.getString(R.string.error_msg_server_offline_records_wont_be_sync);
                    }
                    listener.doOnRestErrorResponse(errorMsg);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return clinicList;
    }
}
