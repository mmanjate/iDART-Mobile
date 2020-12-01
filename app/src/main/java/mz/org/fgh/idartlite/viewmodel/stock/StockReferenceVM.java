package mz.org.fgh.idartlite.viewmodel.stock;

import android.app.Application;

import androidx.annotation.NonNull;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;

public class StockReferenceVM extends BaseViewModel {

    public StockReferenceVM(@NonNull Application application) {
        super(application);
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

    @Override
    public void preInit() {

    }
    // TODO: Implement the ViewModel
}