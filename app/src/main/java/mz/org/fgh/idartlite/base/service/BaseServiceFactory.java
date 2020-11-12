package mz.org.fgh.idartlite.base.service;

import android.app.Application;

import androidx.annotation.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import mz.org.fgh.idartlite.model.User;

public class BaseServiceFactory<T extends BaseService> {

    private List<T> services;

    private Application mApplication;

    private User mCurrentUser;

    private static BaseServiceFactory instance;

    private BaseServiceFactory(@NonNull Application application) {
        mApplication = application;
    }

    private BaseServiceFactory(@NonNull Application application, User currentUser) {
        mApplication = application;
        mCurrentUser = currentUser;
    }

    public static BaseServiceFactory getInstance(Application application, User user){
        if (instance == null){
            instance = new BaseServiceFactory(application, user);
        }
        return instance;
    }

    public static BaseServiceFactory getInstance(Application application){
        if (instance == null){
            instance = new BaseServiceFactory(application);
        }
        return instance;
    }

    public void clearServices(){
        services.clear();
    }

    public void remove(Class<T> tClass) {
        for (T t : services) {
            if (t.getClass().equals(tClass)) services.remove(t);
        }
    }

    public T get(Class<T> tClass) {
        for (T t : services){
            if (t.getClass().equals(tClass)) return t;
        }

        T service;

        try {
            if (mCurrentUser != null){
                service = tClass.getConstructor(Application.class, User.class).newInstance(mApplication, mCurrentUser);
            }else {
                service = tClass.getConstructor(Application.class).newInstance(mApplication);
            }
            services.add(service);

        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Cannot create an instance of " + tClass, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot create an instance of " + tClass, e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Cannot create an instance of " + tClass, e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Cannot create an instance of " + tClass, e);
        }

        return service;
    }
}
