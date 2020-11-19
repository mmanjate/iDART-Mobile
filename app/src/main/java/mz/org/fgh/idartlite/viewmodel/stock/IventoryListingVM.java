package mz.org.fgh.idartlite.viewmodel.stock;

import android.app.Application;

import androidx.annotation.NonNull;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.viewModel.SearchVM;
import mz.org.fgh.idartlite.model.Iventory;
import mz.org.fgh.idartlite.service.stock.IventoryService;

public class IventoryListingVM extends SearchVM<Iventory> {

    public IventoryListingVM(@NonNull Application application) {
        super(application);
    }

    @Override
    protected BaseModel initRecord() {
        return null;
    }

    @Override
    protected Class<IventoryService> getRecordServiceClass() {
        return IventoryService.class;
    }

    @Override
    protected void initFormData() {

    }

    @Override
    public Iventory getSelectedRecord() {
        return (Iventory) super.getSelectedRecord();
    }

    @Override
    public IventoryService getRecordService() {
        return (IventoryService) super.getRecordService();
    }

    @Override
    public List<Iventory> doSearch(long offset, long limit) throws SQLException {
        return getRecordService().getAllWithPagination(offset, limit);
    }

    @Override
    public void displaySearchResults() {

    }

    public void deleteRecord(Iventory selectedRecord) throws SQLException {
        getRecordService().deleteRecord(selectedRecord);
    }

    public void requestForNewRecord(){

    }
}
