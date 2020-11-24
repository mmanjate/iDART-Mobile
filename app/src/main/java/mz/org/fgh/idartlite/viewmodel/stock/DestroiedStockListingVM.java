package mz.org.fgh.idartlite.viewmodel.stock;

import android.app.Application;

import androidx.annotation.NonNull;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.SearchVM;
import mz.org.fgh.idartlite.model.DestroyedDrug;
import mz.org.fgh.idartlite.service.stock.DestroyedStockDrugService;
import mz.org.fgh.idartlite.view.stock.panel.DestroiedStockListFragment;
import mz.org.fgh.idartlite.view.stock.panel.StockActivity;

public class DestroiedStockListingVM extends SearchVM<DestroyedDrug> {

    public DestroiedStockListingVM(@NonNull Application application) {
        super(application);
    }

    @Override
    protected IBaseService initRelatedService() {
        return getServiceProvider().get(DestroyedStockDrugService.class);
    }

    @Override
    protected void doOnNoRecordFound() {

    }

    @Override
    protected BaseModel initRecord() {
        return null;
    }

    @Override
    protected void initFormData() {

    }

    @Override
    public DestroyedDrug getSelectedRecord() {
        return (DestroyedDrug) super.getSelectedRecord();
    }

    @Override
    public DestroyedStockDrugService getRelatedService() {
        return (DestroyedStockDrugService) super.getRelatedService();
    }

    @Override
    public List<DestroyedDrug> doSearch(long offset, long limit) throws SQLException {
        return getRelatedService().getRecords(offset, limit);
    }

    @Override
    public StockActivity getRelatedActivity() {
        return (StockActivity) super.getRelatedActivity();
    }

    @Override
    public DestroiedStockListFragment getRelatedFragment() {
        return (DestroiedStockListFragment) super.getRelatedFragment();
    }

    @Override
    public void displaySearchResults() {
        getRelatedFragment().displaySearchResult();
    }

    public void deleteRecord(DestroyedDrug selectedRecord) throws SQLException {
        getRelatedService().deleteRecord(selectedRecord);
    }


    public void requestForNewRecord(){
        getRelatedFragment().startDestroyStockActivity();
    }
}
