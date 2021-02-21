package mz.org.fgh.idartlite.viewmodel.splash;

import android.app.Application;

import androidx.annotation.NonNull;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.listener.rest.RestResponseListener;
import mz.org.fgh.idartlite.model.AppSettings;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.rest.service.Patient.RestPatientService;
import mz.org.fgh.idartlite.rest.service.Stock.RestStockService;
import mz.org.fgh.idartlite.service.clinic.ClinicService;
import mz.org.fgh.idartlite.service.settings.AppSettingsService;
import mz.org.fgh.idartlite.view.home.IDartHomeActivity;
import mz.org.fgh.idartlite.view.splash.SecondSplashActivity;
import mz.org.fgh.idartlite.workSchedule.executor.WorkerScheduleExecutor;

public class SecondSplashVM extends BaseViewModel implements RestResponseListener<Clinic> {

    private AppSettingsService settingsService;
    private List<AppSettings> appSettings;

    private WorkerScheduleExecutor workerScheduleExecutor;

    public SecondSplashVM(@NonNull Application application) {
        super(application);
    }


    @Override
    protected IBaseService initRelatedService() {
        settingsService = new AppSettingsService(getApplication());
        return null;
    }

    @Override
    protected BaseModel initRecord() {
        return null;
    }

    @Override
    protected void initFormData() {

    }

    @Override
    public void preInit() {

        try {
            this.appSettings = settingsService.getAll();

            scheduleSyncWorks();

            new Thread(() -> getFirstData()).start();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SecondSplashActivity getRelatedActivity() {
        return (SecondSplashActivity) super.getRelatedActivity();
    }

    private void scheduleSyncWorks() {
        workerScheduleExecutor = new WorkerScheduleExecutor(getRelatedActivity().getApplicationContext(), this.appSettings);
        workerScheduleExecutor.initConfigTaskWork();
        workerScheduleExecutor.initStockTaskWork();
        workerScheduleExecutor.initDataTaskWork();
        workerScheduleExecutor.initPatchStockDataTaskWork();
        workerScheduleExecutor.initPostPatientDataTaskWork();
        workerScheduleExecutor.initPostStockDataTaskWork();
        workerScheduleExecutor.initPatientDispenseTaskWork();
        workerScheduleExecutor.initEpisodeTaskWork();
        workerScheduleExecutor.initPostNewPatientDataTaskWork();
    }


    @Override
    public void doOnRestErrorResponse(String errormsg) {

    }

    @Override
    public void doOnRestSucessResponseObject(String flag, Clinic object) {

    }

    @Override
    public void doOnRestSucessResponseObjects(String flag, List<Clinic> objects) {

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
        getRelatedActivity().nextActivityFinishingCurrent(IDartHomeActivity.class, params);

    }

    private void getFirstData() {
        RestPatientService.restGetAllPatient(SecondSplashVM.this);
        try {
            RestStockService.restGetStock(getCurrentClinic());
            RestStockService.restPostStockCenter(getCurrentClinic());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
