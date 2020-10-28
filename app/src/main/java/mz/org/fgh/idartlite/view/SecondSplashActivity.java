package mz.org.fgh.idartlite.view;

import android.os.Bundle;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.TaskSchedule.restTaskSchedule.ExecuteWorkerScheduler;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.base.RestResponseListener;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.service.ClinicService;
import mz.org.fgh.idartlite.service.UserService;
import mz.org.fgh.idartlite.service.restService.RestPatientService;
import mz.org.fgh.idartlite.service.restService.RestStockService;
import mz.org.fgh.idartlite.service.restService.RestTherapeuticRegimenService;

public class SecondSplashActivity extends BaseActivity implements RestResponseListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_splash);

        ExecuteWorkerScheduler executeWorkerScheduler = new ExecuteWorkerScheduler(getApplicationContext());
        executeWorkerScheduler.initConfigTaskWork();
        executeWorkerScheduler.initStockTaskWork();
        executeWorkerScheduler.initDataTaskWork();
        executeWorkerScheduler.initPatchStockDataTaskWork();
        executeWorkerScheduler.initPostPatientDataTaskWork();
        executeWorkerScheduler.initPostStockDataTaskWork();
        executeWorkerScheduler.initPatientDispenseTaskWork();
        executeWorkerScheduler.initEpisodeTaskWork();



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
            localClinic = clinicService.getCLinic().get(0);
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