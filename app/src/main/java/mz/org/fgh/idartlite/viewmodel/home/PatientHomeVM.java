package mz.org.fgh.idartlite.viewmodel.home;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.util.HashMap;
import java.util.Map;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.common.ApplicationStep;
import mz.org.fgh.idartlite.view.home.PatientHomeActivity;
import mz.org.fgh.idartlite.view.patientPanel.AddNewPatientActivity;
import mz.org.fgh.idartlite.view.patientSearch.NewPatientSearchActivity;
import mz.org.fgh.idartlite.view.patientSearch.SearchPatientActivity;
import mz.org.fgh.idartlite.view.reports.ReportTypeActivity;

public class PatientHomeVM extends BaseViewModel {

    public PatientHomeVM(@NonNull Application application) {
        super(application);
    }

    private static final String SANITARY_UNIT="Unidade Sanitária";

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

    public void callSearchPatient(String searchMode){

        if(getRelatedActivity().getApplicationStep().checkSanitaryUnit(currentClinic)) {
            getRelatedActivity().nextActivityWithGenericParams(NewPatientSearchActivity.class);
        }
        else {
            Map<String, Object> params = new HashMap<>();
            params.put("user", getCurrentUser());
            params.put("clinic", getCurrentClinic());
            params.put("searchMode", searchMode);
            params.put("clinicSector", getCurrentClinicSector());
            getRelatedActivity().nextActivityFinishingCurrent(SearchPatientActivity.class, params);
        }
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
}
