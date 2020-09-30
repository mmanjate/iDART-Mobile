package mz.org.fgh.idartlite.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.TaskSchedule.restTaskSchedule.ExecuteGetWorkerScheduler;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.base.RestResponseListener;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.service.ClinicService;
import mz.org.fgh.idartlite.service.UserService;
import mz.org.fgh.idartlite.service.restService.RestDiseaseTypeService;
import mz.org.fgh.idartlite.service.restService.RestDispenseTypeService;
import mz.org.fgh.idartlite.service.restService.RestDrugService;
import mz.org.fgh.idartlite.service.restService.RestFormService;
import mz.org.fgh.idartlite.service.restService.RestPatientService;
import mz.org.fgh.idartlite.service.restService.RestPharmacyTypeService;
import mz.org.fgh.idartlite.service.restService.RestTherapeuticLineService;
import mz.org.fgh.idartlite.service.restService.RestTherapeuticRegimenService;
import mz.org.fgh.idartlite.util.Utilities;

public class SecondSplashActivity extends BaseActivity implements RestResponseListener {

    private ClinicService clinicService;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_splash);

        ExecuteGetWorkerScheduler executeGetWorkerScheduler = new ExecuteGetWorkerScheduler(getApplicationContext());
        executeGetWorkerScheduler.initConfigTaskWork();
        executeGetWorkerScheduler.initDataTaskWork();

        new Thread(new Runnable() {
            public void run() {

                RestPatientService.restGetAllPatient(SecondSplashActivity.this);

            }
        }).start();

    }

    @Override
    public BaseViewModel initViewModel() {
        return null;
    }

    @Override
    public void doOnRestSucessResponse(String flag) {
        nextActivity(HomeActivity.class);
    }

    @Override
    public void doOnRestErrorResponse(String errormsg) {

    }
}