package mz.org.fgh.idartlite.viewmodel.user;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.util.Utilities;

public class ChangeUserPassVM extends UserVM {

    protected String userOldPassword;

    protected String userNewPassword;

    public ChangeUserPassVM(@NonNull Application application) {
        super(application);
    }


    @Override
    protected BaseModel initRecord() {
        return currentUser;
    }

    @Override
    public void preInit() {
        setSelectedRecord(getRelatedService().getByUserNameAndPassword(getCurrentUser()));
    }

    @Bindable
    public String getUserOldPassword() {
        return userOldPassword;
    }

    public void setUserOldPassword(String userOldPassword) {
        this.userOldPassword = userOldPassword;
        notifyPropertyChanged(BR.userOldPassword);
    }

    @Bindable
    public String getUserNewPassword() {
        return userNewPassword;
    }

    public void setUserNewPassword(String userNewPassword) {
        this.userNewPassword = userNewPassword;
        notifyPropertyChanged(BR.userNewPassword);
    }

    @Override
    public void save() {
        if (Utilities.MD5Crypt(userOldPassword).equals(getRelatedRecord().getPassword())){
            if (!userNewPassword.equals(getUserPassRepeat())){
                Utilities.displayAlertDialog(getRelatedFragment().getContext(), "As senhas indicadas não conferem, por favor verificar.").show();
            }else {
                getRelatedRecord().setPassword(userNewPassword);
                super.save();
            }
        }else Utilities.displayAlertDialog(getRelatedFragment().getContext(), "A senha anterior indicada é inválida.").show();
    }
}