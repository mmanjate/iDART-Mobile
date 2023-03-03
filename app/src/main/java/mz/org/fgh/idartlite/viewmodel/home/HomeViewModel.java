package mz.org.fgh.idartlite.viewmodel.home;

import static mz.org.fgh.idartlite.workSchedule.executor.WorkerScheduleExecutor.JOB_ID;
import static mz.org.fgh.idartlite.workSchedule.executor.WorkerScheduleExecutor.ONE_TIME_REQUEST_JOB_ID;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.work.WorkManager;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.home.IDartHomeActivity;
import mz.org.fgh.idartlite.view.home.PatientHomeActivity;
import mz.org.fgh.idartlite.view.home.ui.home.HomeFragment;
import mz.org.fgh.idartlite.view.reports.ReportTypeActivity;
import mz.org.fgh.idartlite.view.stock.panel.StockActivity;
import mz.org.fgh.idartlite.workSchedule.executor.WorkerScheduleExecutor;

public class HomeViewModel extends BaseViewModel {


    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected IBaseService initRelatedService() {
        return null;
    }

    @Override
    protected BaseModel initRecord() {
        if ( getCurrentClinicSector()!=null &&  getCurrentClinicSector().getClinicSectorType().getCode().contains("PROVEDOR")) {
            if (!Utilities.isWorkScheduled("ONE_TIME_CHECK_LOADING_ID" + ONE_TIME_REQUEST_JOB_ID, WorkManager.getInstance(getApplication().getApplicationContext())) && !Utilities.isWorkRunning("ONE_TIME_CHECK_LOADING_ID " + JOB_ID, WorkManager.getInstance(getApplication().getApplicationContext()))) {
                this.workerScheduleExecutor = WorkerScheduleExecutor.getInstance(getApplication().getApplicationContext(), getCurrentClinic(), getSystemSettings());
                this.workerScheduleExecutor.initCheckLoadingTaskWork();
            }
        }
        return null;
    }

    @Override
    protected void initFormData() {

    }

    @Override
    public void preInit() {

    }

    public void callHomePatient(){
        getMyActivity().nextActivityWithGenericParams(PatientHomeActivity.class);
    }

    public IDartHomeActivity getMyActivity(){
        return (IDartHomeActivity) getRelatedFragment().getActivity();
    }

    public void callStck(){
        getMyActivity().nextActivityWithGenericParams(StockActivity.class);
    }

    public void callReports(){
        getMyActivity().nextActivityWithGenericParams(ReportTypeActivity.class);
    }

    @Override
    public HomeFragment getRelatedFragment() {
        return (HomeFragment) super.getRelatedFragment();
    }

    @Bindable
    public String getClinicName(){
        return getRelatedActivity().getCurrentClinic().getClinicName();
    }
    @Bindable
    public String getClinicSectorType(){
        return getRelatedActivity().getCurrentClinicSector().getClinicSectorType().getCode();
    }

    @Bindable
    public String getPhone(){
        return  getRelatedActivity().getCurrentClinic().getPhone();
    }

    @Bindable
    public String getAddress(){
        return getRelatedActivity().getCurrentClinic().getAddress();
    }

    public void endSession(){
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("mz.org.fgh.idartlite.ACTION_LOGOUT");
        getRelatedActivity().sendBroadcast(broadcastIntent);
    }
}