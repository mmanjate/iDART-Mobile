package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.service.EpisodeService;
import mz.org.fgh.idartlite.service.PatientService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.SearchPatientActivity;

public class PatientVM extends BaseViewModel {

    private Patient patient;
    private PatientService patientService;
    private EpisodeService episodeService;

    
    public String searchParam;
    public List<Patient> searchResults;

    public PatientVM(@NonNull Application application) {
        super(application);
        patientService = new PatientService(application, getCurrentUser());
        episodeService = new EpisodeService(application,getCurrentUser());

    }

    public List<Patient> searchPatient(String param, Clinic clinic) throws SQLException {
        return patientService.searchPatientByParamAndClinic(param,clinic);
    }

    public void loadPatientEpisodes() throws SQLException {
        this.patient.setEpisodes(episodeService.getAllEpisodesByPatient(this.patient));
        
        if (!Utilities.listHasElements(this.patient.getEpisodes())){
            throw new RuntimeException(getRelatedActivity().getString(R.string.no_episode_found));
        }
    }

    public void initSearch(){
        if(Utilities.stringHasValue(searchParam)){
            doSearch();
            getRelatedActivity().displaySearchResult();
            if(!Utilities.listHasElements(this.searchResults)){
                Utilities.displayAlertDialog(getRelatedActivity(),getRelatedActivity().getString(R.string.no_search_results)).show();
            }else {
                Utilities.hideSoftKeyboard(getRelatedActivity());
            }
        }else {
            Utilities.displayAlertDialog(getRelatedActivity(),getRelatedActivity().getString(R.string.nid_or_name_is_mandatory)).show();
        }
    }

    private void doSearch(){
        try {
            this.searchResults = searchPatient(this.searchParam.trim(),getCurrentClinic());
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public SearchPatientActivity getRelatedActivity() {
        return (SearchPatientActivity) super.getRelatedActivity();
    }

    @Bindable
    public Clinic getClinic(){
        return getRelatedActivity().getCurrentClinic();
    }

    @Bindable
    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
        notifyPropertyChanged(BR.searchParam);
    }

    public List<Patient> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<Patient> searchResults) {
        this.searchResults = searchResults;
    }
}
