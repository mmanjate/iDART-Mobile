package mz.org.fgh.idartlite.viewmodel.home;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.view.home.HomeActivity;
import mz.org.fgh.idartlite.view.home.PatientHomeActivity;
import mz.org.fgh.idartlite.view.patientSearch.SearchPatientActivity;
import mz.org.fgh.idartlite.view.reports.ReportTypeActivity;
import mz.org.fgh.idartlite.view.stock.panel.StockActivity;

public class HomeVM extends BaseViewModel {

    public HomeVM(@NonNull Application application) {
        super(application);
    }

    @Override
    protected BaseModel initRecord() {
        return null;
    }

    @Override
    protected <T extends BaseService> Class<T> getRecordServiceClass() {
        return null;
    }

    @Override
    protected void initFormData() {

    }

    public void callHomePatient(){
        getRelatedActivity().nextActivityWithGenericParams(PatientHomeActivity.class);
    }

    public void callStck(){
        getRelatedActivity().nextActivityWithGenericParams(StockActivity.class);
    }

    public void callReports(){
        getRelatedActivity().nextActivityWithGenericParams(ReportTypeActivity.class);
    }

    @Override
    public HomeActivity getRelatedActivity(){
        return (HomeActivity) super.getRelatedActivity();
    }

    @Bindable
    public String getClinicName(){
        return getRelatedActivity().getCurrentClinic().getClinicName();
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
