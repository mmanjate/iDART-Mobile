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
import mz.org.fgh.idartlite.base.rest.BaseRestService;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.SearchVM;
import mz.org.fgh.idartlite.listener.rest.RestResponseListener;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.rest.service.Dispense.RestDispenseService;
import mz.org.fgh.idartlite.searchparams.AbstractSearchParams;
import mz.org.fgh.idartlite.searchparams.PatientSearchParams;
import mz.org.fgh.idartlite.service.dispense.DispenseService;
import mz.org.fgh.idartlite.service.dispense.IDispenseService;
import mz.org.fgh.idartlite.service.patient.IPatientService;
import mz.org.fgh.idartlite.service.patient.PatientService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.reports.FILAReportActivity;

public class FILAReportVM extends SearchVM<Patient> implements RestResponseListener<Dispense> {

    private Patient patient;
    private IPatientService patientService;

    private IDispenseService dispenseService;

    List<Dispense> dispenseList;


    public FILAReportVM(@NonNull Application application) {
        super(application);
        patientService = new PatientService(application, getCurrentUser());
        dispenseService=new DispenseService(application,getCurrentUser());

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

    public List<Dispense> getAllDispensesByPatient(Patient patient) throws SQLException {
        this.dispenseList = new ArrayList<>();

        if (this.isOnlineSearch()) {
            RestDispenseService.restGetAllDispenseByPatient(patient, this);

            while (!Utilities.stringHasValue(this.onlineRequestError) || Utilities.listHasElements(this.dispenseList)) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (!Utilities.stringHasValue(this.onlineRequestError)) return this.dispenseList;
            else Utilities.displayAlertDialog(getRelatedActivity(), this.onlineRequestError).show();
        } else   {
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
    protected void doOnNoRecordFound() {
        Utilities.displayAlertDialog(getRelatedActivity(),getRelatedActivity().getString(R.string.no_search_results)).show();
    }

    @Override
    public void displaySearchResults() {
        Utilities.hideSoftKeyboard(getRelatedActivity());

        getRelatedActivity().displaySearchResult();
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
    public List<Patient> doOnlineSearch(long offset, long limit) throws SQLException {
        return doSearch(offset, limit);
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

    @Override
    public void doOnRestSucessResponse(String flag) {

    }

    @Override
    public void doOnRestErrorResponse(String errormsg) {

    }

    @Override
    public void doOnRestSucessResponseObject(String flag, Dispense object) {

    }

    @Override
    public void doOnRestSucessResponseObjects(String flag, List<Dispense> objects) {

    }

    @Override
    public void doOnResponse(String flag, List<Dispense> objects) {
        if (flag.equals(BaseRestService.REQUEST_SUCESS)) {
            this.dispenseList = objects;
        } else if (flag.equals(BaseRestService.REQUEST_NO_DATA)) {
            this.onlineRequestError = "Não foram encontrados registos de levantamentos deste paciente.";
        } else {
            this.onlineRequestError = flag;
        }
    }
}
