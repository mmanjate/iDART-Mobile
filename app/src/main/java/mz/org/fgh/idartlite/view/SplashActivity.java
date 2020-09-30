package mz.org.fgh.idartlite.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.collection.ArrayMap;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.TaskSchedule.restTaskSchedule.ExecuteGetWorkerScheduler;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.rest.RESTServiceHandler;
import mz.org.fgh.idartlite.service.ClinicService;
import mz.org.fgh.idartlite.service.PharmacyTypeService;
import mz.org.fgh.idartlite.service.restService.RestClinicService;
import mz.org.fgh.idartlite.util.Utilities;

import static mz.org.fgh.idartlite.base.BaseService.getRestServiceExecutor;

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