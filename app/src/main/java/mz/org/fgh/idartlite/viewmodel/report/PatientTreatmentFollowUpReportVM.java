package mz.org.fgh.idartlite.viewmodel.report;

import android.app.Application;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.SearchVM;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.searchparams.AbstractSearchParams;
import mz.org.fgh.idartlite.searchparams.ClinicInformationSearchParams;
import mz.org.fgh.idartlite.service.clinicInfo.ClinicInfoService;
import mz.org.fgh.idartlite.service.clinicInfo.IClinicInfoService;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.reports.PatientTreatmentFollowUpReportActivity;

public class PatientTreatmentFollowUpReportVM extends SearchVM<ClinicInformation> {

    protected String reportType;

    public PatientTreatmentFollowUpReportVM(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void doOnNoRecordFound() {
        Utilities.displayAlertDialog(getRelatedActivity(), "Não foram encontrados resultados para a sua pesquisa").show();
    }

    @Override
    protected IBaseService initRelatedService() {
        return new ClinicInfoService(getApplication());
    }

    @Override
    public IClinicInfoService getRelatedService() {
        return (IClinicInfoService) super.getRelatedService();
    }

    @Override
    protected BaseModel initRecord() {
        return null;
    }

    @Override
    protected void initFormData() {

    }

    @Override
    public void preInit() {

    }

    @Override
    public List<ClinicInformation> doSearch(long offset, long limit) throws SQLException {
        return getRelatedService().getPatientTratmentFollowUpByPeriod(getSearchParams().getStartdate(), getSearchParams().getEndDate(), offset, limit, reportType);
    }

    @Override
    public String validateBeforeSearch() {
        if (getSearchParams().getStartdate() == null || getSearchParams().getEndDate() == null ){
            return "Por favor indicar o período por analisar!";
        }else
        if (DateUtilities.dateDiff(getSearchParams().getEndDate(), getSearchParams().getStartdate(), DateUtilities.DAY_FORMAT) < 0){
            return "A data inicio deve ser menor que a data fim.";
        }else
        if ((int) (DateUtilities.dateDiff(DateUtilities.getCurrentDate(), getSearchParams().getStartdate(), DateUtilities.DAY_FORMAT)) < 0){
            return "A data inicio deve ser menor que a data corrente.";
        }
        else
        if ((int) DateUtilities.dateDiff(DateUtilities.getCurrentDate(), getSearchParams().getEndDate(), DateUtilities.DAY_FORMAT) < 0){
            return "A data fim deve ser menor que a data corrente.";
        }
        return null;
    }


    @Override
    public void displaySearchResults() {
        Utilities.hideSoftKeyboard(getRelatedActivity());

        ((PatientTreatmentFollowUpReportActivity) getRelatedActivity()).displaySearchResult();
    }

    public void generatePDF() {
        try {
            ((PatientTreatmentFollowUpReportActivity)this.getRelatedActivity()).createPdfDocument();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ClinicInformationSearchParams getSearchParams() {
        return (ClinicInformationSearchParams) super.getSearchParams();
    }

    @Override
    public AbstractSearchParams<ClinicInformation> initSearchParams() {
        return new ClinicInformationSearchParams();
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportTitle(){
        if (this.reportType.equals(ClinicInformation.PARAM_FOLLOW_STATUS_ALL)) return "Relatorio de Pacientes monitorados pela adesão";
        else if (this.reportType.equals(ClinicInformation.PARAM_FOLLOW_STATUS_WITH_LATE_DAYS)) return "Pacientes que atrasaram nos levantamentos mais de 7 dias";

        return null;
    }
}
