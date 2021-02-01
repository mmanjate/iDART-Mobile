package mz.org.fgh.idartlite.viewmodel.user;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.base.model.BaseModel;

public class ChangeUserPassVM extends UserVM {

    private String userOldPassword;

    public ChangeUserPassVM(@NonNull Application application) {
        super(application);
    }


    @Override
    protected BaseModel initRecord() {
        return currentUser;
    }

    @Bindable
    public String getUserOldPassword() {
        return userOldPassword;
    }

    public void setUserOldPassword(String userOldPassword) {
        this.userOldPassword = userOldPassword;
        notifyPropertyChanged(BR.userOldPassword);
    }
}