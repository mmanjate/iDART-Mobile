package mz.org.fgh.idartlite.viewmodel.clinicInfo;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
        return clinicInfoService.getAllClinicInfosByPatient(selectedPatient);
    }


    public void setRegisterDate(Date date) {
        this.clinicInformation.setRegisterDate(date);
        notifyPropertyChanged(BR.registerDate);
    }

    @Bindable
    public Date getRegisterDate() {
        return this.clinicInformation.getRegisterDate();

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

    public void save1() {

        String validationErros = clinicInformation.validateClinicInfoData(getRelatedActivity());
        if (validationErros.isEmpty()) {

            try {
                clinicInformation.setPatient(patient);
                if (getClinicInformation().getId() == 0) {

                    clinicInfoService.createClinicInfo(clinicInformation);
                }
                else {
                    clinicInfoService.updateClinicInfo(clinicInformation);
                }

                Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.clinic_information_saved_sucessfuly), getRelatedActivity()).show();

            } catch (SQLException e) {
                e.printStackTrace();
                Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.save_error_msg_clinic_information) + e.getLocalizedMessage()).show();
            }
        } else {
            Utilities.displayAlertDialog(getRelatedActivity(), validationErros).show();
        }

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


    @Bindable
    public String getWeight() {
        return String.valueOf(clinicInformation.getWeight());
    }

    public void setWeight(String weight) {
            this.clinicInformation.setWeight(Double.parseDouble(weight));
        notifyPropertyChanged(BR.weight);
    }

    @Bindable
    public String getHeight() {
        return String.valueOf(clinicInformation.getHeight());
    }

    public void setHeight(String height) {
        this.clinicInformation.setHeight(Double.parseDouble(height));
        notifyPropertyChanged(BR.height);
    }

    @Bindable
    public String getImc() {
        return clinicInformation.getImc();
    }

    public void setImc(String imc) {
        this.clinicInformation.setImc(imc);
        notifyPropertyChanged(BR.imc);
    }

    @Bindable
    public String getSystole() {
        return String.valueOf(clinicInformation.getSystole());
    }

    public void setSystole(String systole) {
        this.clinicInformation.setSystole(Integer.parseInt(systole));
        notifyPropertyChanged(BR.systole);
    }

    @Bindable
    public String getDistole() {
        return String.valueOf(clinicInformation.getDistort());
    }

    public void setDistole(String distort) {
        this.clinicInformation.setDistort(Integer.parseInt(distort));
        notifyPropertyChanged(BR.distole);
    }

    @Bindable
    public String getLateDays() {
        return String.valueOf(clinicInformation.getLateDays());
    }

    public void setLateDays(String lateDays) {
        if(!lateDays.isEmpty() || lateDays.length()!=0) {
            this.clinicInformation.setLateDays(Integer.parseInt(lateDays));
        }
        notifyPropertyChanged(BR.lateDays);
    }

    @Bindable
    public String getDaysWithoutMedicine() {
        return String.valueOf(clinicInformation.getDaysWithoutMedicine());
    }

    public void setDaysWithoutMedicine(String daysWithoutMedicine) {
        if(!daysWithoutMedicine.isEmpty() || daysWithoutMedicine.length()!=0) {
            this.clinicInformation.setDaysWithoutMedicine(Integer.parseInt(daysWithoutMedicine));
        }
        notifyPropertyChanged(BR.daysWithoutMedicine);
    }
}
