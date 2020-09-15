package mz.org.fgh.idartlite.base;

import android.app.Application;

import mz.org.fgh.idartlite.dao.IdartLiteDataBaseHelper;

public abstract class BaseService {

    protected IdartLiteDataBaseHelper dataBaseHelper;

    public BaseService(Application application) {
        this.dataBaseHelper = IdartLiteDataBaseHelper.getInstance(application.getApplicationContext());
    }

    public IdartLiteDataBaseHelper getDataBaseHelper() {
        return dataBaseHelper;
    }
}
