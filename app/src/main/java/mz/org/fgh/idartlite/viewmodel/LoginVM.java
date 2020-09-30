package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.service.ClinicService;
import mz.org.fgh.idartlite.service.UserService;
import mz.org.fgh.idartlite.view.HomeActivity;
import mz.org.fgh.idartlite.view.LoginActivity;
import mz.org.fgh.idartlite.view.SecondSplashActivity;
public class LoginVM extends BaseViewModel {
    private User user;
    private Clinic clinic;
    private UserService userService;
    private ClinicService clinicService;
    private String successMessage = "Login was successful!";
    private String successUserCreation = "User was successful create!";
    private String errorMessage = "User Name or Password not valid!";
    private String restErrorMessage = "Rest Access: User Name or Password not valid!";
    private String mandatoryField = "User Name and Password are mandatory!";
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
    @Bindable
    public Clinic getClinic() {
        return clinic;
    }
    public void setClinic(Clinic c) {
        clinic = c;
        notifyPropertyChanged(BR.clinic);
    }
    public LoginVM(@NonNull Application application) throws SQLException {
        super(application);
        user = new User();
        clinic = currentClinic;
        userService = new UserService(getApplication(), this.user);
        clinicService = new ClinicService(getApplication(), this.user);
    }
    public void login() {
        try {
            if (getUserName() != null && getUserPassword() != null) {
                if (getUserName().length() == 0 || getUserPassword().length() == 0) {
                    setToastMessage(mandatoryField);
                } else {
                    if (userService.checkIfUsertableIsEmpty()) {
                        Intent intent = new Intent(getApplication(), SecondSplashActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user", this.user);
                        bundle.putSerializable("clinic", currentClinic);
                        intent.putExtras(bundle);
                        getRelatedActivity().startActivity(intent);
                    } else {
                        if (!userService.login(user)) {
                            if (!clinicService.getCLinic().isEmpty()) {
                                clinic = clinicService.getCLinic().get(0);
                            }
                            Intent intent = new Intent(getApplication(), HomeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user", user);
                            bundle.putSerializable("clinic", clinic);
                            intent.putExtras(bundle);
                            getRelatedActivity().startActivity(intent);
                            setToastMessage(successMessage);
                        } else {
                            setToastMessage(errorMessage);
                        }
                    }
                }
            } else
                setToastMessage(mandatoryField);
        } catch (SQLException e) {
            Log.i("INFO DB", "Erro ao fazer Login: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public LoginActivity getRelatedActivity() {
        return (LoginActivity) super.getRelatedActivity();
    }
}