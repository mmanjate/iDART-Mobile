package mz.org.fgh.idartlite.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.TaskSchedule.restTaskSchedule.ExecuteGetWorkerScheduler;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.service.PharmacyTypeService;
import mz.org.fgh.idartlite.service.restService.RestClinicService;
import mz.org.fgh.idartlite.util.Utilities;

public class SplashActivity extends BaseActivity {

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

        ExecuteGetWorkerScheduler executeGetWorkerScheduler = new ExecuteGetWorkerScheduler(getApplicationContext());
        executeGetWorkerScheduler.initConfigTaskWork();

        WorkManager.getInstance(getApplicationContext()).getWorkInfosByTagLiveData("TASK ID " + ExecuteGetWorkerScheduler.JOB_ID).observe(this, new Observer<List<WorkInfo>>() {
            @Override
            public void onChanged(List<WorkInfo> workInfos) {
                for (WorkInfo workInfo : workInfos){
                    if (workInfo.getState().isFinished()) {
                        break;
                    }

                }

            }
        });

        new Thread(new Runnable() {
            public void run() {
                // do here some long operation
                clinicList = restClinicService.restGetAllClinic();

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

//                startActivityForResult(new Intent(SplashActivity.this, LoginActivity.class), 0);
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
        return null;
    }

}