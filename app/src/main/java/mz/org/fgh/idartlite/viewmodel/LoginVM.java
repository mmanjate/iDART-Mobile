package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.TaskSchedule.restTaskSchedule.ExecutePostWorkerScheduler;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.common.Listble;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.service.ClinicService;
import mz.org.fgh.idartlite.service.UserService;
import mz.org.fgh.idartlite.service.restService.RestPatientService;
import mz.org.fgh.idartlite.service.restService.RestUserService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.HomeActivity;
import mz.org.fgh.idartlite.view.LoginActivity;
import mz.org.fgh.idartlite.view.SecondSplashActivity;

public class LoginVM extends BaseViewModel {
    private RestUserService restUserService;
    private UserService userService;
    private ClinicService clinicService;

    private Clinic selectedClinic;

    private List<Clinic> clinicList;

    private boolean authenticating;

    private boolean remeberMe;

    public LoginVM(@NonNull Application application) {
        super(application);
        this.currentUser = new User();

        userService = new UserService(getApplication(), getCurrentUser());
        restUserService = new RestUserService(getApplication(), getCurrentUser());
        clinicService = new ClinicService(getApplication(), getCurrentUser());
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



    public void saveLogingUser() throws SQLException {
        currentUser.setClinic(clinicService.getCLinic().get(0));
        userService.saveUser(currentUser);
    }

    public void saveUserClinic() throws SQLException {
        clinicService.saveClinic(currentClinic);
    }

    public void login() {
        getRelatedActivity().changeViewToAuthenticatingMode();

        if (getCurrentClinic() == null || getCurrentClinic().getId() < 0){
            getRelatedActivity().changeViewToNormalMode();
            Utilities.displayAlertDialog(getRelatedActivity(), "O campo Farmácia deve ser preenchido.").show();

        }

        getCurrentUser().setUserName(getUserName().trim());
        getRelatedActivity().setCurrentUser(currentUser);
        getRelatedActivity().setCurrentClinic(getCurrentClinic());

        String loginErrors = getCurrentUser().validadeToLogin();


        try {
            if (!Utilities.stringHasValue(loginErrors)) {
                if (userService.checkIfUsertableIsEmpty()) {
                    runRestUserAccess();

                } else {
                    // colocar no lugar certo
                    RestPatientService.restGetAllPatient(null);
                    if (!userService.login(currentUser)) {
                        if (Utilities.listHasElements(clinicService.getCLinic())) {
                            currentClinic = clinicService.getCLinic().get(0);
                            getRelatedActivity().changeViewToNormalMode();
                            moveToHome();
                            this.getRelatedActivity().savingSharedPreferences();
                        }else {
                            getRelatedActivity().changeViewToNormalMode();
                            Utilities.displayAlertDialog(getRelatedActivity(), "Não foi encontrada a configuração da farmácia.").show();
                        }
                    } else {
                        getRelatedActivity().changeViewToNormalMode();
                        Utilities.displayAlertDialog(getRelatedActivity(), "Utilizador e/ou senha inválida").show();
                    }
                }
            } else
                getRelatedActivity().changeViewToNormalMode();

                Utilities.displayAlertDialog(getRelatedActivity(), loginErrors);
        } catch (SQLException e) {
            getRelatedActivity().changeViewToNormalMode();
            Utilities.displayAlertDialog(getRelatedActivity(), "Ocorreu um erro ao autenticar os dados, "+e.getLocalizedMessage()).show();
            Log.i("INFO DB", "Erro ao fazer Login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void runRestUserAccess(){
        restUserService.getUserAuthentication(currentClinic.getUuid(), currentUser.getUserName(), currentUser.getPassword(), getRelatedActivity());
    }

    public boolean appHasUsersOnDB(){
        boolean usersOnDB = false;
        try {
            usersOnDB = userService.checkIfUsertableIsEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
            Utilities.displayAlertDialog(getRelatedActivity(), "Ocorreu um erro ao verificar as configuração de utilizadores.").show();
        }
        return usersOnDB;
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

    @Bindable
    public Listble getSelectedClinic() {
        return selectedClinic;
    }

    public void setSelectedClinic(Listble selectedClinic) {
        this.selectedClinic = (Clinic) selectedClinic;
        setCurrentClinic(this.selectedClinic);
        notifyPropertyChanged(BR.selectedClinic);
    }

    @Bindable
    public List<Clinic> getClinicList() {
        return clinicList;
    }

    public void setClinicList(List<Clinic> clinicList) {
        this.clinicList = clinicList;
        notifyPropertyChanged(BR.clinicList);
    }

    @Bindable
    public boolean isAuthenticating() {
        return authenticating;
    }

    public void setAuthenticating(boolean authenticating) {
        this.authenticating = authenticating;
        notifyPropertyChanged(BR.authenticating);
    }

    @Bindable
    public boolean isRemeberMe() {
        return remeberMe;
    }

    public void setRemeberMe(boolean remeberMe) {
        this.remeberMe = remeberMe;
        notifyPropertyChanged(BR.remeberMe);
    }

    public void changeRemeberMeStatus(){
        setRemeberMe(!isRemeberMe());
    }
}