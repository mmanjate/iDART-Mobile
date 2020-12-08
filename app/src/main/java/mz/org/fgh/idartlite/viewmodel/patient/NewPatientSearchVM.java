package mz.org.fgh.idartlite.viewmodel.patient;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.SearchVM;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.rest.service.Patient.RestPatientService;
import mz.org.fgh.idartlite.service.episode.EpisodeService;
import mz.org.fgh.idartlite.service.episode.IEpisodeService;
import mz.org.fgh.idartlite.service.patient.IPatientService;
import mz.org.fgh.idartlite.service.patient.PatientService;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.patientSearch.NewPatientSearchActivity;

public class NewPatientSearchVM extends SearchVM<Patient> {

    private Patient patient;
    private IPatientService patientService;
    private IEpisodeService episodeService;


    private String searchNid;

    private String searchName;

    private String searchSurname;



    public NewPatientSearchVM(@NonNull Application application) {
        super(application);

        patientService = (PatientService) getServiceProvider().get(PatientService.class);
        episodeService = new EpisodeService(application,getCurrentUser());

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

    public List<Patient> searchPatient(String nid,String name,String surname, long offset, long limit) throws SQLException {
        return patientService.searchPatientByNidOrNameOrSurname( nid, name, surname, offset, limit,this.getRelatedActivity());
    }

    public void createPatientIfNotExists(Patient patient) throws SQLException {
       Patient localPatient= patientService.checkExistsPatientWithNID(patient.getNid());
       if(localPatient==null) {
           patientService.savePatient(patient);
       }
    }


    public Episode initEpisode(Patient patient){

        Episode episode=new Episode();
        episode.setPatient(patient);
        episode.setStartReason("Referido De");
        episode.setUsUuid(getCurrentClinic().getUuid());
        episode.setSanitaryUnit(getCurrentClinic().getDescription());
        episode.setUuid(Utilities.getNewUUID().toString());
        episode.setSyncStatus("R");
        episode.setEpisodeDate(DateUtilities.getCurrentDate());
      return  episode;
    }


    public void createEpisode(Episode episode) throws SQLException {
        episodeService.createEpisode(episode);
    }

    public void loadPatientEpisodes() throws SQLException {
        this.patient.setEpisodes(episodeService.getAllEpisodesByPatient(this.patient));
        
        if (!Utilities.listHasElements(this.patient.getEpisodes())){
            throw new RuntimeException(getRelatedActivity().getString(R.string.no_episode_found));
        }
    }

    @Override
    public void initSearch(){
        if(!Utilities.stringHasValue(searchNid) && !Utilities.stringHasValue(searchName) && !Utilities.stringHasValue(searchSurname)) {
            Utilities.displayAlertDialog(getRelatedActivity(),getRelatedActivity().getString(R.string.nid_or_name_is_mandatory)).show();
        }else {
            try {
                super.initSearch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doOnNoRecordFound() {
        //Utilities.displayAlertDialog(getRelatedActivity(),getRelatedActivity().getString(R.string.no_search_results)).show();

       // if(getRelatedActivity().)
      //  Utilities.displayConfirmationDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.would_like_create_patient), getRelatedActivity().getString(R.string.yes), getRelatedActivity().getString(R.string.no), ((NewPatientSearchActivity)getRelatedActivity())).show();

      RestPatientService.restGetPatientByNidOrNameOrSurname(searchNid, searchName, searchSurname, this.getRelatedActivity());


    //    displaySearchResults();
    }

    @Override
    public void displaySearchResults() {
        Utilities.hideSoftKeyboard(getRelatedActivity());

        getRelatedActivity().displaySearchResult();
    }

    @Override
    public List<Patient> doSearch(long offset, long limit) throws SQLException {

        return searchPatient(this.searchNid !=null? this.searchNid.trim():null ,this.searchName !=null? this.searchName.trim():null,this.searchSurname !=null? this.searchSurname.trim():null, offset, limit);
    }

    public List<Patient> getAllPatient() throws SQLException {
        return patientService.getALLPatient();
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public NewPatientSearchActivity getRelatedActivity(){
        return (NewPatientSearchActivity) super.getRelatedActivity();
    }

    @Override
    public void preInit() {

    }


    @Bindable
    public Clinic getClinic(){
        return getCurrentClinic();
    }

    @Bindable
    public String getSearchNid() {
        return searchNid;
    }

    public void setSearchNid(String searchNid) {
        this.searchNid = searchNid;
    }

    @Bindable
    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    @Bindable
    public String getSearchSurname() {
        return searchSurname;
    }

    public void setSearchSurname(String searchSurname) {
        this.searchSurname = searchSurname;
    }
}
