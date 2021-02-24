package mz.org.fgh.idartlite.viewmodel.patient;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.SearchVM;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.searchparams.AbstractSearchParams;
import mz.org.fgh.idartlite.searchparams.PatientSearchParams;
import mz.org.fgh.idartlite.service.episode.EpisodeService;
import mz.org.fgh.idartlite.service.episode.IEpisodeService;
import mz.org.fgh.idartlite.service.patient.IPatientService;
import mz.org.fgh.idartlite.service.patient.PatientService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.reports.PatientRegisterReportActivity;

public class PatientRegisterReportVM extends SearchVM<Patient> {


    private IPatientService patientService;
    private IEpisodeService episodeService;

    public PatientRegisterReportVM(@NonNull Application application) {
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

    public List<Patient> getPatientsByDates(Date startDate, Date endDate, long offset, long limit) throws SQLException {
        return patientService.getPatientsBetweenStartDateAndEndDate(getApplication(),startDate,endDate,offset,limit);
    }

    public void generatePDF() {
        try {
            this.getRelatedActivity().createPdfDocument();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doOnNoRecordFound() {
        if(getAllDisplyedRecords().size()>0){
            getRelatedActivity().generatePdfButton(true);
        }
        else {
            getRelatedActivity().generatePdfButton(false);
        }
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

    public List<Patient> doSearch(long offset, long limit) throws SQLException {
        return getPatientsByDates(getSearchParams().getStartdate(), getSearchParams().getEndDate(),offset,limit);
    }


    public PatientRegisterReportActivity getRelatedActivity() {
        return (PatientRegisterReportActivity) super.getRelatedActivity();
    }

    @Override
    public void preInit() {

    }

    @Bindable
    public Clinic getClinic(){
        return getCurrentClinic();
    }

}
