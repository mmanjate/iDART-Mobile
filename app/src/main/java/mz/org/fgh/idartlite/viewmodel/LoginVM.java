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

    private String successMessage = "Login was successful";
    private String errorMessage = "Email or Password not valid";
    private UserService userService;

    @Bindable
    private String toastMessage = null;

    public LoginVM(@NonNull Application application) {
        super(application);
        user = new User();
        userService = new UserService(getApplication(), getCurrentUser());
    }

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



    public boolean isInputDataValid() {
        try {
            if (getUserName().length() == 0 || getUserPassword().length() == 0) {
                setToastMessage("Campo User Name e Password Sao Obrigatorios!");
            } else {

                if (userService.checkIfUsertableIsEmpty()) {
                    User user = new User();
                    user.setUserName("root@root.com");
                    user.setPassword("root");
                    userService.saveUser(user);
                    setToastMessage("User Root Criado!");
                } else {
                    if (!userService.login(user)) {
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        //intent.putEXtra("user");
                        getRelatedActivity().startActivity(intent);
                    } else {
                        setToastMessage("Campo User Name ou Password estao errados!");
                    }
                }
            }
        }catch (SQLException e) {
            Log.i("INFO DB", "Erro ao fazer Login" + e.getMessage());
            e.printStackTrace();
        }
        return !TextUtils.isEmpty(getUserName()) && Patterns.EMAIL_ADDRESS.matcher(getUserName()).matches() && getUserPassword().length() > 5;
    }

    public void login(){
        if (isInputDataValid())
            setToastMessage(successMessage);
        else
            setToastMessage(errorMessage);
    }

    @Override
    public LoginActivity getRelatedActivity() {
        return (LoginActivity) super.getRelatedActivity();
    }

    public void requestUserFromCloud(){
        this.userService.getUsers();
    }
}
