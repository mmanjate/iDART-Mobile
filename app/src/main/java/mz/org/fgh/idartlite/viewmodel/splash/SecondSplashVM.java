package mz.org.fgh.idartlite.viewmodel.splash;

import static mz.org.fgh.idartlite.base.application.IdartLiteApplication.CHANNEL_1_ID;
import static mz.org.fgh.idartlite.base.application.IdartLiteApplication.CHANNEL_2_ID;
import static mz.org.fgh.idartlite.base.application.IdartLiteApplication.CHANNEL_3_ID;
import static mz.org.fgh.idartlite.view.home.ui.settings.AppSettingsFragment.DOWNLOAD_MESSAGE_STATUS;
import static mz.org.fgh.idartlite.view.home.ui.settings.AppSettingsFragment.REMOVAL_MESSAGE_STATUS;
import static mz.org.fgh.idartlite.view.home.ui.settings.AppSettingsFragment.UPLOAD_MESSAGE_STATUS;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.sql.SQLException;
import java.util.Arrays;
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
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.home.IDartHomeActivity;
import mz.org.fgh.idartlite.view.splash.SecondSplashActivity;
import mz.org.fgh.idartlite.workSchedule.executor.WorkerScheduleExecutor;
import mz.org.fgh.idartlite.workSchedule.work.DataSyncWorker;
import mz.org.fgh.idartlite.workSchedule.work.get.PatientWorker;
import mz.org.fgh.idartlite.workSchedule.work.get.StockWorker;

public class SecondSplashVM extends BaseViewModel{

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
            getFirstData();
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

    public void goHome() {

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
        WorkManager workManager;
        OneTimeWorkRequest patientOneTimeWorkRequest = new OneTimeWorkRequest.Builder(PatientWorker.class).build();
        OneTimeWorkRequest stockOneTimeWorkRequest = new OneTimeWorkRequest.Builder(StockWorker.class).build();
        workManager = WorkManager.getInstance(getApplication());
        workManager.enqueue(Arrays.asList(stockOneTimeWorkRequest,patientOneTimeWorkRequest));
     //   goHome();
        observeRunningSync(workManager, patientOneTimeWorkRequest);

        try {
           RestStockService.restPostStockCenter(getCurrentClinic());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void observeRunningSync(WorkManager workManager, OneTimeWorkRequest mRequest){
        workManager.getWorkInfoByIdLiveData(mRequest.getId()).observe(getRelatedActivity(), workInfo -> {
            if (workInfo != null) {
                if (workInfo.getState() == WorkInfo.State.SUCCEEDED){
                    WorkInfo.State state = workInfo.getState();
                    goHome();
                }
            }
        });
    }
}
