package mz.org.fgh.idartlite.viewmodel.login;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.rest.BaseRestService;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.rest.helper.RESTServiceHandler;
import mz.org.fgh.idartlite.rest.service.RestRunDataForTestService;
import mz.org.fgh.idartlite.rest.service.User.RestUserService;
import mz.org.fgh.idartlite.service.clinic.ClinicService;
import mz.org.fgh.idartlite.service.clinic.IClinicService;
import mz.org.fgh.idartlite.service.user.IUserService;
import mz.org.fgh.idartlite.service.user.UserService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.home.HomeActivity;
import mz.org.fgh.idartlite.view.login.LoginActivity;
import mz.org.fgh.idartlite.view.splash.SecondSplashActivity;

public class LoginVM extends BaseViewModel {
    private RestUserService restUserService;
    private IUserService userService;
    private IClinicService clinicService;

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

    @Override
    protected IBaseService initRelatedService() {
        return null;
    }

    @Override
    protected BaseModel initRecord() {
        return null;
    }

    @Override
    protected void initFormData() {

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
        this.currentUser.setClinic(clinicService.getAllClinics().get(0));
        userService.saveUser(getRelatedActivity().getCurrentUser());
    }

    public void saveUserClinic() throws SQLException {
        clinicService.saveClinic(getCurrentClinic());
    }

    public void login() {
        getRelatedActivity().changeViewToAuthenticatingMode();
        getRelatedActivity().getActivityLoginBinding().executePendingBindings();

        if ((appHasUsersOnDB() && getCurrentClinic() == null) || (appHasUsersOnDB() && getCurrentClinic().getUuid()== null) ){
            getRelatedActivity().changeViewToNormalMode();
            Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.pharmacy_required)).show();
        return;
        }

        try {


            String loginErrors = getCurrentUser().validadeToLogin();

            if (!Utilities.stringHasValue(loginErrors)) {
                getCurrentUser().setUserName(getUserName().trim());
                if (userService.checkIfUsertableIsEmpty()) {
                    runRestUserAccess();

                } else {
                    // Somente para testes --- estas funcionalidades foram alocadas no WorkManager da app
                    if (RESTServiceHandler.getServerStatus(BaseRestService.baseUrl)) {
                        RestRunDataForTestService runDataForTestService = new RestRunDataForTestService(getApplication(), getCurrentUser());
                    }

                    if (!userService.login(getCurrentUser())) {
                        if (Utilities.listHasElements(clinicService.getAllClinics())) {
                            setCurrentClinic(clinicService.getAllClinics().get(0));
                            getRelatedActivity().changeViewToNormalMode();
                            moveToHome();
                            this.getRelatedActivity().savingSharedPreferences();
                        } else {
                            getRelatedActivity().changeViewToNormalMode();
                            Utilities.displayAlertDialog(getRelatedActivity(),  getRelatedActivity().getString(R.string.pharmacy_config_not_found)).show();
                        }
                    } else {
                        getRelatedActivity().changeViewToNormalMode();
                        Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.user_or_pass_invalid)).show();
                    }
                }
            } else {
                getRelatedActivity().changeViewToNormalMode();

                Utilities.displayAlertDialog(getRelatedActivity(), loginErrors).show();

            }
        } catch (SQLException e) {
            getRelatedActivity().changeViewToNormalMode();
            Utilities.displayAlertDialog(getRelatedActivity(), "Ocorreu um erro ao verificar a configuração de utilizadores, "+e.getLocalizedMessage()).show();
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
            Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.error_verifying)).show();
        }
        return usersOnDB;
    }

    @Override
    public LoginActivity getRelatedActivity() {
        return (LoginActivity) super.getRelatedActivity();
    }

    @Override
    public void preInit() {

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