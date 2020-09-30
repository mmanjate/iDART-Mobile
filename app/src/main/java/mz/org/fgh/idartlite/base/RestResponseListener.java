package mz.org.fgh.idartlite.base;

public interface RestResponseListener {

    void doOnRestSucessResponse(String flag);

    void doOnRestErrorResponse(String errormsg);
}
