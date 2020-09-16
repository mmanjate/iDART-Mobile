package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import android.text.TextUtils;
import android.util.Patterns;
import java.sql.SQLException;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.service.UserService;
import mz.org.fgh.idartlite.view.LoginActivity;
import mz.org.fgh.idartlite.view.MainActivity;

public class LoginVM extends BaseViewModel {

    private User user;

    private String successMessage      = "Login was successful!";
    private String successUserCreation = "User was successful create!";
    private String errorMessage        = "User Name or Password not valid!";
    private String mandatoryField      = "User Name and Password are mandatory!";
    private UserService userService;

    @Bindable
    private String toastMessage = null;


    public String getToastMessage() {
        return toastMessage;
    }

    private void setToastMessage(String toastMessage) {

        this.toastMessage = toastMessage;
        notifyPropertyChanged(BR.toastMessage);
    }

    public void setUserName(String userName) {
        user.setUserName(userName);
        notifyPropertyChanged(BR.userName);
    }

    @Bindable
    public String getUserName() {
        return user.getUserName();
    }

    @Bindable
    public String getUserPassword() {
        return user.getPassword();
    }

    public void setUserPassword(String password) {
        user.setPassword(password);
        notifyPropertyChanged(BR.userPassword);
    }

    public LoginVM(@NonNull Application application) {
        super(application);
        user = new User();
        userService = new UserService(getApplication());
    }

     public void login(){
         try {
             if (getUserName().length() == 0 || getUserPassword().length() == 0) {
                 setToastMessage(mandatoryField);
             } else {

                 if (userService.checkIfUsertableIsEmpty()) {
                     User user = new User();
                     user.setUserName("root");
                     user.setPassword("root");
                     userService.saveUser(user);
                     setToastMessage(successUserCreation);
                 } else {
                     if (!userService.login(user)) {
                         Intent intent = new Intent(getApplication(), MainActivity.class);
                         //intent.putEXtra("user");
                         getBaseActivity().startActivity(intent);
                     } else {
                         setToastMessage(errorMessage);
                     }
                 }
             }
         }catch (SQLException e) {
             Log.i("INFO DB", "Erro ao fazer Login" + e.getMessage());
             e.printStackTrace();
         }
    }

    @Override
    public LoginActivity getBaseActivity() {
        return (LoginActivity) super.getBaseActivity();
    }
}
