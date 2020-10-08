package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.SearchVM;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.service.EpisodeService;
import mz.org.fgh.idartlite.service.PatientService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.SearchPatientActivity;

public class PatientVM extends SearchVM<Patient> {

    private Patient patient;
    private PatientService patientService;
    private EpisodeService episodeService;

    
    private String searchParam;


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

    @Override
    public void initSearch(){
        if(!Utilities.stringHasValue(searchParam)) {
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
    protected void displaySearchResults() {
        getRelatedActivity().displaySearchResult();
    }

    @Override
    public List<Patient> doSearch() throws SQLException {
        return searchPatient(this.searchParam.trim(), getCurrentClinic());
    }

    @Override
    public int getPageSize() {
        return 5;
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
        return getCurrentClinic();
    }

    @Bindable
    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
        notifyPropertyChanged(BR.searchParam);
    }

}
