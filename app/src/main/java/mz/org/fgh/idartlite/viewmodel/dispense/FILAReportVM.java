package mz.org.fgh.idartlite.viewmodel.dispense;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.SearchVM;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.patient.Patient;
import mz.org.fgh.idartlite.rest.service.Dispense.RestDispenseService;
import mz.org.fgh.idartlite.searchparams.AbstractSearchParams;
import mz.org.fgh.idartlite.searchparams.PatientSearchParams;
import mz.org.fgh.idartlite.service.dispense.DispenseService;
import mz.org.fgh.idartlite.service.dispense.IDispenseService;
import mz.org.fgh.idartlite.service.patient.IPatientService;
import mz.org.fgh.idartlite.service.patient.PatientService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.reports.FILAReportActivity;

public class FILAReportVM extends SearchVM<Patient>{

    private Patient patient;
    private IPatientService patientService;

    private IDispenseService dispenseService;

    List<Dispense> dispenseList;


    public FILAReportVM(@NonNull Application application) {
        super(application);
        patientService = new PatientService(application, getCurrentUser());
        dispenseService=new DispenseService(application,getCurrentUser());
        setFullLoad(true);
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

    public List<Patient> searchPatient(String param, Clinic clinic, long offset, long limit) throws SQLException {
        return patientService.searchPatientByParamAndClinic(param,clinic, offset, limit);
    }

    public void showDialog () {
        getLoadingDialog().startLoadingDialog();
    }

    public List<Dispense> getAllDispensesByPatient(Patient patient) throws SQLException {
        this.dispenseList = new ArrayList<>();

        if (this.isOnlineSearch()) {
            RestDispenseService.restGetAllDispenseByPatient(patient, this);
        } else {
            return dispenseService.getAllOfPatient(patient);
        }
        return null;
    }


    @Override
    public void initSearch(){
        if(!Utilities.stringHasValue(getSearchParam())) {
            Utilities.displayAlertDialog(getRelatedActivity(),getRelatedActivity().getString(R.string.nid_or_name_is_mandatory)).show();
        }else {
            super.initSearch();
        }
    }

    @Override
    public void doOnResponse(String flag, List objects) {
        getRelatedActivity().doAfterSearch(objects);
    }

    @Override
    protected void doOnNoRecordFound() {
        Utilities.displayAlertDialog(getRelatedActivity(),getRelatedActivity().getString(R.string.no_search_results)).show();
    }

    @Override
    public void displaySearchResults() {
        Utilities.hideSoftKeyboard(getRelatedActivity());

        getRelatedActivity().displaySearchResult();
    }

    public void dismisDialog () {
        getLoadingDialog().dismisDialog();
    }

    @Override
    public PatientSearchParams getSearchParams() {
        return (PatientSearchParams) super.getSearchParams();
    }

    @Override
    public AbstractSearchParams<Patient> initSearchParams() {
        return new PatientSearchParams();
    }

    @Override
    public List<Patient> doSearch(long offset, long limit) throws SQLException {
        return searchPatient(getSearchParam().trim(), getCurrentClinic(), offset, limit);
    }

    @Override
    public void doOnlineSearch(long offset, long limit) throws SQLException {
        doSearch(offset, limit);
    }

    @Override
    protected boolean canDisplayRecsAfterInitSearch() {
        return true;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public FILAReportActivity getRelatedActivity() {
        return (FILAReportActivity) super.getRelatedActivity();
    }

    @Override
    public void preInit() {

    }

    @Bindable
    public Clinic getClinic(){
        return getCurrentClinic();
    }

    @Bindable
    public String getSearchParam() {
        return getSearchParams().getSearchParam();
    }

    public void setSearchParam(String searchParam) {
        getSearchParams().setSearchParam(searchParam);
        notifyPropertyChanged(BR.searchParam);
    }
}
