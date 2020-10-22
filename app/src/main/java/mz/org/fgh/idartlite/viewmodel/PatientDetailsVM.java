package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.view.patient.PatientDetailsFragment;

public class PatientDetailsVM extends BaseViewModel {

    private boolean initialDataVisible;
    private boolean contactDataVisible;
    private boolean otherDataVisible;

    private Patient patient;

    private PatientDetailsFragment patientDetailsFragment;

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

    public void changeDataViewStatus(View view){
        getPatientDetailsFragment().changeVisibilityToInitialData(view);
    }

    public PatientDetailsFragment getPatientDetailsFragment() {
        return patientDetailsFragment;
    }

    public void setPatientDetailsFragment(PatientDetailsFragment patientDetailsFragment) {
        this.patientDetailsFragment = patientDetailsFragment;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
