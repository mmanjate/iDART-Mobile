package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.PharmacyType;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.service.ClinicService;
import mz.org.fgh.idartlite.service.PatientService;
import mz.org.fgh.idartlite.service.PharmacyTypeService;
import mz.org.fgh.idartlite.service.UserService;
import mz.org.fgh.idartlite.view.LoginActivity;
import mz.org.fgh.idartlite.view.HomeActivity;

public class LoginVM extends BaseViewModel {

    private User user;
    private Clinic clinic;
    private PharmacyType pharmacyType;
    private UserService userService;
    private ClinicService clinicService;
    private PatientService patientService;
    private PharmacyTypeService pharmacyTypeService;

    private String successMessage      = "Login was successful!";
    private String successUserCreation = "User was successful create!";
    private String errorMessage        = "User Name or Password not valid!";
    private String mandatoryField      = "User Name and Password are mandatory!";

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
        clinic = new Clinic();
        userService = new UserService(getApplication(), this.user);
        clinicService = new ClinicService(getApplication(), this.user);
        patientService = new PatientService(getApplication(), this.user);
        pharmacyTypeService = new PharmacyTypeService(getApplication(), this.user);
    }

     public void login(){
         try {
             if (getUserName().length() == 0 || getUserPassword().length() == 0) {
                 setToastMessage(mandatoryField);
             } else {

                 if (userService.checkIfUsertableIsEmpty()) {
                     pharmacyType = new PharmacyType();
                     pharmacyType.setDescription("qualquer");
                     pharmacyTypeService.savePharmacyType(pharmacyType);
                     pharmacyType.setId(1);

                     clinic.setAddress("Av.Mateus Munguambe");
                     clinic.setClinicName("Farmácia Indico");
                     clinic.setPhone("82665382");
                     clinic.setCode("3da5f12714555ded1f0e40824b2c8568");
                     clinic.setUuid("3da5f12714555ded1f0e40824b2c8568");
                     clinic.setPharmacyType(pharmacyType);
                     clinicService.saveClinic(clinic);

                     Patient patient = new Patient();
                     patient.setAddress("Matola");
                     Date date = Calendar.getInstance().getTime();
                     patient.setBirthDate(date);
                     patient.setFirstName("Antonio Mateus");
                     patient.setLastName("Munguambe");
                     patient.setGender("Masculino");
                     patient.setNid("0101010101/2020/00001");
                     patientService.savePatient(patient);

                     patient.setAddress("CS Chabeco");
                     date = Calendar.getInstance().getTime();
                     patient.setBirthDate(date);
                     patient.setFirstName("Engels Mateus");
                     patient.setLastName("Nhantumbo");
                     patient.setGender("Masculino");
                     patient.setNid("0101010101/2020/63254");
                     patientService.savePatient(patient);

                     User user = new User();
                     user.setUserName("root");
                     user.setPassword("root");
                     clinic.setId(1);
                     user.setClinic(clinic);
                     userService.saveUser(user);
                     setToastMessage(successUserCreation);
                 } else {
                     if (!userService.login(user)) {
                         if(!clinicService.getCLinic().isEmpty()){
                             clinic = clinicService.getCLinic().get(0);
                         }
                         Intent intent = new Intent(getApplication(), HomeActivity.class);
                         Bundle bundle = new Bundle();
                         bundle.putSerializable("user",user);
                         bundle.putSerializable("clinic",clinic);
                         intent.putExtras(bundle);
                         getRelatedActivity().startActivity(intent);
                         setToastMessage(successMessage);
                     } else {
                         setToastMessage(errorMessage);
                     }
                 }
             }
         }catch (SQLException e) {
             Log.i("INFO DB", "Erro ao fazer Login: " + e.getMessage());
             e.printStackTrace();
         }
    }

    @Override
    public LoginActivity getRelatedActivity() {

        return (LoginActivity) super.getRelatedActivity();
    }
}
