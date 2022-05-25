package mz.org.fgh.idartlite.viewmodel.splash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.model.AppSettings;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.service.clinic.ClinicService;
import mz.org.fgh.idartlite.service.settings.AppSettingsService;
import mz.org.fgh.idartlite.view.home.IDartHomeActivity;
import mz.org.fgh.idartlite.view.splash.SecondSplashActivity;
import mz.org.fgh.idartlite.workSchedule.executor.WorkerScheduleExecutor;

public class SecondSplashVM extends BaseViewModel{

    private AppSettingsService settingsService;
    private List<AppSettings> appSettings;

    private Clinic clinic;

    public SecondSplashVM(@NonNull Application application) {
        super(application);
    }


    @Override
    protected IBaseService initRelatedService() {
        //settingsService = new AppSettingsService(getApplication());
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
        getFirstData();
    }

    @Override
    public SecondSplashActivity getRelatedActivity() {
        return (SecondSplashActivity) super.getRelatedActivity();
    }


    public void goHome() {
        Map<String, Object> params = new HashMap<>();
        params.put("user", getCurrentUser());
        params.put("clinic", this.clinic);
        params.put("clinicSector", getCurrentClinicSector());
        getRelatedActivity().nextActivityFinishingCurrent(IDartHomeActivity.class, params);

    }

    private void getFirstData() {
        ClinicService clinicService = new ClinicService(getApplication(),getCurrentUser());
        Clinic localClinic = null;
        try {
            localClinic = clinicService.getAllClinics().get(0);
            this.clinic = localClinic;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.initWorkScheduleExecutor(getRelatedActivity().getApplicationContext(), this.clinic, this.appSettings);
        this.workerScheduleExecutor.runPatinetAndStockSync(true);
        goHome();
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
