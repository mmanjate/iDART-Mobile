package mz.org.fgh.idartlite.viewmodel.clinicInfo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.SearchVM;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.searchparams.AbstractSearchParams;
import mz.org.fgh.idartlite.searchparams.ClinicInfoSearchParams;
import mz.org.fgh.idartlite.service.clinicInfo.ClinicInfoService;
import mz.org.fgh.idartlite.service.clinicInfo.IClinicInfoService;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.reports.PregnantPatientReportActivity;

public class PregnantPatientReportVM extends SearchVM<ClinicInformation> {


    private IClinicInfoService clinicInfoService;

    public PregnantPatientReportVM(@NonNull Application application) {
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

    public List<ClinicInformation> getPregnantPatientWithStartDateAndEndDate(Date startDate,Date endDate, long offset, long limit) throws SQLException {
        return clinicInfoService.getPregnantPatientWithStartDateAndEndDateWithLimit(startDate, endDate,offset,limit);
    }

    public void generatePDF() {
        try {
            this.getRelatedActivity().createPdfDocument();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doOnNoRecordFound() {

        if(getAllDisplyedRecords().size()>0){
            getRelatedActivity().generatePdfButton(true);
        }
        else {
            Utilities.displayAlertDialog(getRelatedActivity(), "Não foram encontrados resultados para a sua pesquisa").show();
            getRelatedActivity().generatePdfButton(false);
        }

    }


    public List<ClinicInformation> doSearch(long offset, long limit) throws SQLException {
        return getPregnantPatientWithStartDateAndEndDate(getSearchParams().getStartdate(), getSearchParams().getEndDate(),offset,limit);
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

        getRelatedActivity().displaySearchResult();
    }

    @Override
    public AbstractSearchParams<ClinicInformation> initSearchParams() {
        return new ClinicInfoSearchParams();
    }

    @Override
    public void preInit() {}

    @Override
    public ClinicInfoSearchParams getSearchParams() {
        return (ClinicInfoSearchParams) super.getSearchParams();
    }


    public PregnantPatientReportActivity getRelatedActivity() {
        return (PregnantPatientReportActivity) super.getRelatedActivity();
    }



    @Bindable
    public Clinic getClinic(){
        return getCurrentClinic();
    }

}
