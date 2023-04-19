package mz.org.fgh.idartlite.viewmodel.clinicInfo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.SearchVM;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.searchparams.AbstractSearchParams;
import mz.org.fgh.idartlite.searchparams.ClinicInformationSearchParams;
import mz.org.fgh.idartlite.service.clinicInfo.ClinicInfoService;
import mz.org.fgh.idartlite.service.clinicInfo.IClinicInfoService;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.reports.PregnantPatientReportActivity;
import mz.org.fgh.idartlite.view.reports.TBTracedPatientReportActivity;

public class TBTracedPatientReportVM extends SearchVM<ClinicInformation> {


    private IClinicInfoService clinicInfoService;

    private String reportType;

    public TBTracedPatientReportVM(@NonNull Application application) {
        super(application);
        clinicInfoService = new ClinicInfoService(application, getCurrentUser());


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

    public List<ClinicInformation> getTracedPatientsWithStartDateAndEndDateWithLimit(Date startDate,Date endDate, long offset, long limit) throws SQLException {
        return clinicInfoService.getTracedPatientsWithStartDateAndEndDateWithLimit(startDate, endDate,offset,limit,reportType);
    }

    public void generatePDF() {
        try {
            super.generatePDF();
            ((TBTracedPatientReportActivity)getRelatedActivity()).createPdfDocument();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doOnNoRecordFound() {

        Utilities.displayAlertDialog(getRelatedActivity(), "Não foram encontrados resultados para a sua pesquisa").show();

    }


    public List<ClinicInformation> doSearch(long offset, long limit) throws SQLException {
        return getTracedPatientsWithStartDateAndEndDateWithLimit(getSearchParams().getStartdate(), getSearchParams().getEndDate(),offset,limit);
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

        ((TBTracedPatientReportActivity)getRelatedActivity()).displaySearchResult();
    }

    @Override
    public AbstractSearchParams<ClinicInformation> initSearchParams() {
        return new ClinicInformationSearchParams();
    }

    @Override
    public void preInit() {}

    @Override
    public ClinicInformationSearchParams getSearchParams() {
        return (ClinicInformationSearchParams) super.getSearchParams();
    }


  /*  public TBTracedPatientReportActivity getRelatedActivity() {
        return (TBTracedPatientReportActivity) super.getRelatedActivity();
    }*/



    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportTitle(){
        if (getReportType().equals(ClinicInformation.TB_STATUS_SUSPECT)) return getRelatedActivity().getString(R.string.pacientes_suspeitos_tb_report_title);
        else if (getReportType().equals(ClinicInformation.TB_STATUS_ALL)) return getRelatedActivity().getString(R.string.pacientes_rastreados_tb_report_title);

        return null;
    }

    public String getReportTableTitle(){
        if (reportType.equals(ClinicInformation.TB_STATUS_SUSPECT)) return getRelatedActivity().getString(R.string.pacientes_suspeitos_tb);
        else if (reportType.equals(ClinicInformation.TB_STATUS_ALL)) return getRelatedActivity().getString(R.string.pacientes_rastreados_tb);

        return null;
    }


    @Bindable
    public Clinic getClinic(){
        return getCurrentClinic();
    }

}
