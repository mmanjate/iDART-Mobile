package mz.org.fgh.idartlite.base;

import android.app.Application;

import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.User;

public class BaseRestService {

    protected Application application;
    protected User currentUser;
    protected Clinic currentClinic;

    public BaseRestService(Application application, User currentUser, Clinic currentClinic) {
        this.application = application;
        this.currentUser = currentUser;
        this.currentClinic = currentClinic;
    }


}
