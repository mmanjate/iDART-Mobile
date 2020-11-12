package mz.org.fgh.idartlite.base.rest;

import android.app.Application;

import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.User;

public abstract class BaseRestService {

    protected Application application;
    protected User currentUser;
    protected Clinic currentClinic;

    public BaseRestService(Application application, User currentUser, Clinic currentClinic) {
        this.application = application;
        this.currentUser = currentUser;
        this.currentClinic = currentClinic;
    }

    public BaseRestService(Application application, User currentUser) {
        this.application = application;
        this.currentUser = currentUser;
    }

    public BaseRestService(Application application) {
        this.application = application;
    }


}
