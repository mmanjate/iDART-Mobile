package mz.org.fgh.idartlite.viewmodel.stock;

import android.app.Application;

import androidx.annotation.NonNull;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;

public class StockReferenceVM extends BaseViewModel {

    public StockReferenceVM(@NonNull Application application) {
        super(application);
    }

    @Override
    protected BaseModel initRecord() {
        return null;
    }

    @Override
    protected <T extends BaseService> Class<T> getRecordServiceClass() {
        return null;
    }

    @Override
    protected void initFormData() {

    }
    // TODO: Implement the ViewModel
}