package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;

import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.view.HomeActivity;
import mz.org.fgh.idartlite.view.SearchPatientActivity;

public class HomeVM extends BaseViewModel {

    public HomeVM(@NonNull Application application) {
        super(application);
    }

    public void callSearchPatient(){
        Intent intent = new Intent(getApplication(), SearchPatientActivity.class);
        getBaseActivity().startActivity(intent);
    }

    @Override
    public HomeActivity getBaseActivity(){
        return (HomeActivity) super.getBaseActivity();
    }

}
