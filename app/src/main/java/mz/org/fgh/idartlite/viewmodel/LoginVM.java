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
import java.util.List;

import mz.org.fgh.idartlite.BR;
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
                     clinic.setId(1);
                     patient.setAddress("Matola");
                     Date date = Calendar.getInstance().getTime();
                     patient.setBirthDate(date);
                     patient.setFirstName("Antonio Mateus");
                     patient.setPhone("82665382");
                     patient.setLastName("Munguambe");
                     patient.setGender("Masculino");
                     patient.setNid("0101010101/2020/00001");
                     patient.setUuid("0101010101/2020/00001");
                     patient.setClinic(clinic);
                     patientService.savePatient(patient);

                     patient.setAddress("CS Chabeco");
                     date = Calendar.getInstance().getTime();
                     clinic.setId(1);
                     patient.setBirthDate(date);
                     patient.setFirstName("Engels Mateus");
                     patient.setLastName("Nhantumbo");
                     patient.setPhone("826219264");
                     patient.setGender("Masculino");
                     patient.setNid("0101010101/2020/63254");
                     patient.setClinic(clinic);
                     patientService.savePatient(patient);

                     //Create DispenseType
                     DispenseType dt = new DispenseType();
                     dt.setCode("MENSAL");
                     dt.setDescription("Mensal");
                     this.dispenseTypeService.createDispenseType(dt);

                     System.out.println("DispenseType ID "+dt.getId());

                     //Create Therapheuticline
                     TherapeuticLine tl = new TherapeuticLine();
                     tl.setCode("AZT+3TC+NVP");
                     tl.setDescription("AZITROMICINA+LAMIVUDINA");
                     this.therapeuthicLineService.createTherapheuticLine(tl);

                     System.out.println("TherapeuticLine ID "+tl.getId()+"CODE LINHA"+tl.getCode());

                     //Create TherapeuticRegimen
                     TherapeuticRegimen tr = new TherapeuticRegimen();
                     tr.setRegimenCode("AZT+3TC+NVP");
                     tr.setDescription("AZITROMICINA");
                     this.therapheuticRegimenService.createTherapheuticRegimen(tr);

                     System.out.println("Regime ID "+tr.getId());

                     //Create prescription
                     Prescription p = new Prescription();
                     p.setDispenseType(dt);
                     p.setExpiryDate(DateUtilitis.getCurrentDate());
                     patient.setId(1);
                     p.setPatient(patient);
                     p.setPrescriptionDate(DateUtilitis.getCurrentDate());
                     p.setPrescriptionSeq("01010101");
                     p.setSupply(0);
                     p.setSyncStatus("ready");
                     tl.setId(1);
                     p.setTherapeuticLine(tl);
                     tr.setId(1);
                     p.setTherapeuticRegimen(tr);
                     p.setUrgentNotes("Nao especial");
                     p.setUrgentPrescription("Nao especial");
                     p.setUuid("1");
                     this.prescriptionService.createPrescription(p);

                     System.out.println("ID Prescicao: "+p.getId());
                     PrescribedDrug pd = new PrescribedDrug();
                     pd.setId(1);
                     pd.setDrug(drug);

                     List<DispensedDrug> dds = new ArrayList<>();

                     //Create Dispense
                     Dispense dispense = new Dispense();
                     dispense.setNextPickupDate(DateUtilitis.getCurrentDate());
                     dispense.setPickupDate(DateUtilitis.getCurrentDate());
                     dispense.setSupply(4);
                     p.setId(1);
                     dispense.setPrescription(p);
                     dispense.setUuid("12");
                     this.dispenseService.createDispense(dispense);

                     dispense = new Dispense();
                     dispense.setNextPickupDate(DateUtilitis.getCurrentDate());
                     dispense.setPickupDate(DateUtilitis.getCurrentDate());
                     dispense.setSupply(4);
                     p.setId(1);
                     dispense.setPrescription(p);
                     dispense.setUuid("13");
                     this.dispenseService.createDispense(dispense);

                     dispense = new Dispense();
                     dispense.setNextPickupDate(DateUtilitis.getCurrentDate());
                     dispense.setPickupDate(DateUtilitis.getCurrentDate());
                     dispense.setSupply(4);
                     p.setId(1);
                     dispense.setPrescription(p);
                     dispense.setUuid("14");
                     this.dispenseService.createDispense(dispense);

                     //Creating an Episode For Patient1
                     Episode episode=new Episode();
                     episode.setEpisodeDate(date);
                     episode.setNotes("EPisodio teste ");
                     episode.setStartReason("Referido De");
                     episode.setSyncStatus("R");
                     episode.setPatient(patient);
                     episodeService.createEpisode(episode);

                     Prescription prescription=new Prescription();
                     prescription.setPatient(patient);
                     prescription.setPrescriptionDate(date);

                     prescription.setExpiryDate(date);
                     prescription.setSupply(123);
                     prescription.setPrescriptionSeq("111");
                     prescriptionService.createPrescription(prescription);

                     System.out.println("ID Dispensa: "+dispense.getId());

                     User user = new User();
                     user.setUserName("root");
                     user.setPassword("root");
                     clinic.setId(1);
                     user.setClinic(clinic);
                     userService.saveUser(user);

                     diseaseType = new DiseaseType();
                     diseaseType.setCode("102030");
                     diseaseType.setDescription("HIV");
                     diseaseTypeService.saveDiseaseType(diseaseType);
                     diseaseType.setId(1);

                     form = new Form();
                     form.setUnit("1-1");
                     form.setDescription("um de manha e um final do dia");
                     formService.saveForm(form);
                     form.setId(1);

                     drug = new Drug();
                     drug.setDescription("[DTG] DoluteGravir 50mg");
                     drug.setPackSize(180);
                     drug.setInstruction("1/1/1");
                     drug.setFnmcode("D05019060-A");
                     drug.setDiseaseType(diseaseType);
                     drug.setForm(form);
                     drugService.saveDrug(drug);
                     drug.setId(1);

                     stock = new Stock();
                     stock.setBatchNumber("250845AD");
                     stock.setClinic(clinic);
                     stock.setDrug(drug);
                     stock.setDateReceived(date);
                     stock.setExpiryDate(date);
                     stock.setOrderNumber("00001/001/2020");
                     stock.setPrice(50);
                     stock.setShelfNumber(80);
                     stock.setUnitsReceived(2390);
                     stock.setStockMoviment(2390);
                     stock.setUuid("3da5f12714555ded1f0e40824b2c8568");
                     stockService.saveOrUpdateStock(stock);

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
