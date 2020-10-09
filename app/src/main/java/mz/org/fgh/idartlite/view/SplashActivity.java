package mz.org.fgh.idartlite.view;

import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.TaskSchedule.restTaskSchedule.ExecuteWorkerScheduler;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.base.RestResponseListener;
import mz.org.fgh.idartlite.common.DialogListener;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.service.PharmacyTypeService;
import mz.org.fgh.idartlite.service.restService.RestClinicService;
import mz.org.fgh.idartlite.service.restService.RestDiseaseTypeService;
import mz.org.fgh.idartlite.service.restService.RestDispenseTypeService;
import mz.org.fgh.idartlite.service.restService.RestDrugService;
import mz.org.fgh.idartlite.service.restService.RestFormService;
import mz.org.fgh.idartlite.service.restService.RestPharmacyTypeService;
import mz.org.fgh.idartlite.service.restService.RestTherapeuticLineService;
import mz.org.fgh.idartlite.service.restService.RestTherapeuticRegimenService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.SplashVM;

public class SplashActivity extends BaseActivity implements RestResponseListener, DialogListener {

    private static final String TAG = "SplashActivity";

    private RestClinicService restClinicService;
    private PharmacyTypeService pharmacyTypeService;

    private List<Clinic> clinicList = new ArrayList<Clinic>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        restClinicService = new RestClinicService(getApplication(), null);
        pharmacyTypeService = new PharmacyTypeService(getApplication(), null);

        new Thread(new Runnable() {
            public void run() {

                RestPharmacyTypeService.restGetAllPharmacyType();
                RestFormService.restGetAllForms();
                RestDrugService.restGetAllDrugs();
                RestDiseaseTypeService.restGetAllDiseaseType();
                RestDispenseTypeService.restGetAllDispenseType();
                RestTherapeuticRegimenService.restGetAllTherapeuticRegimen();
                RestTherapeuticLineService.restGetAllTherapeuticLine();

                clinicList = restClinicService.restGetAllClinic(SplashActivity.this);
                while (!Utilities.listHasElements(clinicList)) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Map<String, Object> params = new HashMap<>();
                params.put("clinicList", clinicList);
                nextActivity(LoginActivity.class, params);
                finish();
            }
        }).start();
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
        if (getRelatedViewModel().appHasUsersOnDB()){
            finishAffinity();
            System.exit(0);
        }else {
            nextActivity(LoginActivity.class);
            finish();
        }

    }

    @Override
    public void doOnDeny() {

    }
}