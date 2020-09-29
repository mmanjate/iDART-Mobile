package mz.org.fgh.idartlite.view;

import android.os.Bundle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.service.restService.RestClinicService;
import mz.org.fgh.idartlite.util.Utilities;

public class SplashActivity extends BaseActivity {
    private RestClinicService restClinicService;

    private List<Clinic> clinicList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        restClinicService = new RestClinicService(getApplication(), null);

        clinicList = restClinicService.restGetAllClinic();

        while (!Utilities.listHasElements(clinicList)){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Map<String, Object> params = new HashMap<>();
        params.put("clinicList", clinicList);
        nextActivity(LoginActivity.class, params);

    }

    @Override
    public BaseViewModel initViewModel() {
        return null;
    }

}