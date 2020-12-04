package mz.org.fgh.idartlite.listener.rest;

import java.util.List;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.model.Patient;

public interface RestResponseListener<T extends BaseModel> {

    void doOnRestSucessResponse(String flag);

    void doOnRestErrorResponse(String errormsg);

    void doOnRestSucessResponseObject(String flag, T object);

   void doOnRestSucessResponseObjects(String flag, List<T> objects);
}
