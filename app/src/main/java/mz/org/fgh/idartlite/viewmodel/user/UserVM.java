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
import mz.org.fgh.idartlite.view.home.ui.user.AddUserFragment;

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
    public AddUserFragment getRelatedFragment() {
        return (AddUserFragment) super.getRelatedFragment();
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
            try {
                getRelatedRecord().setClinic(getCurrentClinic());
                getRelatedService().save(getRelatedRecord());

                Utilities.displayAlertDialog(getRelatedFragment().getContext(), "O seu pedido de acesso foi enviado com sucesso!").show();
            } catch (SQLException e) {
                Utilities.displayAlertDialog(getRelatedFragment().getContext(), getRelatedFragment().getContext().getString(R.string.generic_error_msg) + " " + e.getMessage()).show();
                e.printStackTrace();
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
