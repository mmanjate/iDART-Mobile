package mz.org.fgh.idartlite.viewmodel.patient;

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
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.model.District;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Province;
import mz.org.fgh.idartlite.service.clinic.ClinicSectorService;
import mz.org.fgh.idartlite.service.clinic.IClinicSectorService;
import mz.org.fgh.idartlite.service.episode.EpisodeService;
import mz.org.fgh.idartlite.service.episode.IEpisodeService;
import mz.org.fgh.idartlite.service.patient.IPatientService;
import mz.org.fgh.idartlite.service.patient.PatientService;
import mz.org.fgh.idartlite.service.territory.DistrictService;
import mz.org.fgh.idartlite.service.territory.IDistrictService;
import mz.org.fgh.idartlite.service.territory.IProvinceService;
import mz.org.fgh.idartlite.service.territory.ProvinceService;
import mz.org.fgh.idartlite.util.SimpleValue;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.patientPanel.AddNewPatientActivity;

public class AddPatientVM extends BaseViewModel {
    private boolean initialDataVisible;

    private boolean addressDataVisible;

    private IProvinceService provinceService;

    private IDistrictService districtService;

    private IPatientService patientService;

    private IClinicSectorService clinicSectorService;

    private List<SimpleValue> identifierTypes;

    private List<SimpleValue> genders;

    private List<District> districts;

    private Patient patient;

    private Episode episode;

    private String age;

    private IEpisodeService episodeService;

    public AddPatientVM(@NonNull Application application) {
        super(application);

        initialDataVisible = true;

        provinceService = new ProvinceService(application, getCurrentUser());
        districtService = new DistrictService(application, getCurrentUser());
        patientService = new PatientService(application, getCurrentUser());
        clinicSectorService=new ClinicSectorService(application,getCurrentUser());
        episodeService = new EpisodeService(application, getCurrentUser());
        identifierTypes = new ArrayList<>();
        genders = new ArrayList<>();
        districts = new ArrayList<>();
        patient=new Patient();
        episode=new Episode();
        loadIdentifierTypes();
        loadGenders();


    }

    private void doSave() throws SQLException {


        if((patient.getProvince()!=null && patient.getProvince().getDescription()!=null)  || (patient.getDistrict()!=null && patient.getDistrict().getDescription()!=null )){

            patient.setUuid(Utilities.getNewUUID().toString());
            patient.setClinic(getRelatedActivity().getCurrentClinic());

            episode.setPatient(patient);
            episode.setStartReason("Referido De");
            episode.setUsUuid(currentClinic.getUuid());
            episode.setSanitaryUnit(currentClinic.getDescription());
            episode.setUuid(Utilities.getNewUUID().toString());
            episode.setSyncStatus("R");

            String validationErros=patient.validate(getRelatedActivity());

            String validationErrorsEpisode=episode.validateEpisodeDataForCreatingPatient(getRelatedActivity());

            Patient localPatient = patientService.checkExistsPatientWithNID(patient.getNid());


            if(validationErros.isEmpty() && validationErrorsEpisode.isEmpty()) {

                if(getRelatedActivity().getApplicationStep().isApplicationStepEdit()){
                    if(localPatient.getId() == patient.getId()){
                        patientService.updatePatient(this.patient);
                        episodeService.udpateEpisode(episode);
                        Utilities.displayAlertDialog( getRelatedActivity(), getRelatedActivity().getString(R.string.patient_updated_sucessfuly),getRelatedActivity()).show();

                    }
                    else{
                        Utilities.displayAlertDialog( getRelatedActivity(),"Ja Existe um Paciente com o NID colocado").show();
                    }

                }
                else {
                    if(localPatient==null) {
                        patientService.savePatient(this.patient);
                        episodeService.createEpisode(episode);
                        Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.patient_created_sucessfuly), getRelatedActivity()).show();
                    }
                    else{
                        Utilities.displayAlertDialog( getRelatedActivity(),"Ja Existe um Paciente com o NID colocado").show();
                    }
                }
            }
            else {

                Utilities.displayAlertDialog( getRelatedActivity(),validationErros + validationErrorsEpisode).show();

            }
        }
        else {
            Utilities.displayAlertDialog( getRelatedActivity(),"Provincia e/ou districto sao dados obrigatorios").show();
        }

    }

    public void save()  {
     //   getCurrentStep().changeToSave();
        try {
            doSave();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //  Utilities.displayConfirmationDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.confirm_prescription_save), getRelatedActivity().getString(R.string.yes), getRelatedActivity().getString(R.string.no), AddPatientVM.this).show();

    }

    @Override
    public AddNewPatientActivity getRelatedActivity() {
        return (AddNewPatientActivity) super.getRelatedActivity();
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

    public void changeInitialDataViewStatus(View view){
        ((AddNewPatientActivity) getRelatedActivity()).changeFormSectionVisibility(view);
    }

    public List<Province> getAllProvinces() throws SQLException {
        return provinceService.getAllProvinces();
    }

    public List<District> getAllDistrictsByProvince(Province province) throws SQLException {
        return districtService.getDistrictByProvince(province);
    }

    public List<SimpleValue> getIdentifierTypes() {
        return identifierTypes;
    }

    public void setIdentifierTypes(List<SimpleValue> identifierTypes) {
        this.identifierTypes = identifierTypes;
    }

    public List<SimpleValue> getGenders() {
        return genders;
    }

    public void setGenders(List<SimpleValue> genders) {
        this.genders = genders;
    }

    private void loadIdentifierTypes(){
      //  identifierTypes.add(new SimpleValue());
        identifierTypes.add(SimpleValue.fastCreate("NID"));
        identifierTypes.add(SimpleValue.fastCreate("PreP"));
    }

    private void loadGenders(){
        genders.add(new SimpleValue());
        genders.add(SimpleValue.fastCreate("M"));
        genders.add(SimpleValue.fastCreate("F"));
    }

    @Bindable
    public Listble getGender(){
        return Utilities.findOnArray(this.genders, SimpleValue.fastCreate(getPatient().getGender()));
    }

    public void setGender(Listble gender){
        getPatient().setGender(gender.getDescription());
        notifyPropertyChanged(BR.gender);
    }

    @Bindable
    public Listble getIdentifierType(){
        return Utilities.findOnArray(this.identifierTypes, SimpleValue.fastCreate(getPatient().getIdentifierType()));
    }

    public void setIdentifierType(Listble identifierType){
        getPatient().setIdentifierType(identifierType.getDescription());
        notifyPropertyChanged(BR.identifierType);
    }

    public void setInitalDateTarv(Date date) {
        this.patient.setStartARVDate(date);
        notifyPropertyChanged(BR.initalDateTarv);
    }

    @Bindable
    public Date getInitalDateTarv() {
        return this.patient.getStartARVDate();

    }

    public void setBirthDate(Date date) {
        this.patient.setBirthDate(date);
        notifyPropertyChanged(BR.birthDate);
    }

    @Bindable
    public Date getBirthDate() {
        return this.patient.getBirthDate();

    }

    public void setAdmissionDate(Date date) {
        this.episode.setEpisodeDate(date);
        notifyPropertyChanged(BR.admissionDate);
    }

    @Bindable
    public Date getAdmissionDate() {
        return this.episode.getEpisodeDate();

    }

    @Bindable
    public Listble getProvince() {
        return patient.getProvince();
    }

    public void setProvince(Listble province)  {
        this.patient.setProvince((Province) province);

       // if(patient.getDistrict()==null) {
            try {
            //    getDistricts().clear();
                districts.clear();
                getDistricts().add(new District());
                getDistricts().addAll(getAllDistrictsByProvince((Province) getProvince()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            getRelatedActivity().loadDistrcitAdapter();
            notifyPropertyChanged(BR.province);
        notifyPropertyChanged(BR.district);
        //}

    }

    @Bindable
    public Listble getDistrict() {
        return patient.getDistrict();
    }

    public void setDistrict(Listble district) {
        this.patient.setDistrict((District) district);
        notifyPropertyChanged(BR.district);
    }



    @Bindable
    public String getNearPlace() {
        return patient.getAddress();
    }

    public void setNearPlace(String nearPlace) {
        patient.setAddress(nearPlace);
        notifyPropertyChanged(BR.nearPlace);
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    @Bindable
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }


    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public String getTitle(){
       if (getRelatedActivity().getApplicationStep().isApplicationStepEdit()){
           return  getRelatedActivity().getString(R.string.actualizar_paciente);
       }
       else{
           return  getRelatedActivity().getString(R.string.adicionar_paciente);
       }

    }
}
