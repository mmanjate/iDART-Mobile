package mz.org.fgh.idartlite.viewmodel.patient;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.base.viewModel.SearchVM;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.service.episode.EpisodeService;
import mz.org.fgh.idartlite.service.episode.IEpisodeService;
import mz.org.fgh.idartlite.service.patient.IPatientService;
import mz.org.fgh.idartlite.service.patient.PatientService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.patientSearch.SearchPatientActivity;

public class PatientVM extends SearchVM<Patient> {

    private Patient patient;
    private IPatientService patientService;
    private IEpisodeService episodeService;

    
    private String searchParam;


    public PatientVM(@NonNull Application application) {
        super(application);

        patientService = (PatientService) getBaseServiceFactory().get(PatientService.class);
        episodeService = new EpisodeService(application,getCurrentUser());

    }

    @Override
    protected BaseModel initRecord() {
        return null;
    }

    @Override
    protected <T extends BaseService> Class<T> getRecordServiceClass() {
        return null;
    }

    @Override
    protected void initFormData() {

    }

    public List<Patient> searchPatient(String param, Clinic clinic, long offset, long limit) throws SQLException {
        return patientService.searchPatientByParamAndClinic(param,clinic, offset, limit);
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
    public void displaySearchResults() {
        Utilities.hideSoftKeyboard(getRelatedActivity());

        getRelatedActivity().displaySearchResult();
    }

    @Override
    public List<Patient> doSearch(long offset, long limit) throws SQLException {
        return searchPatient(this.searchParam.trim(), getCurrentClinic(), offset, limit);
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
