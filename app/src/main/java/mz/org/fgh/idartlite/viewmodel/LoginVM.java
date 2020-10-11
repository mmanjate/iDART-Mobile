package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.common.Listble;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.service.ClinicService;
import mz.org.fgh.idartlite.service.UserService;
import mz.org.fgh.idartlite.service.restService.RestPatientService;
import mz.org.fgh.idartlite.service.restService.RestRunDataForTestService;
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

        userService = new UserService(getApplication(), getCurrentUser());
        restUserService = new RestUserService(getApplication(), getCurrentUser());
        clinicService = new ClinicService(getApplication(), getCurrentUser());
    }

    public void setUserName(String userName) {
        this.currentUser.setUserName(userName);
        notifyPropertyChanged(BR.userName);
    }

    @Bindable
    public String getUserName() {
            return this.currentUser.getUserName();
    }

    @Bindable
    public String getUserPassword() {
        return this.currentUser.getPassword();
    }

    public void setUserPassword(String password) {
        this.currentUser.setPassword(password);
        notifyPropertyChanged(BR.userPassword);

    }

    @Bindable
    public Clinic getClinic() {
        return getCurrentClinic();
    }

    public void setClinic(Clinic c) {
        setCurrentClinic(c);
        notifyPropertyChanged(BR.clinic);
    }



    public void saveLogingUser() throws SQLException {
        this.currentUser.setClinic(clinicService.getCLinic().get(0));
        userService.saveUser(getRelatedActivity().getCurrentUser());
    }

    public void saveUserClinic() throws SQLException {
        clinicService.saveClinic(getCurrentClinic());
    }

    public void login() {
        getRelatedActivity().changeViewToAuthenticatingMode();
        getRelatedActivity().getActivityLoginBinding().executePendingBindings();

        if ((getCurrentClinic() == null || getCurrentClinic().getId() < 0) && appHasUsersOnDB()){
            getRelatedActivity().changeViewToNormalMode();
            Utilities.displayAlertDialog(getRelatedActivity(), "O campo Farmácia deve ser preenchido.").show();
        return;
        }

        try {
            getCurrentUser().setUserName(getUserName().trim());

            String loginErrors = getCurrentUser().validadeToLogin();

            if (!Utilities.stringHasValue(loginErrors)) {
                if (userService.checkIfUsertableIsEmpty()) {
                    runRestUserAccess();

                } else {
                    // Somente para testes --- estas funcionalidades foram alocadas no WorkManager da app
                    RestRunDataForTestService runDataForTestService = new RestRunDataForTestService(getApplication(),getCurrentUser());
                    if (!userService.login(getCurrentUser())) {
                        if (Utilities.listHasElements(clinicService.getCLinic())) {
                            setCurrentClinic(clinicService.getCLinic().get(0));
                            getRelatedActivity().changeViewToNormalMode();
                            moveToHome();
                            this.getRelatedActivity().savingSharedPreferences();
                        } else {
                            getRelatedActivity().changeViewToNormalMode();
                            Utilities.displayAlertDialog(getRelatedActivity(), "Não foi encontrada a configuração da farmácia.").show();
                        }
                    } else {
                        getRelatedActivity().changeViewToNormalMode();
                        Utilities.displayAlertDialog(getRelatedActivity(), "Utilizador e/ou senha inválida").show();
                    }
                }
            } else {
                getRelatedActivity().changeViewToNormalMode();

                Utilities.displayAlertDialog(getRelatedActivity(), loginErrors).show();

            }
        } catch (SQLException e) {
            getRelatedActivity().changeViewToNormalMode();
            Utilities.displayAlertDialog(getRelatedActivity(), "Ocorreu um erro ao autenticar os dados, "+e.getLocalizedMessage()).show();
            Log.i("INFO DB", "Erro ao fazer Login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void runRestUserAccess(){
        restUserService.getUserAuthentication(getCurrentClinic().getUuid(), getRelatedActivity().getCurrentUser().getUserName(), getRelatedActivity().getCurrentUser().getPassword(), getRelatedActivity());
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
        params.put("user", getCurrentUser());
        params.put("clinic", getCurrentClinic());
        getRelatedActivity().nextActivity(SecondSplashActivity.class, params);
    }

    private void moveToHome() {
        Map<String, Object> params = new HashMap<>();
        params.put("user", getCurrentUser());
        params.put("clinic", getCurrentClinic());
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