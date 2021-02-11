package mz.org.fgh.idartlite.viewmodel.user;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import java.sql.SQLException;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.service.user.IUserService;
import mz.org.fgh.idartlite.service.user.UserService;
import mz.org.fgh.idartlite.util.Utilities;

public class UserVM extends BaseViewModel {

    private String userPassRepeat;

    public UserVM(@NonNull Application application) {
        super(application);
    }

    @Override
    protected IBaseService initRelatedService() {
        return new UserService(getApplication());
    }

    @Override
    protected BaseModel initRecord() {
        return new User();
    }

    @Override
    public User getRelatedRecord() {
        return (User) super.getRelatedRecord();
    }

    @Override
    public IUserService getRelatedService() {
        return (IUserService) super.getRelatedService();
    }

    public void save(){
        String errors = getRelatedRecord().isValid(getRelatedFragment().getContext());

        if (!Utilities.stringHasValue(errors)) {
            if (!getRelatedRecord().getPassword().equals(getUserPassRepeat())){
                Utilities.displayAlertDialog(getRelatedFragment().getContext(), "As senhas indicadas não conferem, por favor verificar.").show();
            }else {
                try {
                    getRelatedRecord().setClinic(getCurrentClinic());

                    if (getRelatedRecord().getId() > 0){
                        getRelatedRecord().setSyncStatus(BaseModel.SYNC_SATUS_UPDATED);
                    }else {
                        getRelatedRecord().setSyncStatus(BaseModel.SYNC_SATUS_READY);
                    }

                    getRelatedService().save(getRelatedRecord());

                    Utilities.displayAlertDialog(getRelatedFragment().getContext(), "Operação efectuada com sucesso!").show();

                    setSelectedRecord(new User());

                } catch (SQLException e) {
                    Utilities.displayAlertDialog(getRelatedFragment().getContext(), getRelatedFragment().getContext().getString(R.string.generic_error_msg) + " " + e.getMessage()).show();
                    e.printStackTrace();
                }
            }
        }else {
            Utilities.displayAlertDialog(getRelatedFragment().getContext(), errors).show();
        }
    }

    @Override
    protected void initFormData() {

    }

    @Override
    public void preInit() {

    }

    @Bindable
    public String getUserName(){
        return getRelatedRecord().getUserName();
    }

    public void setUserName(String userName){
        getRelatedRecord().setUserName(userName);
        notifyPropertyChanged(BR.userName);
    }

    @Bindable
    public String getUserPassword(){
        return getRelatedRecord().getPassword();
    }

    public void setUserPassword(String userPass){
        getRelatedRecord().setPassword(userPass);
        notifyPropertyChanged(BR.userPassword);
    }

    @Bindable
    public String getUserPassRepeat() {
        return userPassRepeat;
    }

    public void setUserPassRepeat(String userPassRepeat) {
        this.userPassRepeat = userPassRepeat;
        notifyPropertyChanged(BR.userPassRepeat);
    }

    public void changeDataViewStatus(View view){

    }
}
