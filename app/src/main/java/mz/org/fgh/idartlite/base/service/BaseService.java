package mz.org.fgh.idartlite.base.service;

import android.app.Application;

import java.sql.SQLException;

import mz.org.fgh.idartlite.base.databasehelper.IdartLiteDataBaseHelper;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.util.Utilities;

public abstract class BaseService<T extends BaseModel> implements IBaseService<T>{

    protected IdartLiteDataBaseHelper dataBaseHelper;

    protected User currentUser;
    protected Application application;
    public static Application app;

    public BaseService(Application application, User currentUser) {
        init(application,currentUser);
    }

    public BaseService(Application application) {
        init(application,null);
    }

    public void init(Application application, User currentUser){
        this.dataBaseHelper = IdartLiteDataBaseHelper.getInstance(application.getApplicationContext());

        this.currentUser = currentUser;
        this.application=application;
        BaseService.app = application;
    }

    protected IdartLiteDataBaseHelper getDataBaseHelper() {
        return dataBaseHelper;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Application getApplication(){
        return application;
    }
    public static  Application getApp(){
        return app;
    }

    @Override
    public void save(T record) throws SQLException {
        String errors = record.isValid(getApplication().getApplicationContext());

        if (Utilities.stringHasValue(errors)) {
            Utilities.displayAlertDialog(getApplication().getApplicationContext(), errors).show();
            return;
        }
    }

    @Override
    public void update(T relatedRecord) throws SQLException {
        String errors = relatedRecord.isValid(getApplication().getApplicationContext());

        if (Utilities.stringHasValue(errors)) {
            Utilities.displayAlertDialog(getApplication().getApplicationContext(), errors).show();
            return;
        }
    }

    @Override
    public void deleteRecord(T selectedRecord) throws SQLException {
        String errors = selectedRecord.canBeRemoved(getApplication().getApplicationContext());

        if (Utilities.stringHasValue(errors)) {
            Utilities.displayAlertDialog(getApplication().getApplicationContext(), errors).show();
            return;
        }
    }
}
