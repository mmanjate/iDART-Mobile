package mz.org.fgh.idartlite.viewmodel.report;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.view.reports.ReportTypeActivity;
import mz.org.fgh.idartlite.view.reports.ReportsListingActivity;

public class ReportTypeVM extends BaseViewModel {

    public ReportTypeVM(@NonNull Application application) {
        super(application);
    }

    @Override
    protected IBaseService initRelatedService() {
        return null;
    }

    @Override
    protected BaseModel initRecord() {
        return null;
    }

    @Override
    protected void initFormData() {

    }

    public void callReports(){
        Intent intent = new Intent(getApplication(), ReportsListingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", getRelatedActivity().getCurrentUser());
        bundle.putSerializable("clinic", getRelatedActivity().getCurrentClinic());
        intent.putExtras(bundle);
        getRelatedActivity().startActivity(intent);
    }

    @Override
    public ReportTypeActivity getRelatedActivity(){
        return (ReportTypeActivity) super.getRelatedActivity();
    }

    @Override
    public void preInit() {

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

    public void changeInitialDataViewStatus(View view){
        getRelatedActivity().changeFormSectionVisibility(view);
    }
}
