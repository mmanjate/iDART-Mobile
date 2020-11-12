package mz.org.fgh.idartlite.viewmodel.about;

import android.app.Application;

import androidx.annotation.NonNull;

import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;

public class AboutVM extends BaseViewModel {

    public AboutVM(@NonNull Application application) {
        super(application);
    }

    public void close(){
        getRelatedActivity().finish();
    }
}
