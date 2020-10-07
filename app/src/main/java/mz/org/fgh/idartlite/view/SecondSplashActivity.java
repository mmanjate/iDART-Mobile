package mz.org.fgh.idartlite.view;

import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.TaskSchedule.restTaskSchedule.ExecuteGetWorkerScheduler;
import mz.org.fgh.idartlite.TaskSchedule.restTaskSchedule.ExecutePostWorkerScheduler;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.base.RestResponseListener;
import mz.org.fgh.idartlite.service.ClinicService;
import mz.org.fgh.idartlite.service.UserService;
import mz.org.fgh.idartlite.service.restService.RestPatientService;
import mz.org.fgh.idartlite.service.restService.RestStockService;

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

        ExecutePostWorkerScheduler executePostWorkerScheduler = new ExecutePostWorkerScheduler(getApplicationContext());
        executePostWorkerScheduler.initPostPatientDataTaskWork();
        executePostWorkerScheduler.initPostStockDataTaskWork();

        new Thread(new Runnable() {
            public void run() {
                RestStockService.restPostStockCenter(getCurrentClinic());
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
        Map<String, Object> params = new HashMap<>();
        params.put("user", getCurrentUser());
        params.put("clinic", getCurrentClinic());
        nextActivity(HomeActivity.class, params);
        finish();
    }

    @Override
    public void doOnRestErrorResponse(String errormsg) {

    }
}