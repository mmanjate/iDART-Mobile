package mz.org.fgh.idartlite.viewmodel.clinicInfo;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.dao.clinicInfo.IClinicInfoDao;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.service.clinicInfo.ClinicInfoService;
import mz.org.fgh.idartlite.service.clinicInfo.IClinicInfoService;
import mz.org.fgh.idartlite.service.dispense.DispenseService;
import mz.org.fgh.idartlite.service.dispense.IDispenseService;
import mz.org.fgh.idartlite.service.episode.EpisodeService;
import mz.org.fgh.idartlite.service.episode.IEpisodeService;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.clinicInfo.ClinicInfoActivity;
import mz.org.fgh.idartlite.view.episode.EpisodeActivity;
import mz.org.fgh.idartlite.view.patientPanel.AddNewPatientActivity;

public class ClinicInfoVM extends BaseViewModel {



    private IClinicInfoService clinicInfoService;

    private Patient patient;
    private ClinicInformation clinicInformation;

    private boolean initialDataVisible;

    private boolean addressDataVisible;


    public ClinicInfoVM(@NonNull Application application) {
        super(application);
        initNewClinicInfo();
        clinicInfoService = new ClinicInfoService(application, getCurrentUser());

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

    private void initNewClinicInfo() {
        this.clinicInformation = new ClinicInformation();
    }

    public List<ClinicInformation> gatAllOfPatient(Patient selectedPatient) throws SQLException {
       // return episodeService.getAllEpisodesByPatient(selectedPatient);
        return null;
    }





    public void createClinicInfo(ClinicInformation clinicInformation) throws SQLException {
        clinicInfoService.createClinicInfo(clinicInformation);
    }
    public void deleteClinicInfo(ClinicInformation clinicInformation) throws SQLException {
        clinicInfoService.deleteClinicInfo(clinicInformation);
    }

    public void changeInitialDataViewStatus(View view){
        ((ClinicInfoActivity) getRelatedActivity()).changeFormSectionVisibility(view);
    }

    public ClinicInformation getClinicInformation() {
        return clinicInformation;
    }

    public void setClinicInformation(ClinicInformation clinicInformation) {
        this.clinicInformation = clinicInformation;
    }

    @Override
    public ClinicInfoActivity getRelatedActivity() {
        return (ClinicInfoActivity) super.getRelatedActivity();
    }

    @Override
    public void preInit() {

    }

    public void save1(){




    }
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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
    public boolean isAddressDataVisible() {
        return addressDataVisible;
    }

    public void setAddressDataVisible(boolean addressDataVisible) {
        this.addressDataVisible = addressDataVisible;
        notifyPropertyChanged(BR.addressDataVisible);
    }

}
