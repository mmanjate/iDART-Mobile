package mz.org.fgh.idartlite.base;

import android.app.Application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;

import mz.org.fgh.idartlite.dao.IdartLiteDataBaseHelper;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.ExecutorThreadProvider;

public abstract class BaseService {

    protected IdartLiteDataBaseHelper dataBaseHelper;


    protected static ExecutorService restServiceExecutor;
    public static final String baseUrl = "http://10.10.2.136:3001";

    protected User currentUser;
    protected Application application;
    public static Application app;

    public BaseService(Application application, User currentUser) {
        initServices(application,currentUser);
    }

    public BaseService(Application application) {
        initServices(application,null);
    }

    private void initServices(Application application, User currentUser){
        this.dataBaseHelper = IdartLiteDataBaseHelper.getInstance(application.getApplicationContext());
        restServiceExecutor = ExecutorThreadProvider.getInstance().getExecutorService();

        this.currentUser = currentUser;
        this.application=application;
        BaseService.app = application;
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
    public static  Application getApp(){
        return app;
    }


    public Date getSqlDateFromString(String stringDate, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            Date date = (Date) format.parse(stringDate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Date getUtilDateFromString(String stringDate, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            Date date = (Date) format.parse(stringDate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getStringDateFromDate(Date date, String pattern) {
        SimpleDateFormat datetemp = new SimpleDateFormat(pattern);
        String data = datetemp.format(date);
        return data;

    }

}
