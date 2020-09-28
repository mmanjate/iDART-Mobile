package mz.org.fgh.idartlite.base;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mz.org.fgh.idartlite.dao.IdartLiteDataBaseHelper;
import mz.org.fgh.idartlite.model.User;

public abstract class BaseService {

    protected IdartLiteDataBaseHelper dataBaseHelper;

    private static final int NUMBER_OF_THREADS = 4;
    protected static ExecutorService restServiceExecutor;

    protected User currentUser;
    protected Application application;

    public BaseService(Application application, User currentUser) {
        this.dataBaseHelper = IdartLiteDataBaseHelper.getInstance(application.getApplicationContext());
        restServiceExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        this.currentUser = currentUser;
        this.application=application;
    }

    protected IdartLiteDataBaseHelper getDataBaseHelper() {
        return dataBaseHelper;
    }

    public static ExecutorService getRestServiceExecutor() {
        return restServiceExecutor;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Application getApplication(){
        return application;
    }
}
