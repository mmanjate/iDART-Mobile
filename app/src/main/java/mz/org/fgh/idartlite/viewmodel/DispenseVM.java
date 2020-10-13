package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseActivity;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.PrescribedDrug;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.service.DispenseDrugService;
import mz.org.fgh.idartlite.service.DispenseService;
import mz.org.fgh.idartlite.service.DrugService;
import mz.org.fgh.idartlite.service.EpisodeService;
import mz.org.fgh.idartlite.service.PrescriptionService;
import mz.org.fgh.idartlite.service.StockService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.patient.CreateDispenseActivity;
import mz.org.fgh.idartlite.view.patient.DispenseFragment;
import mz.org.fgh.idartlite.view.patient.PatientActivity;

public class DispenseVM extends BaseViewModel {

    private DispenseService dispenseService;

    private Dispense dispense;

    private boolean initialDataVisible;

    private boolean drugDataVisible;

    private PrescriptionService prescriptionService;

    private DispenseDrugService dispenseDrugService;

    private DrugService drugService;

    private StockService stockService;

    private EpisodeService episodeService;


    public DispenseVM(@NonNull Application application) {
        super(application);

        this.dispense = new Dispense();
        dispenseService = new DispenseService(application, getCurrentUser());
        prescriptionService = new PrescriptionService(application, getCurrentUser());
        dispenseDrugService = new DispenseDrugService(application, getCurrentUser());
        this.drugService = new DrugService(application, getCurrentUser());
        this.stockService = new StockService(application, getCurrentUser());
        this.episodeService = new EpisodeService(application, getCurrentUser());
        this.setViewListRemoveButton(true);

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

    public void create(Dispense dispense) throws SQLException {
        this.dispenseService.createDispense(dispense);
    }

    public Prescription getLastPatientPrescription(Patient patient) throws SQLException {

        return this.prescriptionService.getLastPatientPrescription(patient);
    }

    public List<Drug> getAllDrugs() throws SQLException {
        return drugService.getAll();
    }

    public List<Stock> getAllStocksByClinicAndDrug(Clinic clinic, Drug drug) {

        List<Stock> stocks = new ArrayList<>();
        try {
            stocks = this.stockService.getAllStocksByClinicAndDrug(clinic, drug);
        } catch (SQLException e) {
            e.printStackTrace();
            Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.find_stock_list_error) + e.getLocalizedMessage()).show();
        }

        return stocks;
    }

    @Override
    public BaseActivity getRelatedActivity() {
        return super.getRelatedActivity();
    }

    public void save() {

        String validationErrors = this.patientHasEndingEpisode();

        if (!Utilities.stringHasValue(validationErrors)) {

            ((CreateDispenseActivity) getRelatedActivity()).loadFormData();

            validationErrors = ((CreateDispenseActivity) getRelatedActivity()).validateDispenseDurationByPrescription();

            if (!Utilities.stringHasValue(validationErrors)) {

                validationErrors = ((CreateDispenseActivity) getRelatedActivity()).validateStockForSelectedDrugs();

                if (!Utilities.stringHasValue(validationErrors)) {

                    validationErrors = this.dispense.validate();

                    if (!Utilities.stringHasValue(validationErrors)) {
                        try {
                            boolean isNewDispense = true;

                            if (dispense.getId() > 0) {
                                isNewDispense = false;
                                this.dispenseService.deleteDispense(dispense);
                                dispense.setId(0);
                                this.dispenseService.saveOrUpdateDispense(dispense);
                            } else
                                this.dispenseService.saveOrUpdateDispense(dispense);
                            if (isNewDispense)
                                Utilities.displayAlertDialog(getRelatedActivity(), "O aviamento foi criado com sucesso!", ((CreateDispenseActivity) getRelatedActivity())).show();
                            else
                                Utilities.displayAlertDialog(getRelatedActivity(), "O aviamento em edição foi removido e criado novo!", ((CreateDispenseActivity) getRelatedActivity())).show();

                        } catch (SQLException e) {
                            e.printStackTrace();
                            Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.save_error_msg) + e.getLocalizedMessage()).show();
                        }
                    } else {
                        Utilities.displayAlertDialog(getRelatedActivity(), validationErrors).show();
                    }
                } else {
                    Utilities.displayAlertDialog(getRelatedActivity(), validationErrors).show();
                }
            } else {
                Utilities.displayAlertDialog(getRelatedActivity(), validationErrors).show();
            }
        } else {
            Utilities.displayAlertDialog(getRelatedActivity(), validationErrors).show();
        }
    }

    public List<DispensedDrug> findDispensedDrugsByDispenseId(int id) throws SQLException {

        return this.dispenseDrugService.findDispensedDrugByDispenseId(id);
    }

    public List<Dispense> getAllDispensesByPrescription(Prescription prescription) throws SQLException {

        return this.dispenseService.getAllDispenseByPrescription(prescription);
    }

    public List<PrescribedDrug> getAllPrescribedDrugsByPrescription(Prescription prescription) throws SQLException {

        return this.prescriptionService.getAllOfPrescription(prescription);
    }

    public void backToPreviusActivity() {
        Map<String, Object> params = new HashMap<>();
        params.put("patient", getDispense().getPrescription().getPatient());
        params.put("user", getCurrentUser());
        params.put("clinic", getCurrentClinic());
        params.put("requestedFragment", DispenseFragment.FRAGMENT_CODE_DISPENSE);
        getRelatedActivity().nextActivity(PatientActivity.class, params);
    }

    public String dispenseCanBeEdited() throws SQLException {
        if (this.dispense.isSyncStatusSent(this.dispense.getSyncStatus()))
            return getRelatedActivity().getString(R.string.cant_edit_synced_dispense);
        return "";
    }

    public String checkDispenseRemoveConditions() {
        if (this.dispense.isSyncStatusSent(this.dispense.getSyncStatus()))
            return getRelatedActivity().getString(R.string.dispense_cant_be_removed_msg);
        return "";
    }

    public String patientHasEndingEpisode() {
        boolean hasEndingEpisode = this.episodeService.patientHasEndingEpisode(getDispense().getPrescription().getPatient());
        if (hasEndingEpisode) {
            return "Não pode se editar/remover a dispensa porque o paciente já tem o episódio final.";
        }
        return "";
    }

    public boolean patientHasEpisodioFim(Patient patient) {
        return this.episodeService.patientHasEndingEpisode(patient);
    }

}
