package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.DispenseType;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.TherapeuticLine;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.service.DispenseService;
import mz.org.fgh.idartlite.service.DispenseTypeService;
import mz.org.fgh.idartlite.service.DrugService;
import mz.org.fgh.idartlite.service.PrescriptionService;
import mz.org.fgh.idartlite.service.TherapeuthicLineService;
import mz.org.fgh.idartlite.service.TherapheuticRegimenService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.patient.PrescriptionActivity;

public class PrescriptionVM extends BaseViewModel {

    private PrescriptionService prescriptionService;

    private Prescription prescription;
    private TherapheuticRegimenService regimenService;
    private TherapeuthicLineService lineService;
    private DispenseTypeService dispenseTypeService;
    private DrugService drugService;

    private DispenseService dispenseService;

    private boolean seeOnlyOfRegime;

    private boolean initialDataVisible;

    private boolean drugDataVisible;

    private boolean urgentPrescription;

    public PrescriptionVM(@NonNull Application application) {
        super(application);

        initNewPrescription();

        initServices(application);

        urgentPrescription = false;

        this.seeOnlyOfRegime = true;
    }

    private void initNewPrescription() {
        this.prescription = new Prescription();
    }

    private void initServices(@NonNull Application application) {
        prescriptionService = new PrescriptionService(application, getCurrentUser());
        regimenService = new TherapheuticRegimenService(application, getCurrentUser());
        lineService = new TherapeuthicLineService(application, getCurrentUser());
        dispenseTypeService = new DispenseTypeService(application, getCurrentUser());
        drugService = new DrugService(application, getCurrentUser());
        dispenseService = new DispenseService(application, getCurrentUser());
    }

    public List<Prescription> gatAllOfPatient(Patient patient) throws SQLException {
        return prescriptionService.getAllPrescriptionsByPatient(patient);
    }

    public void create(Prescription prescription) throws SQLException {
        this.prescriptionService.createPrescription(prescription);
    }

    public void deletePrescription(Prescription prescription) throws SQLException {
        this.prescriptionService.deletePrescription(prescription);
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
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

    @Override
    public BaseActivity getRelatedActivity() {
        return super.getRelatedActivity();
    }

    public void setDrugDataVisible(boolean drugDataVisible) {
        this.drugDataVisible = drugDataVisible;
        notifyPropertyChanged(BR.drugDataVisible);
    }

    public List<TherapeuticRegimen> getAllTherapeuticRegimen () throws SQLException {
        return regimenService.getAll();
    }

    public List<TherapeuticLine> getAllTherapeuticLines () throws SQLException {
        return lineService.getAll();
    }

    public List<DispenseType> getAllDispenseTypes () throws SQLException {
        return dispenseTypeService.getAll();
    }

    public List<Drug> getAllDrugs() throws SQLException {
        return drugService.getAll();
    }

    public List<Drug> getAllDrugsOfTheraputicRegimen(TherapeuticRegimen therapeuticRegimen) throws SQLException {
        return drugService.getAllOfTherapeuticRegimen(therapeuticRegimen);
    }

    public void setPrescriptionToSpetial(){
        urgentPrescription = !urgentPrescription;
        if (urgentPrescription) {
            this.prescription.setUrgentPrescription(Prescription.URGENT_PRESCRIPTION);

        }else {
            this.prescription.setUrgentPrescription(Prescription.NOT_URGENT_PRESCRIPTION);
        }

        ((PrescriptionActivity)getRelatedActivity()).changeMotiveSpinnerStatus(urgentPrescription);
    }

    public void save(){

        ((PrescriptionActivity)getRelatedActivity()).loadFormData();
        String validationErrors = this.prescription.validate();
        if (!Utilities.stringHasValue(validationErrors)) {
            try {
                if (getRelatedActivity().getApplicationStep().isapplicationstepcreate()) {
                    prescriptionService.createPrescription(this.prescription);
                    Utilities.displayConfirmationDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.would_like_to_dispense), getRelatedActivity().getString(R.string.yes), getRelatedActivity().getString(R.string.no), ((PrescriptionActivity)getRelatedActivity())).show();
                } else if (getRelatedActivity().getApplicationStep().isApplicationStepEdit()) {
                    prescriptionService.updatePrescription(this.prescription);
                    Utilities.displayAlertDialog(getRelatedActivity(), "A Prescrição foi actualizada com sucesso.", ((PrescriptionActivity)getRelatedActivity())).show();
                }


            } catch (SQLException e) {
                e.printStackTrace();
                Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.save_error_msg) + e.getLocalizedMessage()).show();
            }
        }else {
            Utilities.displayAlertDialog(getRelatedActivity(), validationErrors).show();
        }
    }

    public Prescription getLastPatientPrescription(Patient patient) throws SQLException {

        return  this.prescriptionService.getLastPatientPrescription(patient);
    }

    public String checkPrescriptionRemoveConditions() {
        try {
            if (!this.prescription.isSyncStatusReady(this.prescription.getSyncStatus())) return getRelatedActivity().getString(R.string.prescription_cant_be_removed_msg);
            if (prescriptionHasDispenses()) return getRelatedActivity().getString(R.string.prescription_got_dispenses_msg);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "";
    }

    private boolean prescriptionHasDispenses() throws SQLException {
        return dispenseService.countAllOfPrescription(this.prescription) > 0;
    }

    public void loadPrescribedDrugsOfPrescription() throws SQLException {
        this.prescription.setPrescribedDrugs(prescriptionService.getAllOfPrescription(this.prescription));
    }

    public void loadLastPatientPrescription() throws SQLException {
        this.prescription = prescriptionService.getLastPatientPrescription(this.prescription.getPatient());
        this.prescription.setPrescribedDrugs(prescriptionService.getAllOfPrescription(this.prescription));
        this.prescription.setId(0);
    }

    @Bindable
    public boolean isSeeOnlyOfRegime() {
        return seeOnlyOfRegime;
    }

    public void setSeeOnlyOfRegime(boolean seeOnlyOfRegime) {
        this.seeOnlyOfRegime = seeOnlyOfRegime;
        notifyPropertyChanged(BR.seeOnlyOfRegime);
    }

    public void changeDrugsListingMode(){
        this.seeOnlyOfRegime = !this.seeOnlyOfRegime;

        ((PrescriptionActivity)getRelatedActivity()).reloadDrugsSpinnerByRegime(this.prescription.getTherapeuticRegimen());
    }

    public String prescriptionCanBeEdited() throws SQLException {
        if (prescriptionHasDispenses()) return getRelatedActivity().getString(R.string.cant_edit_prescription_msg);
        if (!this.prescription.isSyncStatusReady(this.prescription.getSyncStatus())) return getRelatedActivity().getString(R.string.cant_edit_synced_prescription);
        return "";
    }
}
