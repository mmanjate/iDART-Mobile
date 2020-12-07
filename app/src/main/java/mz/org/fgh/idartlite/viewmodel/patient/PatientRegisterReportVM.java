package mz.org.fgh.idartlite.viewmodel.patient;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.SearchVM;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.service.dispense.IDispenseService;
import mz.org.fgh.idartlite.service.episode.EpisodeService;
import mz.org.fgh.idartlite.service.episode.IEpisodeService;
import mz.org.fgh.idartlite.service.patient.IPatientService;
import mz.org.fgh.idartlite.service.patient.PatientService;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.patientSearch.SearchPatientActivity;
import mz.org.fgh.idartlite.view.reports.DispenseReportActivity;
import mz.org.fgh.idartlite.view.reports.PatientRegisterReportActivity;

public class PatientRegisterReportVM extends SearchVM<Patient> {


    private IPatientService patientService;
    private IEpisodeService episodeService;

    private String startDate;

    private String endDate;


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



    @Override
    public void initSearch(){
        if(!Utilities.stringHasValue(startDate) || !Utilities.stringHasValue(endDate)) {
            Utilities.displayAlertDialog(getRelatedActivity(),getRelatedActivity().getString(R.string.start_end_date_is_mandatory)).show();
        }else {

            try {
                super.initSearch();
                if(getAllDisplyedRecords().size()>0){
                    getRelatedActivity().generatePdfButton(true);
                }
                else {
                    getRelatedActivity().generatePdfButton(false);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
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
        Utilities.displayAlertDialog(getRelatedActivity(),getRelatedActivity().getString(R.string.no_search_results)).show();
    }

    @Override
    public void displaySearchResults() {
        Utilities.hideSoftKeyboard(getRelatedActivity());

        getRelatedActivity().displaySearchResult();
    }

    public List<Patient> doSearch(long offset, long limit) throws SQLException {

        return getPatientsByDates(DateUtilities.createDate(startDate, DateUtilities.DATE_FORMAT), DateUtilities.createDate(endDate, DateUtilities.DATE_FORMAT),offset,limit);
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

    @Bindable
    public String getStart() {
        return startDate;
    }

    public void setStart(String searchParam) {
        this.startDate = searchParam;
        notifyPropertyChanged(BR.start);
    }

    @Bindable
    public String getEnd() {
        return endDate;
    }

    public void setEnd(String searchParam) {
        this.endDate = searchParam;
        notifyPropertyChanged(BR.end);
    }

}
