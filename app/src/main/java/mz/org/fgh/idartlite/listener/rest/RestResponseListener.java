package mz.org.fgh.idartlite.listener.rest;

import java.util.List;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.rest.ServiceWatcher;

public interface RestResponseListener<T extends BaseModel> {

    void doOnRestSucessResponse(String flag);

    void doOnRestErrorResponse(String errormsg);

    void doOnRestSucessResponseObject(String flag, T object);

   void doOnRestSucessResponseObjects(String flag, List<T> objects);

    boolean registRunningService(ServiceWatcher serviceWatcher);

    void updateServiceStatus(ServiceWatcher serviceWatcher);
}
