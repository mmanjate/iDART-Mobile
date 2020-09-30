package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.base.BaseViewModel;

import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.DiseaseType;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Form;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.PharmacyType;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.service.ClinicService;
import mz.org.fgh.idartlite.service.DiseaseTypeService;
import mz.org.fgh.idartlite.service.DrugService;
import mz.org.fgh.idartlite.service.FormService;
import mz.org.fgh.idartlite.service.PatientService;
import mz.org.fgh.idartlite.service.PharmacyTypeService;
import mz.org.fgh.idartlite.service.StockService;
import mz.org.fgh.idartlite.service.UserService;

import mz.org.fgh.idartlite.model.*;
import mz.org.fgh.idartlite.service.*;
import mz.org.fgh.idartlite.util.DateUtilitis;

import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.LoginActivity;
import mz.org.fgh.idartlite.view.HomeActivity;

public class LoginVM extends BaseViewModel {

    private User user;
    private Clinic clinic;
    private Stock stock;
    private Drug drug;
    private DiseaseType diseaseType;
    private Form form;
    private PharmacyType pharmacyType;
    private UserService userService;
    private ClinicService clinicService;
    private StockService stockService;
    private DrugService drugService;
    private DiseaseTypeService diseaseTypeService;
    private FormService formService;
    private PatientService patientService;
    private PharmacyTypeService pharmacyTypeService;
    private PrescriptionService prescriptionService;
    private DispenseService dispenseService;
    private DispenseTypeService dispenseTypeService;
    private TherapheuticRegimenService therapheuticRegimenService;
    private TherapeuthicLineService therapeuthicLineService;
    private EpisodeService episodeService;
    private DispenseDrugService dispenseDrugService;

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
        clinic = new Clinic();
        userService = new UserService(getApplication(), this.user);
        clinicService = new ClinicService(getApplication(), this.user);
        patientService = new PatientService(getApplication(), this.user);
        pharmacyTypeService = new PharmacyTypeService(getApplication(), this.user);

        drugService = new DrugService(getApplication(), this.user);
        stockService = new StockService(getApplication(), this.user);
        diseaseTypeService = new DiseaseTypeService(getApplication(), this.user);
        formService = new FormService(getApplication(), this.user);

        this.dispenseTypeService = new DispenseTypeService(getApplication(), this.user);
        this.prescriptionService = new PrescriptionService(getApplication(), this.user);
        this.therapheuticRegimenService = new TherapheuticRegimenService(getApplication(), this.user);
        this.therapeuthicLineService = new TherapeuthicLineService(getApplication(), this.user);
        this.dispenseService = new DispenseService(getApplication(), this.user);
        this.episodeService = new EpisodeService(getApplication(), this.user);
        this.dispenseDrugService = new DispenseDrugService(getApplication(), this.user);

    }

    public void login() {
        try {
            if (getUserName() != null && getUserPassword() != null) {
                if (getUserName().length() == 0 || getUserPassword().length() == 0) {
                    setToastMessage(mandatoryField);
                } else {

                    if (userService.checkIfUsertableIsEmpty()) {
                        final boolean[] resultado = {false};

                        new Thread(new Runnable() {
                            public void run() {
                                // do here some long operation
                                try {
                                    Thread.sleep(2000);
                                    resultado[0] = userService.getUserAuthentication(currentClinic.getUuid(), user.getUserName(), user.getPassword());

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                        if (true) {
                            System.out.println(currentClinic);
                            System.out.println(user.getUserName());
                            clinicService.saveClinic(currentClinic);
                            user.setClinic(clinicService.getCLinic().get(0));
                            userService.saveUser(user);
                            // so para teste
                            Intent intent = new Intent(getApplication(), HomeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user", user);
                            bundle.putSerializable("clinic", clinicService.getCLinic().get(0));
                            intent.putExtras(bundle);
                            getRelatedActivity().startActivity(intent);
                            setToastMessage(successUserCreation);
                        } else {
                            setToastMessage(restErrorMessage);
                        }


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
