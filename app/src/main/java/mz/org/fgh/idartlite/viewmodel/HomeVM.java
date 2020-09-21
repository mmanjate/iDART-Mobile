package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.view.HomeActivity;
import mz.org.fgh.idartlite.view.SearchPatientActivity;
import mz.org.fgh.idartlite.view.stock.StockActivity;

public class HomeVM extends BaseViewModel {

    public HomeVM(@NonNull Application application) {
        super(application);
    }

    public void callSearchPatient(){
        Intent intent = new Intent(getApplication(), SearchPatientActivity.class);
        getRelatedActivity().startActivity(intent);
    }

    public void callStck(){
        Intent intent = new Intent(getApplication(), StockActivity.class);
        getRelatedActivity().startActivity(intent);
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
}
