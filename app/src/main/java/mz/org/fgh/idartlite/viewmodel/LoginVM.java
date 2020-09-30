package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.service.ClinicService;
import mz.org.fgh.idartlite.service.UserService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.HomeActivity;
import mz.org.fgh.idartlite.view.LoginActivity;
import mz.org.fgh.idartlite.view.SecondSplashActivity;

public class LoginVM extends BaseViewModel {
    private UserService userService;
    private ClinicService clinicService;
    private String successMessage = "Login was successful!";
    private String successUserCreation = "User was successful create!";
    private String errorMessage = "User Name or Password not valid!";
    private String restErrorMessage = "Rest Access: User Name or Password not valid!";
    private String mandatoryField = "User Name and Password are mandatory!";
    private boolean userAuthentic = false;

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
            currentUser.setUserName(userName);
        notifyPropertyChanged(BR.userName);
    }

    @Bindable
    public String getUserName() {
            return currentUser.getUserName();
    }

    @Bindable
    public String getUserPassword() {
            return currentUser.getPassword();

    }

    public void setUserPassword(String password) {
            currentUser.setPassword(password);
        notifyPropertyChanged(BR.userPassword);

    }

    @Bindable
    public Clinic getClinic() {
        return currentClinic;
    }

    public void setClinic(Clinic c) {
        currentClinic = c;
        notifyPropertyChanged(BR.clinic);
    }

    public boolean isUserAuthentic() {
        return userAuthentic;
    }

    public void setUserAuthentic(boolean userAuthentic) {
        this.userAuthentic = userAuthentic;
    }

    public LoginVM(@NonNull Application application) throws SQLException {
        super(application);
        this.currentUser = new User();

        userService = new UserService(getApplication(), getCurrentUser());
        clinicService = new ClinicService(getApplication(), getCurrentUser());
    }

    public void saveLogingUser() throws SQLException {
        currentUser.setClinic(clinicService.getCLinic().get(0));
        userService.saveUser(currentUser);
    }

    public void saveUserClinic() throws SQLException {
        clinicService.saveClinic(currentClinic);
    }

    public void login() {

        getRelatedActivity().setCurrentUser(currentUser);
        getRelatedActivity().setCurrentClinic(getCurrentClinic());

        try {
            if (getUserName() != null && getUserPassword() != null) {
                if (getUserName().length() == 0 || getUserPassword().length() == 0) {
                    setToastMessage(mandatoryField);
                } else {
                    if (userService.checkIfUsertableIsEmpty()) {
                        getRelatedActivity().runRestUserAccess();

                    } else {
                        if (!userService.login(currentUser)) {
                            if (!clinicService.getCLinic().isEmpty()) {
                                currentClinic = clinicService.getCLinic().get(0);
                            }
                            moveToHome();
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

    public void saveUserSettingsAndProcced() throws SQLException {
        saveUserClinic();
        saveLogingUser();

        moveToSecondSplsh();
    }

    public void moveToSecondSplsh() {
        Map<String, Object> params = new HashMap<>();
        params.put("user", this.currentUser);
        params.put("clinic", currentClinic);
        getRelatedActivity().nextActivity(SecondSplashActivity.class, params);
    }

    private void moveToHome() {
        Map<String, Object> params = new HashMap<>();
        params.put("user", this.currentUser);
        params.put("clinic", currentClinic);
        getRelatedActivity().nextActivity(HomeActivity.class, params);
    }
}