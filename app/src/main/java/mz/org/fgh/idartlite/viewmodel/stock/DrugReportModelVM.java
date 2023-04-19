package mz.org.fgh.idartlite.viewmodel.stock;

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
import mz.org.fgh.idartlite.model.DrugReportModel;
import mz.org.fgh.idartlite.searchparams.AbstractSearchParams;
import mz.org.fgh.idartlite.searchparams.StockSearchParams;
import mz.org.fgh.idartlite.service.drug.DrugService;
import mz.org.fgh.idartlite.service.drug.IDrugService;
import mz.org.fgh.idartlite.service.stock.IStockService;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.reports.StockListReportActivity;

public class DrugReportModelVM extends SearchVM<DrugReportModel> {
    private IStockService stockService;
    private IDrugService drugService;

    public DrugReportModelVM(@NonNull Application application) {
        super(application);
        drugService = new DrugService(application, getCurrentUser());
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

    public List<DrugReportModel> getStockListByDates(Date startDate, Date endDate) throws SQLException {
        return drugService.getAllWithLoteAndNotExpired(startDate, endDate);
    }

    @Override
    public String validateBeforeSearch() {

        if (getSearchParams().getStartdate() == null || getSearchParams().getEndDate() == null ){
            return "Por favor indicar o período por analisar!";
        }
        else if (DateUtilities.dateDiff(getSearchParams().getEndDate(), getSearchParams().getStartdate(), DateUtilities.DAY_FORMAT) < 0){
            return "A data inicio deve ser menor que a data fim.";
        }else
        if ((int) (DateUtilities.dateDiff(DateUtilities.getCurrentDate(), getSearchParams().getStartdate(), DateUtilities.DAY_FORMAT)) < 0){
            return "A data inicio deve ser menor que a data corrente";
        }
        else
        if ((int) DateUtilities.dateDiff(DateUtilities.getCurrentDate(), getSearchParams().getEndDate(), DateUtilities.DAY_FORMAT) < 0){
            return "A data fim deve ser menor ou igual que a data corrente.";
        }
        return null;
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
        Utilities.displayAlertDialog(getRelatedActivity(), "Não foram encontrados resultados para a sua pesquisa").show();
    }


    public List<DrugReportModel> doSearch(long offset, long limit) throws SQLException {

        return getStockListByDates(getSearchParams().getStartdate(), getSearchParams().getEndDate());
    }



    @Override
    public void displaySearchResults() {
        Utilities.hideSoftKeyboard(getRelatedActivity());

        getRelatedActivity().displaySearchResult();
    }

    @Override
    public StockSearchParams getSearchParams() {
        return (StockSearchParams) super.getSearchParams();
    }

    @Override
    public AbstractSearchParams<DrugReportModel> initSearchParams() {
        return new StockSearchParams();
    }


    public StockListReportActivity getRelatedActivity() {
        return (StockListReportActivity) super.getRelatedActivity();
    }

    @Override
    public void preInit() {

    }

    @Bindable
    public Clinic getClinic(){
        return getCurrentClinic();
    }


    @Bindable
    public Clinic getDrugList(){
        return getCurrentClinic();
    }
}
