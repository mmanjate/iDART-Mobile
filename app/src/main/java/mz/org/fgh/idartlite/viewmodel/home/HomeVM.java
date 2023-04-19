package mz.org.fgh.idartlite.viewmodel.home;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.ClinicSector;
import mz.org.fgh.idartlite.view.home.IDartHomeActivity;
import mz.org.fgh.idartlite.view.home.PatientHomeActivity;
import mz.org.fgh.idartlite.view.reports.ReportTypeActivity;
import mz.org.fgh.idartlite.view.stock.panel.StockActivity;

public class HomeVM extends BaseViewModel {

    public HomeVM(@NonNull Application application) {

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
    public IDartHomeActivity getRelatedActivity(){
        return (IDartHomeActivity) super.getRelatedActivity();
    }

    @Override
    public void preInit() {

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
