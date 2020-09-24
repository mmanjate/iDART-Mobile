package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.service.DispenseDrugService;
import mz.org.fgh.idartlite.service.DispenseService;
import mz.org.fgh.idartlite.service.PrescriptionService;

public class DispenseVM extends BaseViewModel {

    private DispenseService dispenseService;

    private Dispense dispense;

    private boolean initialDataVisible;

    private boolean drugDataVisible;

    private PrescriptionService prescriptionService;

    private DispenseDrugService dispenseDrugService;


    public DispenseVM(@NonNull Application application) {
        super(application);

        this.dispense = new Dispense();

        dispenseService = new DispenseService(application, getCurrentUser());

        prescriptionService = new PrescriptionService(application, getCurrentUser());

        dispenseDrugService = new DispenseDrugService(application, getCurrentUser());
    }

    public List<Dispense> gatAllOfPrescription(Prescription prescription) throws SQLException {
        return dispenseService.getAllOfPrescription(prescription);
    }

    public List<Dispense> gatAllOfPatient(Patient patient) throws SQLException {
        return dispenseService.getAllOfPatient(patient);
    }

    public void deleteDispense(Dispense dispense) throws SQLException {
        this.dispenseService.deleteDispense(dispense);
    }

    public Dispense getDispense() {
        return this.dispense;
    }

    public void setDispense(Dispense dispense) {
        this.dispense = dispense;
    }

    @Bindable
    public boolean isInitialDataVisible() {
        return initialDataVisible;
    }

    public void setInitialDataVisible(boolean initialDataVisible) {
        this.initialDataVisible = initialDataVisible;
        notifyPropertyChanged(BR.initialDataVisible);
    }

    @Bindable
    public boolean isDrugDataVisible() {
        return drugDataVisible;
    }


    public void setDrugDataVisible(boolean drugDataVisible) {
        this.drugDataVisible = drugDataVisible;
        notifyPropertyChanged(BR.drugDataVisible);
    }

    public List<TherapeuticRegimen> getAllTherapeuticRegimen (){
        return null;
    }

    public void create(Dispense dispense) throws SQLException {
        this.dispenseService.createDispense(dispense);
    }

    public Prescription getLastPatientPrescription(Patient patient) throws SQLException {

        return  this.prescriptionService.getLastPatientPrescription(patient);
    }

    public void createDispensedDrug(DispensedDrug dispensedDrug) throws SQLException {
        this.dispenseDrugService.createDispensedDrug(dispensedDrug);
    }

}
