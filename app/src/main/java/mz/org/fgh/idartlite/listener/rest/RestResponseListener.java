package mz.org.fgh.idartlite.listener.rest;

public interface RestResponseListener {

    void doOnRestSucessResponse(String flag);

    void doOnRestErrorResponse(String errormsg);
}
