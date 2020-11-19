package mz.org.fgh.idartlite.viewmodel.home;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.common.ApplicationStep;
import mz.org.fgh.idartlite.view.home.PatientHomeActivity;
import mz.org.fgh.idartlite.view.patientPanel.AddNewPatientActivity;
import mz.org.fgh.idartlite.view.patientSearch.SearchPatientActivity;
import mz.org.fgh.idartlite.view.reports.ReportTypeActivity;

public class PatientHomeVM extends BaseViewModel {

    public PatientHomeVM(@NonNull Application application) {
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

    public void callSearchPatient(){
        getRelatedActivity().nextActivityWithGenericParams(SearchPatientActivity.class);
    }

    public void callAddNewPatient(){
      //  getRelatedActivity().nextActivityWithGenericParams(AddNewPatientActivity.class);
        Intent intent = new Intent(getRelatedActivity(), AddNewPatientActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", getCurrentUser());
        bundle.putSerializable("clinic", getRelatedActivity().getCurrentClinic());
        bundle.putSerializable("step", ApplicationStep.STEP_CREATE);
        intent.putExtras(bundle);
        getRelatedActivity().startActivity(intent);
    }

    public void callReports(){
        getRelatedActivity().nextActivityWithGenericParams(ReportTypeActivity.class);
    }

    @Override
    public PatientHomeActivity getRelatedActivity(){
        return (PatientHomeActivity) super.getRelatedActivity();
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
