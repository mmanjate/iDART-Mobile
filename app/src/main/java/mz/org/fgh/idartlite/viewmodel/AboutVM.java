package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import mz.org.fgh.idartlite.base.BaseViewModel;

public class AboutVM extends BaseViewModel {

    public AboutVM(@NonNull Application application) {
        super(application);
    }

    public void close(){
        getRelatedActivity().finish();
    }
}
