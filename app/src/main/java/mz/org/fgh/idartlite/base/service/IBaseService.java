package mz.org.fgh.idartlite.base.service;

import android.app.Application;

import mz.org.fgh.idartlite.model.User;


public interface IBaseService {

    public void initServices(Application application, User currentUser);


}
