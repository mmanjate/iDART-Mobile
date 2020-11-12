package mz.org.fgh.idartlite.base.service;

import android.app.Application;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;

import mz.org.fgh.idartlite.dao.IdartLiteDataBaseHelper;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.rest.ExecutorThreadProvider;
import mz.org.fgh.idartlite.util.Utilities;

public abstract class BaseService {

    protected IdartLiteDataBaseHelper dataBaseHelper;


    protected static ExecutorService restServiceExecutor;
    public static final String baseUrl = "http://dev.fgh.org.mz:3110";

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
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.ROOT);
        try {
            Date date = (Date) format.parse(stringDate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Date getUtilDateFromString(String stringDate, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern,Locale.ROOT);
        try {
            Date date = (Date) format.parse(stringDate);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getStringDateFromDate(Date date, String pattern) {
        SimpleDateFormat datetemp = new SimpleDateFormat(pattern,Locale.ROOT);
        String data = datetemp.format(date);
        return data;

    }

    protected static String generateErrorMsg(VolleyError error){
        if (error instanceof NetworkError) {
            return "A network error as occured ";
        } else if (error instanceof ServerError) {
            return "A server error as occured ";
        } else if (error instanceof AuthFailureError) {
            return "An authentication error as occured ";
        } else if (error instanceof ParseError) {
            return "A parse error as occured ";
        } else if (error instanceof NoConnectionError) {
            return "No connection ";
        } else if (error instanceof TimeoutError) {
            return "Connection timeout";
        }
        return Utilities.stringHasValue(error.getMessage()) ? error.getMessage() : "Erro desconhecido";
    }


}
