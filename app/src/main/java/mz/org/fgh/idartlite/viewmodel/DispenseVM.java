package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;
import android.view.View;

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
import mz.org.fgh.idartlite.common.DialogListener;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.PrescribedDrug;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.service.DispenseDrugService;
import mz.org.fgh.idartlite.service.DispenseService;
import mz.org.fgh.idartlite.service.DrugService;
import mz.org.fgh.idartlite.service.EpisodeService;
import mz.org.fgh.idartlite.service.PrescriptionService;
import mz.org.fgh.idartlite.service.StockService;
import mz.org.fgh.idartlite.util.DateUtilitis;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.patient.CreateDispenseActivity;
import mz.org.fgh.idartlite.view.patient.DispenseFragment;
import mz.org.fgh.idartlite.view.patient.PatientActivity;

public class DispenseVM extends BaseViewModel implements DialogListener {

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

    public Dispense getLastPatientDispense(Prescription prescription) throws SQLException {

        return this.dispenseService.getLastDispenseFromPrescription(prescription);
    }

    public List<Drug> getAllDrugs() throws SQLException {
        return drugService.getAll();
    }

    public List<Drug> getAllDrugsFromPrescritionRegimen() throws SQLException {
        return drugService.getAllOfTherapeuticRegimen((TherapeuticRegimen) this.dispense.getPrescription().getTherapeuticRegimen());
    }

    public List<Drug> getDrugsWithoutRectParanthesis(List<Drug> drugs) throws SQLException {
        return drugService.getDrugsWithoutRectParanthesis(drugs);
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

        ((CreateDispenseActivity) getRelatedActivity()).loadFormData();

        String validationErrors = this.patientHasEndingEpisode();


        if (!Utilities.stringHasValue(validationErrors)) {

            validationErrors = ((CreateDispenseActivity) getRelatedActivity()).validateDispenseDurationByPrescription();

            if (!Utilities.stringHasValue(validationErrors)) {

                validationErrors = ((CreateDispenseActivity) getRelatedActivity()).validateStockForSelectedDrugs();

                if (!Utilities.stringHasValue(validationErrors)) {

                    validationErrors = this.dispense.validate();

                    if (!Utilities.stringHasValue(validationErrors)) {
                        try {
                            if (dispense.getId() > 0) {
                                this.editDispenseAndRemovePrior();
                            } else {
                                Prescription prescription = this.getLastPatientPrescription(getDispense().getPrescription().getPatient());
                                Dispense dispense = this.getLastPatientDispense(prescription);

                                if (dispense != null) {
                                    double remainDays = DateUtilitis.dateDiff(dispense.getNextPickupDate(), this.dispense.getPickupDate(), DateUtilitis.DAY_FORMAT);
                                    java.util.Date nextPickupDateCalculated = getNextPickupDate(this.dispense.getPickupDate(), this.dispense.getSupply(), (int) remainDays);
                                    double controlNextPickupDate = DateUtilitis.dateDiff(this.dispense.getNextPickupDate(), nextPickupDateCalculated, DateUtilitis.DAY_FORMAT);

                                    if (remainDays > 2 && controlNextPickupDate < 0) {
                                        Utilities.displayConfirmationDialog(getRelatedActivity(),
                                                getRelatedActivity().getString(R.string.cant_dispense_patient_has_drugs) + "\n" +
                                                        "Nota: O Paciente contém medicamentos para +" + remainDays +" dias e caso desejar aviar, serão adicionados estes dias a data do próximo Levantamento, passando para [ " +
                                                        nextPickupDateCalculated + " ]",
                                                getRelatedActivity().getString(R.string.yes), getRelatedActivity().getString(R.string.no),
                                                DispenseVM.this).show();
                                    }else{
                                        String patientNid = this.dispense.getPrescription().getPatient().getNid();
                                        this.dispenseService.saveOrUpdateDispense(this.dispense);
                                        Utilities.displayAlertDialog(getRelatedActivity(), "Aviamento para o paciente " + patientNid + " efectuado com sucesso!", ((CreateDispenseActivity) getRelatedActivity())).show();
                                    }
                                }else {
                                    String patientNid = this.dispense.getPrescription().getPatient().getNid();
                                    this.dispenseService.saveOrUpdateDispense(this.dispense);
                                    Utilities.displayAlertDialog(getRelatedActivity(), "Aviamento para o paciente " + patientNid + " efectuado com sucesso!", ((CreateDispenseActivity) getRelatedActivity())).show();

                                }
                            }

                        } catch (Exception e) {
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
            return getRelatedActivity().getString(R.string.dispense_has_final_episode_cant_be_edit);
        }
        return "";
    }

    public boolean patientHasEpisodioFim(Patient patient) {
        return this.episodeService.patientHasEndingEpisode(patient);
    }

    @Override
    public void doOnConfirmed() {
        if (getCurrentStep().isApplicationStepEdit()) {
            Dispense dispense = this.dispense;
            try {
                this.dispenseService.deleteDispense(dispense);
                dispense.setId(0);
                this.dispenseService.saveOrUpdateDispense(dispense);
                this.backToPreviusActivity();
            } catch (SQLException ex) {

            }
        } else {
            try {
                Prescription prescription = this.getLastPatientPrescription(getDispense().getPrescription().getPatient());
                Dispense dispense = this.getLastPatientDispense(prescription);
                double remainDays = DateUtilitis.dateDiff(dispense.getNextPickupDate(), this.dispense.getPickupDate(), DateUtilitis.DAY_FORMAT);
                this.dispense.setNextPickupDate(getNextPickupDate(this.dispense.getPickupDate(), this.dispense.getSupply(), (int) remainDays));
                String patientNid = this.dispense.getPrescription().getPatient().getNid();
                this.dispenseService.saveOrUpdateDispense(this.dispense);
                Utilities.displayAlertDialog(getRelatedActivity(), "Aviamento para o paciente " + patientNid + " efectuado com sucesso!", ((CreateDispenseActivity) getRelatedActivity())).show();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void doOnDeny() {
        if (getCurrentStep().isApplicationStepEdit()) {
            getCurrentStep().changeToList();
            this.backToPreviusActivity();
        }
    }

    public void editDispenseAndRemovePrior() {
        getCurrentStep().changeToEdit();

        StringBuilder dispensedDrugsList = new StringBuilder();

        Dispense dispenseInEditMode = ((CreateDispenseActivity) getRelatedActivity()).getDispenseSelectedForEdit();
        List<DispensedDrug> dispensedDrugs = new ArrayList<>();
        try {
            dispensedDrugs = this.findDispensedDrugsByDispenseId(dispenseInEditMode.getId());
        } catch (SQLException ex) {
        }

        String dataLevantamento = "Data Levantamento: " + DateUtilitis.formatToDDMMYYYY(dispenseInEditMode.getPickupDate(), "/");
        String duracao = "Duração: " + Utilities.parseSupplyToLabel(dispenseInEditMode.getSupply());
        String dataProximoLevantamento = "Data Próximo Levantamento: " + DateUtilitis.formatToDDMMYYYY(dispenseInEditMode.getNextPickupDate(), "/");

        for (DispensedDrug dd : dispensedDrugs
        ) {
            Drug drug = dd.getStock().getDrug();
            dispensedDrugsList.append(drug.getDescription() + "\n");
        }

        String detalhesAviamento = getRelatedActivity().getString(R.string.dispense_drug_detail_list);
        String listaDeMedicamentosDispensados = getRelatedActivity().getString(R.string.dispensed_drug_list);
        String gostariaDeRemoverAdispensaAnterior = getRelatedActivity().getString(R.string.would_you_like_remove_prior_dispense);

        String editWillRemoveSelectedDispense = detalhesAviamento + "\n\n" + dataLevantamento +
                "\n" + duracao + "\n" + dataProximoLevantamento + "\n\n" + listaDeMedicamentosDispensados + "\n"
                + dispensedDrugsList + "\n" + gostariaDeRemoverAdispensaAnterior;

        Utilities.displayConfirmationDialog(getRelatedActivity(), editWillRemoveSelectedDispense, getRelatedActivity().getString(R.string.yes), getRelatedActivity().getString(R.string.no), DispenseVM.this).show();
    }

    public void changeDataViewStatus(View view) {
        ((CreateDispenseActivity) getRelatedActivity()).changeFormSectionVisibility(view);
    }

    public java.util.Date getNextPickupDate(java.util.Date dispenseDate, int dipenseDuration, int remainDays) {

        int daysToAdd = 0;

        if (dipenseDuration == 2) {
            daysToAdd = Dispense.DURATION_TWO_WEEKS;
        } else if (dipenseDuration == 4) {
            daysToAdd = Dispense.DURATION_ONE_MONTH;
        } else if (dipenseDuration == 8) {
            daysToAdd = Dispense.DURATION_TWO_MONTHS;
        } else if (dipenseDuration == 12) {
            daysToAdd = Dispense.DURATION_THREE_MONTHS;
        } else if (dipenseDuration == 16) {
            daysToAdd = Dispense.DURATION_FOUR_MONTHS;
        } else if (dipenseDuration == 20) {
            daysToAdd = Dispense.DURATION_FIVE_MONTHS;
        } else if (dipenseDuration == 24) {
            daysToAdd = Dispense.DURATION_SIX_MONTHS;
        }


        java.util.Date nextPickupDate = DateUtilitis.addDaysToDate(dispenseDate, daysToAdd + remainDays);

        String dataDispensa = DateUtilitis.parseDateToDDMMYYYYString(nextPickupDate);

        int isWeekend = DateUtilitis.isSaturdayOrSunday(dataDispensa);

        if (isWeekend == 6) {
            nextPickupDate = DateUtilitis.addDaysToDate(nextPickupDate, -1);
        } else if (isWeekend == 7) {
            nextPickupDate = DateUtilitis.addDaysToDate(nextPickupDate, -2);
        }

        return nextPickupDate;
    }
}
