package mz.org.fgh.idartlite.viewmodel.stock;

import android.app.Application;

import androidx.annotation.NonNull;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.SearchVM;
import mz.org.fgh.idartlite.common.ApplicationStep;
import mz.org.fgh.idartlite.model.ReferedStockMoviment;
import mz.org.fgh.idartlite.service.stock.IReferedStockService;
import mz.org.fgh.idartlite.service.stock.ReferedStockMovimentServiceImpl;
import mz.org.fgh.idartlite.view.stock.panel.StockReferenceFragment;

public class ReferedStockMovimentListingVM extends SearchVM<ReferedStockMoviment> {

    public ReferedStockMovimentListingVM(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void doOnNoRecordFound() {

    }

    @Override
    protected IBaseService initRelatedService() {
        return getServiceProvider().get(ReferedStockMovimentServiceImpl.class);
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
    public List<ReferedStockMoviment> doSearch(long offset, long limit) throws SQLException {
        return getRelatedService().getAllWithPagination(offset, limit);
    }

    @Override
    public IReferedStockService getRelatedService() {
        return (IReferedStockService) super.getRelatedService();
    }

    @Override
    public void displaySearchResults() {
        getRelatedFragment().displaySearchResult();
    }

    @Override
    public ReferedStockMoviment getRelatedRecord() {
        return (ReferedStockMoviment) super.getRelatedRecord();
    }

    @Override
    public StockReferenceFragment getRelatedFragment() {
        return (StockReferenceFragment) super.getRelatedFragment();
    }

    public void requestForNewRecord(){
        getRelatedFragment().startReferedStockMovimentActivity(null, ApplicationStep.STEP_CREATE);
    }

    public void deleteRecord(ReferedStockMoviment selectedRecord) throws SQLException {
        getRelatedService().delete(selectedRecord);
    }
}
