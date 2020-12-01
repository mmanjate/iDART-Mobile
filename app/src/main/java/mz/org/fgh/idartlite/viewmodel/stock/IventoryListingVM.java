package mz.org.fgh.idartlite.viewmodel.stock;

import android.app.Application;

import androidx.annotation.NonNull;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.SearchVM;
import mz.org.fgh.idartlite.model.inventory.Iventory;
import mz.org.fgh.idartlite.service.stock.IIventoryService;
import mz.org.fgh.idartlite.service.stock.IventoryService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.stock.panel.StockInventoryListingFragment;

public class IventoryListingVM extends SearchVM<Iventory> {

    public IventoryListingVM(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void doOnNoRecordFound() {
    }

    @Override
    protected IBaseService initRelatedService() {
        return getServiceProvider().get(IventoryService.class);
    }

    @Override
    protected BaseModel initRecord() {
        return null;
    }

    @Override
    protected void initFormData() {

    }

    @Override
    public Iventory getSelectedRecord() {
        return (Iventory) super.getSelectedRecord();
    }

    @Override
    public void preInit() {

    }

    @Override
    public IIventoryService getRelatedService() {
        return (IventoryService) super.getRelatedService();
    }

    @Override
    public List<Iventory> doSearch(long offset, long limit) throws SQLException {
        return getRelatedService().getAllWithPagination(offset, limit);
    }

    @Override
    public void displaySearchResults() {
        getRelatedFragment().displaySearchResult();
    }

    @Override
    public StockInventoryListingFragment getRelatedFragment() {
        return (StockInventoryListingFragment) super.getRelatedFragment();
    }

    public void deleteRecord(Iventory selectedRecord) throws SQLException {
        getRelatedService().deleteRecord(selectedRecord);
    }

    public void requestForNewRecord(){
        try {
            if (getRelatedService().isLastInventoryOpen()){
                Utilities.displayAlertDialog(getRelatedFragment().getContext(), "O último inventário iniciado ainda não foi terminado, por favor terminar para iniciar novo.").show();
            }else {
                getRelatedFragment().startInventoryActivity();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
