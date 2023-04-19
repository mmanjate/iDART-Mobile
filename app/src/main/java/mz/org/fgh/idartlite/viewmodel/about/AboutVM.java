package mz.org.fgh.idartlite.viewmodel.about;

import android.app.Application;

import androidx.annotation.NonNull;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;

public class AboutVM extends BaseViewModel {

    public AboutVM(@NonNull Application application) {
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

    public void close(){
        getRelatedActivity().finish();
    }
}
