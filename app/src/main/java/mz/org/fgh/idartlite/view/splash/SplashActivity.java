package mz.org.fgh.idartlite.view.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.rest.BaseRestService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.listener.dialog.IDialogListener;
import mz.org.fgh.idartlite.listener.rest.RestResponseListener;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.rest.helper.RESTServiceHandler;
import mz.org.fgh.idartlite.rest.service.RestClinicService;
import mz.org.fgh.idartlite.rest.service.RestDiseaseTypeService;
import mz.org.fgh.idartlite.rest.service.RestDispenseTypeService;
import mz.org.fgh.idartlite.rest.service.RestDrugService;
import mz.org.fgh.idartlite.rest.service.RestFormService;
import mz.org.fgh.idartlite.rest.service.RestPharmacyTypeService;
import mz.org.fgh.idartlite.rest.service.RestTherapeuticLineService;
import mz.org.fgh.idartlite.rest.service.RestTherapeuticRegimenService;
import mz.org.fgh.idartlite.service.clinic.IPharmacyTypeService;
import mz.org.fgh.idartlite.service.clinic.PharmacyTypeService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.login.LoginActivity;
import mz.org.fgh.idartlite.viewmodel.splash.SplashVM;

public class SplashActivity extends BaseActivity implements RestResponseListener, IDialogListener {

    private static final String TAG = "SplashActivity";

    private RestClinicService restClinicService;
    private IPharmacyTypeService pharmacyTypeService;

    private List<Clinic> clinicList = new ArrayList<Clinic>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        restClinicService = new RestClinicService(getApplication(), null);
        pharmacyTypeService = new PharmacyTypeService(getApplication(), null);

        if (RESTServiceHandler.getServerStatus(BaseRestService.baseUrl)) {
            new Thread(new Runnable() {
                public void run() {
                    RestPharmacyTypeService.restGetAllPharmacyType();

                    RestDiseaseTypeService.restGetAllDiseaseType();

                    RestFormService.restGetAllForms();

                    try {
                        long timeOut = 60000;
                        long requestTime = 0;
                        while (!Utilities.listHasElements(getRelatedViewModel().getAllDiseaseTypes())) {
                            Thread.sleep(2000);
                            requestTime += 2000;
                            if (requestTime >= timeOut) {
                            /*runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Utilities.displayAlertDialog(SplashActivity.this, getString(R.string.time_out_msg)).show();
                                }
                            });*/
                                break;
                            }
                        }

                        RestDrugService.restGetAllDrugs();


                        requestTime = 0;
                        while (!Utilities.listHasElements(getRelatedViewModel().getAllDrugs())) {
                            Thread.sleep(4000);
                            requestTime += 4000;
                            if (requestTime >= timeOut) {
                            /*runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Utilities.displayAlertDialog(SplashActivity.this, getString(R.string.time_out_msg)).show();
                                }
                            });*/
                                break;
                            }
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    RestDispenseTypeService.restGetAllDispenseType();
                    RestTherapeuticRegimenService.restGetAllTherapeuticRegimen();
                    RestTherapeuticLineService.restGetAllTherapeuticLine();

                    clinicList = restClinicService.restGetAllClinic(SplashActivity.this);
                    long timeOut = 60000;
                    long requestTime = 0;
                    while (!Utilities.listHasElements(clinicList)) {
                        try {
                            Thread.sleep(2000);
                            requestTime += 2000;
                            if (requestTime >= timeOut) {
                            /*runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Utilities.displayAlertDialog(SplashActivity.this, getString(R.string.time_out_msg)).show();
                                }
                            });*/
                                break;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    Map<String, Object> params = new HashMap<>();
                    params.put("clinicList", clinicList);
                    nextActivityFinishingCurrent(LoginActivity.class, params);
                }
            }).start();
        }else {
            String errorMsg = "";

            if (getRelatedViewModel().appHasNoUsersOnDB()){
                errorMsg = getString(R.string.error_msg_server_offline);
            }else {
                errorMsg = getString(R.string.error_msg_server_offline_records_wont_be_sync);
            }
            Utilities.displayAlertDialog(SplashActivity.this, errorMsg, SplashActivity.this).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(SplashVM.class);
    }

    @Override
    public SplashVM getRelatedViewModel() {
        return (SplashVM) super.getRelatedViewModel();
    }

    @Override
    public void doOnRestSucessResponse(String flag) {
        if (Utilities.stringHasValue(flag)){
            long timeOut = 60000;
            long requestTime = 0;
            while (!Utilities.listHasElements(getRelatedViewModel().getAllPharmacyTypes())){
                try {
                    Thread.sleep(2000);
                    requestTime += 2000;
                    if (requestTime >= timeOut){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Utilities.displayAlertDialog(SplashActivity.this, getString(R.string.time_out_msg)).show();
                            }
                        });
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void doOnRestErrorResponse(String errormsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utilities.displayAlertDialog(SplashActivity.this, errormsg, SplashActivity.this).show();
            }
        });
    }

    @Override
    public void doOnConfirmed() {
        if (getRelatedViewModel().appHasNoUsersOnDB()){
            finishAffinity();
            System.exit(0);
        }else {
            nextActivityFinishingCurrent(LoginActivity.class);
        }
    }

    @Override
    public void doOnDeny() {}
}