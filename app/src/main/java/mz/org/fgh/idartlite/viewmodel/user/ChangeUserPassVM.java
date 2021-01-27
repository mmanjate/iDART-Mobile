package mz.org.fgh.idartlite.viewmodel.user;

import android.app.Application;

import androidx.annotation.NonNull;

import mz.org.fgh.idartlite.base.model.BaseModel;

public class ChangeUserPassVM extends UserVM {
    public ChangeUserPassVM(@NonNull Application application) {
        super(application);
    }


    @Override
    protected BaseModel initRecord() {
        return currentUser;
    }

   
}