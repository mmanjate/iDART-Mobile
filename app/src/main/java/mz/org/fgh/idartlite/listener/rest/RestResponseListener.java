package mz.org.fgh.idartlite.listener.rest;

import java.util.List;

import mz.org.fgh.idartlite.base.model.BaseModel;

public interface RestResponseListener<T extends BaseModel> {

    void doOnRestSucessResponse(String flag);

    void doOnRestErrorResponse(String errormsg);

    void doOnRestSucessResponseObject(String flag, T object);

   void doOnRestSucessResponseObjects(String flag, List<T> objects);

    default void doOnResponse(String flag, List<T> objects) {

    }
}
