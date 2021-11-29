package mz.org.fgh.idartlite.viewmodel.report;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

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
import mz.org.fgh.idartlite.searchparams.AbstractSearchParams;
import mz.org.fgh.idartlite.service.dispense.DispenseService;
import mz.org.fgh.idartlite.service.dispense.IDispenseService;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.reports.DispenseDrugStatisticReportActivity;
import mz.org.fgh.idartlite.view.reports.DispensesNonSyncReportActivity;

public class DispensesNonSyncReportVM extends SearchVM<Dispense> {


    private IDispenseService dispenseService;

    private String startDate;

    private String endDate;


    public DispensesNonSyncReportVM(@NonNull Application application) {
        super(application);
        dispenseService = new DispenseService(application, getCurrentUser());
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

    public List<Dispense> getDispensesByDates(Date startDate, Date endDate, long offset, long limit) throws SQLException {

        List<Dispense> dispenseList = dispenseService.getDispensesNonSyncBetweenStartDateAndEndDateWithLimit(startDate, endDate,offset,limit);

        return dispenseList;
    }


    @Override
    public void initSearch() {
        if (!Utilities.stringHasValue(startDate) || !Utilities.stringHasValue(endDate)) {
            Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.start_end_date_is_mandatory)).show();
        } else if (DateUtilities.dateDiff(DateUtilities.createDate(endDate, DateUtilities.DATE_FORMAT), DateUtilities.createDate(startDate, DateUtilities.DATE_FORMAT), DateUtilities.DAY_FORMAT) < 0) {
            Utilities.displayAlertDialog(getRelatedActivity(), "A data inicio deve ser menor que a data fim.").show();
        } else if ((int) (DateUtilities.dateDiff(DateUtilities.getCurrentDate(), DateUtilities.createDate(startDate, DateUtilities.DATE_FORMAT), DateUtilities.DAY_FORMAT)) < 0) {
            Utilities.displayAlertDialog(getRelatedActivity(), "A data inicio deve ser menor ou igual que a data corrente.").show();
        } else {

            try {
                super.initSearch();
                if (getAllDisplyedRecords().size() > 0) {
                    getRelatedActivity().generatePdfButton(true);
                } else {
                    Utilities.displayAlertDialog(getRelatedActivity(), "Não foram encontrados resultados para a sua pesquisa").show();
                    getRelatedActivity().generatePdfButton(false);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    @Override
    public void createPdfDocument() {
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

    }


    public List<Dispense> doSearch(long offset, long limit) throws SQLException {

        return getDispensesByDates(DateUtilities.createDate(startDate, DateUtilities.DATE_FORMAT), DateUtilities.createDateWithTime(endDate,DateUtilities.END_DAY_TIME, DateUtilities.DATE_TIME_FORMAT), offset, limit);
    }


    @Override
    public void displaySearchResults() {
        Utilities.hideSoftKeyboard(getRelatedActivity());

        getRelatedActivity().displaySearchResult();
    }

    @Override
    public AbstractSearchParams<Dispense> initSearchParams() {
        return null;
    }

    public DispensesNonSyncReportActivity getRelatedActivity() {
        return (DispensesNonSyncReportActivity) super.getRelatedActivity();
    }

    @Override
    public void preInit() {

    }

    @Bindable
    public Clinic getClinic() {
        return getCurrentClinic();
    }

    @Bindable
    public String getSearchParam() {
        return startDate;
    }

    public void setSearchParam(String searchParam) {
        this.startDate = searchParam;
        notifyPropertyChanged(BR.searchParam);
    }

    @Bindable
    public String getSearchParam2() {
        return endDate;
    }

    public void setSearchParam2(String searchParam) {
        this.endDate = searchParam;
        notifyPropertyChanged(BR.searchParam);
    }

}
