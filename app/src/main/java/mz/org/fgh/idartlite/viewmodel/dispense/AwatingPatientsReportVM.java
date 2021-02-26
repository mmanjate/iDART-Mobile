package mz.org.fgh.idartlite.viewmodel.dispense;

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
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.searchparams.AbstractSearchParams;
import mz.org.fgh.idartlite.searchparams.DispenseSearchParams;
import mz.org.fgh.idartlite.service.dispense.DispenseService;
import mz.org.fgh.idartlite.service.dispense.IDispenseService;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.reports.PatientsAwaitingReportActivity;

public class AwatingPatientsReportVM extends SearchVM<Dispense> {


    public AwatingPatientsReportVM(@NonNull Application application) {
        super(application);
    }

    @Override
    protected IBaseService initRelatedService() {
        return new DispenseService(getApplication());
    }

    @Override
    public IDispenseService getRelatedService() {
        return (IDispenseService) super.getRelatedService();
    }

    @Override
    protected BaseModel initRecord() {
        return null;
    }

    @Override
    protected void initFormData() {}

    public List<Dispense> getDispensesByDates(Date startDate,Date endDate, long offset, long limit) throws SQLException {
        return getRelatedService().getDispensesBetweenNextPickupDateStartDateAndEndDateWithLimit(startDate, endDate,offset,limit);
    }

    @Override
    public String validateBeforeSearch() {
        if (getSearchParams().getStartdate() == null || getSearchParams().getEndDate() == null ){
            return "Por favor indicar o período por analisar!";
        }
        else if (DateUtilities.dateDiff(getSearchParams().getEndDate(), getSearchParams().getStartdate(), DateUtilities.DAY_FORMAT) < 0){
            return "A data inicio deve ser menor que a data fim.";
        }else
        if ((int) (DateUtilities.dateDiff(DateUtilities.getCurrentDate(), getSearchParams().getStartdate(), DateUtilities.DAY_FORMAT)) > 0){
            return "A data inicio deve ser maior ou igual que a data corrente.";
        }

        return null;
    }

    public void generatePDF() {
        try {
            ((PatientsAwaitingReportActivity)getRelatedActivity()).createPdfDocument();
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


    public List<Dispense> doSearch(long offset, long limit) throws SQLException {
        return getDispensesByDates(getSearchParams().getStartdate(), getSearchParams().getEndDate(),offset,limit);
    }

    @Override
    public void displaySearchResults() {
        Utilities.hideSoftKeyboard(getRelatedActivity());

        ((PatientsAwaitingReportActivity)getRelatedActivity()).displaySearchResult();
    }

    @Override
    public AbstractSearchParams<Dispense> initSearchParams() {
        return new DispenseSearchParams();
    }


    @Override
    public void preInit() {}

    @Override
    public DispenseSearchParams getSearchParams() {
        return (DispenseSearchParams) super.getSearchParams();
    }

    @Bindable
    public Clinic getClinic(){
        return getCurrentClinic();
    }

}
