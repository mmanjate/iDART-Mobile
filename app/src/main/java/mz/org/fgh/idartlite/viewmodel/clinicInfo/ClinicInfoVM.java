package mz.org.fgh.idartlite.viewmodel.clinicInfo;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.model.patient.Patient;
import mz.org.fgh.idartlite.service.clinicInfo.ClinicInfoService;
import mz.org.fgh.idartlite.service.clinicInfo.IClinicInfoService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.clinicInfo.ClinicInfoActivity;

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


    public void setTreatmentStartDate(Date date) {
        this.clinicInformation.setStartTreatmentDate(date);
        notifyPropertyChanged(BR.treatmentStartDate);
    }

    @Bindable
    public Date getTreatmentStartDate() {
        return this.clinicInformation.getStartTreatmentDate();

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

        String messageToShow= "";;

      if(getRelatedActivity().getAnswerChecked().length()!=0){
          Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getAnswerChecked()).show();
          return;
      }

      getRelatedActivity().getWeightFromView();

        String validationErros = clinicInformation.validateClinicInfoData(getRelatedActivity());
        if (validationErros.isEmpty()) {

            try {
                clinicInformation.setPatient(patient);

                if (getClinicInformation().getId() == 0) {


                    clinicInformation.setUuid(Utilities.getNewUUID().toString());
                    clinicInformation.setSyncStatus("R");
                    clinicInfoService.createClinicInfo(clinicInformation);
                }
                else {
                    clinicInfoService.updateClinicInfo(clinicInformation);
                }

                if(clinicInformation.isTreatmentTB()){
                    //Utilities.displayConfirmationDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.info_tb_stop_message),getRelatedActivity().getString(R.string.yes),null, getRelatedActivity()).show();
                   // return;

                    messageToShow = Utilities.concatStrings(messageToShow, getRelatedActivity().getString(R.string.info_tb_stop_message)  , "\n\n");
                }
                if(clinicInformation.getCheckForTbSyntoms()){
                    //Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.information_tb_message), getRelatedActivity()).show();
                    //return;

                    messageToShow = Utilities.concatStrings(messageToShow, getRelatedActivity().getString(R.string.information_tb_message)  , "\n\n");
                }


                if(clinicInformation.getCheckPregnancy()){
                  //  Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.info_refer_to_us_message), getRelatedActivity()).show();
                   // return;
                    messageToShow = Utilities.concatStrings(messageToShow, getRelatedActivity().getString(R.string.info_refer_to_us_message)  , "\n\n");
                }

                if(clinicInformation.getLateDays()>7){
                //    Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.info_monitoring_message), getRelatedActivity()).show();
                //    return;
                    messageToShow = Utilities.concatStrings(messageToShow, getRelatedActivity().getString(R.string.info_monitoring_message)+"  e / ou "  , "\n\n");

                    messageToShow = Utilities.concatStrings(messageToShow, getRelatedActivity().getString(R.string.info_refer_to_us_message)  , "\n\n");
                }

                if(clinicInformation.isAdverseReactionOfMedicine()){
                //    Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.info_ram_message), getRelatedActivity()).show();
                //    return;

                    messageToShow = Utilities.concatStrings(messageToShow, getRelatedActivity().getString(R.string.info_ram_message)  , "\n\n");
                }

                messageToShow = Utilities.concatStrings(messageToShow, getRelatedActivity().getString(R.string.clinic_information_saved_sucessfuly)  , "\n\n");
                Utilities.displayAlertDialog(getRelatedActivity(),messageToShow, getRelatedActivity()).show();

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

/*
    @Bindable
    public String getWeight() {
        if (getClinicInformation().getWeight() == 0) return "";

        return Utilities.parseDoubleToString(getClinicInformation().getWeight());
    }

    public void setWeight(String weight) {
        this.clinicInformation.setWeight(Utilities.stringHasValue(weight) ? Double.parseDouble(weight) : 0.0);
        notifyPropertyChanged(BR.weight);

    }*/

    @Bindable
    public String getHeight() {
        if (getClinicInformation().getHeight() == 0) return "";

        return Utilities.parseIntToString(getClinicInformation().getHeight());
    }

    public void setHeight(String height) {
        this.clinicInformation.setHeight(Utilities.stringHasValue(height) ? Integer.parseInt(height) : 0);
        notifyPropertyChanged(BR.height);
    }


    @Bindable
    public String getSystole() {
        if (getClinicInformation().getSystole() == 0) return "";

        return Utilities.parseIntToString(getClinicInformation().getSystole());
    }

    public void setSystole(String systole) {
        this.clinicInformation.setSystole(Utilities.stringHasValue(systole) ? Integer.parseInt(systole) : 0);
        notifyPropertyChanged(BR.systole);
    }

    @Bindable
    public String getDistole() {
        if (getClinicInformation().getDistort() == 0) return "";

        return Utilities.parseIntToString(getClinicInformation().getDistort());
    }

    public void setDistole(String distort) {
        this.clinicInformation.setDistort(Utilities.stringHasValue(distort) ? Integer.parseInt(distort) : 0);
        notifyPropertyChanged(BR.distole);
    }

    @Bindable
    public String getLateDays() {
        if (getClinicInformation().getLateDays() == 0) return "";

        return Utilities.parseIntToString(getClinicInformation().getLateDays());
    }

    public void setLateDays(String lateDays) {
        this.clinicInformation.setLateDays(Utilities.stringHasValue(lateDays) ? Integer.parseInt(lateDays) : 0);
        notifyPropertyChanged(BR.lateDays);
    }

    @Bindable
    public String getDaysWithoutMedicine() {
        if (getClinicInformation().getDaysWithoutMedicine() == 0) return "";

        return Utilities.parseIntToString(getClinicInformation().getDaysWithoutMedicine());
    }

    public void setDaysWithoutMedicine(String daysWithoutMedicine) {
        this.clinicInformation.setDaysWithoutMedicine(Utilities.stringHasValue(daysWithoutMedicine) ? Integer.parseInt(daysWithoutMedicine) : 0);
        notifyPropertyChanged(BR.daysWithoutMedicine);
    }

    public void setImc(String imc) {
        this.clinicInformation.setImc(imc);
        notifyPropertyChanged(BR.imc);
    }

    @Bindable
    public String getImc() {
        return this.clinicInformation.getImc();

    }

}
