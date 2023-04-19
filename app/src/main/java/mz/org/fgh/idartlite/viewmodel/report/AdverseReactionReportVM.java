package mz.org.fgh.idartlite.viewmodel.report;

import android.app.Application;

import androidx.annotation.NonNull;

import com.itextpdf.text.DocumentException;

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
import mz.org.fgh.idartlite.view.reports.AdverseReactionReportActivity;

public class AdverseReactionReportVM extends SearchVM<ClinicInformation> {

    private String reportType;

    public AdverseReactionReportVM(@NonNull Application application) {
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
        return getRelatedService().getRAMsByPeriod(getSearchParams().getStartdate(), getSearchParams().getEndDate(), offset, limit, reportType);
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
    public AdverseReactionReportActivity getRelatedActivity() {
        return (AdverseReactionReportActivity) super.getRelatedActivity();
    }

    @Override
    public void displaySearchResults() {
        Utilities.hideSoftKeyboard(getRelatedActivity());

        getRelatedActivity().displaySearchResult();
    }

    @Override
    public ClinicInformationSearchParams getSearchParams() {
        return (ClinicInformationSearchParams) super.getSearchParams();
    }

    @Override
    public AbstractSearchParams<ClinicInformation> initSearchParams() {
        return new ClinicInformationSearchParams();
    }

    @Override
    public void createPdfDocument() {
        try {
            this.getRelatedActivity().createPdfDocument();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportTitle(){
        if (this.reportType.equals(ClinicInformation.PARAM_RAM_STATUS_ALL)) return "Relatorio de Pacientes que rastrearam para RAMs";
        else if (this.reportType.equals(ClinicInformation.PARAM_RAM_STATUS_POSETIVE)) return "Relatorio de Pacientes com RAMs";

        return null;
    }
}
