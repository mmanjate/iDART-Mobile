package mz.org.fgh.idartlite.base.service;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.IdartLiteDataBaseHelper;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.helper.ExecutorThreadProvider;


public interface IBaseService {

    public void initServices(Application application, User currentUser);


}
