package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.base.BaseViewModel;

public class PatientDetailsVM extends BaseViewModel {

    private boolean initialDataVisible;
    private boolean contactDataVisible;
    private boolean otherDataVisible;

    public PatientDetailsVM(@NonNull Application application) {
        super(application);
    }

    @Bindable
    public boolean isInitialDataVisible() {
        return initialDataVisible;
    }

    public void setInitialDataVisible(boolean initialDataVisible) {
        this.initialDataVisible = initialDataVisible;
        notifyPropertyChanged(BR.initialDataVisible);
    }

    @Bindable
    public boolean isContactDataVisible() {
        return contactDataVisible;
    }

    public void setContactDataVisible(boolean contactDataVisible) {
        this.contactDataVisible = contactDataVisible;
        notifyPropertyChanged(BR.contactDataVisible);
    }

    @Bindable
    public boolean isOtherDataVisible() {
        return otherDataVisible;
    }

    public void setOtherDataVisible(boolean otherDataVisible) {
        this.otherDataVisible = otherDataVisible;
        notifyPropertyChanged(BR.otherDataVisible);
    }
}
