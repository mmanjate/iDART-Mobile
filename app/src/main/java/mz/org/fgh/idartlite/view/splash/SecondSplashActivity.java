package mz.org.fgh.idartlite.view.splash;

import android.os.Bundle;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.listener.rest.RestResponseListener;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.rest.service.Patient.RestPatientService;
import mz.org.fgh.idartlite.rest.service.Stock.RestStockService;
import mz.org.fgh.idartlite.service.clinic.ClinicService;
import mz.org.fgh.idartlite.view.home.HomeActivity;
import mz.org.fgh.idartlite.workSchedule.executor.WorkerScheduleExecutor;

public class SecondSplashActivity extends BaseActivity implements RestResponseListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_splash);

        WorkerScheduleExecutor workerScheduleExecutor = new WorkerScheduleExecutor(getApplicationContext());
        workerScheduleExecutor.initConfigTaskWork();
        workerScheduleExecutor.initStockTaskWork();
        workerScheduleExecutor.initDataTaskWork();
        workerScheduleExecutor.initPatchStockDataTaskWork();
        workerScheduleExecutor.initPostPatientDataTaskWork();
        workerScheduleExecutor.initPostStockDataTaskWork();
        workerScheduleExecutor.initPatientDispenseTaskWork();
        workerScheduleExecutor.initEpisodeTaskWork();
        workerScheduleExecutor.initPostNewPatientDataTaskWork();



        new Thread(new Runnable() {
            public void run() {
                RestPatientService.restGetAllPatient(SecondSplashActivity.this);
                try {
                    RestStockService.restGetStock(getCurrentClinic());
                    RestStockService.restPostStockCenter(getCurrentClinic());
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    @Override
    public BaseViewModel initViewModel() {
        return null;
    }

    @Override
    public void doOnRestSucessResponse(String flag) {

        ClinicService clinicService = new ClinicService(getApplication(),getCurrentUser());
        Clinic localClinic = null;
        try {
            localClinic = clinicService.getAllClinics().get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Map<String, Object> params = new HashMap<>();
        params.put("user", getCurrentUser());
        params.put("clinic", localClinic);
        nextActivity(HomeActivity.class, params);
        finish();
    }

    @Override
    public void doOnRestErrorResponse(String errormsg) {

    }
}