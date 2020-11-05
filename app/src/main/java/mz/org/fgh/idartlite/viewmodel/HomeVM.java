package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.view.HomeActivity;
import mz.org.fgh.idartlite.view.SearchPatientActivity;
import mz.org.fgh.idartlite.view.reports.ReportTypeActivity;
import mz.org.fgh.idartlite.view.reports.ReportsListingActivity;
import mz.org.fgh.idartlite.view.stock.StockActivity;

public class HomeVM extends BaseViewModel {

    public HomeVM(@NonNull Application application) {
        super(application);
    }

    public void callSearchPatient(){
        getRelatedActivity().nextActivityWithGenericParams(SearchPatientActivity.class);
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
