package mz.org.fgh.idartlite.viewmodel.prescription;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.model.DispenseType;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.PrescribedDrug;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.TherapeuticLine;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.service.dispense.DispenseService;
import mz.org.fgh.idartlite.service.dispense.DispenseTypeService;
import mz.org.fgh.idartlite.service.dispense.IDispenseService;
import mz.org.fgh.idartlite.service.dispense.IDispenseTypeService;
import mz.org.fgh.idartlite.service.drug.DrugService;
import mz.org.fgh.idartlite.service.drug.IDrugService;
import mz.org.fgh.idartlite.service.drug.ITherapeuthicLineService;
import mz.org.fgh.idartlite.service.drug.ITherapheuticRegimenService;
import mz.org.fgh.idartlite.service.drug.TherapeuthicLineService;
import mz.org.fgh.idartlite.service.drug.TherapheuticRegimenService;
import mz.org.fgh.idartlite.service.prescription.IPrescribedDrugService;
import mz.org.fgh.idartlite.service.prescription.IPrescriptionService;
import mz.org.fgh.idartlite.service.prescription.PrescribedDrugService;
import mz.org.fgh.idartlite.service.prescription.PrescriptionService;
import mz.org.fgh.idartlite.util.SimpleValue;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.patientPanel.PatientPanelActivity;
import mz.org.fgh.idartlite.view.patientPanel.PrescriptionFragment;
import mz.org.fgh.idartlite.view.prescription.PrescriptionActivity;

public class PrescriptionVM extends BaseViewModel {

    private IPrescriptionService prescriptionService;

    private Prescription prescription;
    
    private ITherapheuticRegimenService regimenService;
    private ITherapeuthicLineService lineService;
    private IDispenseTypeService dispenseTypeService;
    private IDrugService drugService;

    private PrescriptionFragment relatedListingFragment;

    private IDispenseService dispenseService;

    private boolean seeOnlyOfRegime;

    private boolean initialDataVisible;

    private boolean drugDataVisible;

    private boolean urgentPrescription;
    private boolean newPrescriptionMustBeEspetial;
    private Prescription oldPrescription;
    private List<SimpleValue> durations;

    private Drug selectedDrug;
    private List<SimpleValue> motives;

    private List<Listble> selectedDrugs;

    private IPrescribedDrugService prescribedDrugService;


    public PrescriptionVM(@NonNull Application application) {
        super(application);

        initNewPrescription();

        initServices(application);

        urgentPrescription = false;

        this.seeOnlyOfRegime = true;

        durations = new ArrayList<>();
        motives = new ArrayList<>();

        loadPrescriptionDuration();
        loadUrgenceMotives();

        initialDataVisible = true;
    }

    @Override
    protected IBaseService initRelatedService() {
        return getServiceProvider().get(PrescriptionService.class);
    }

    @Override
    protected BaseModel initRecord() {
        return null;
    }

    @Override
    protected void initFormData() {

    }

    private void initNewPrescription() {
        this.prescription = new Prescription();

    }

    private void loadPrescriptionDuration(){
        durations.add(new SimpleValue());
        durations.add(SimpleValue.fastCreate(2, Prescription.DURATION_TWO_WEEKS));
        durations.add(SimpleValue.fastCreate(4, Prescription.DURATION_ONE_MONTH));
        durations.add(SimpleValue.fastCreate(8, Prescription.DURATION_TWO_MONTHS));
        durations.add(SimpleValue.fastCreate(12, Prescription.DURATION_THREE_MONTHS));
        durations.add(SimpleValue.fastCreate(16, Prescription.DURATION_FOUR_MONTHS));
        durations.add(SimpleValue.fastCreate(20, Prescription.DURATION_FIVE_MONTHS));
        durations.add(SimpleValue.fastCreate(24, Prescription.DURATION_SIX_MONTHS));
    }

    private void loadUrgenceMotives(){
        motives.add(new SimpleValue());
        motives.add(SimpleValue.fastCreate("Perda de Medicamento"));
        motives.add(SimpleValue.fastCreate("Ausencia do Clinico"));
        motives.add(SimpleValue.fastCreate("Laboratorio"));
        motives.add(SimpleValue.fastCreate("Rotura de Stock"));
        motives.add(SimpleValue.fastCreate("Outro"));
    }

    @Override
    public PrescriptionFragment getRelatedFragment() {
        return (PrescriptionFragment) super.getRelatedFragment();
    }

    @Override
    public void preInit() {

    }

    public void requestForNewRecord(){
        try {

            if (getRelatedFragment().getMyActivity().getPatient().hasEndEpisode()) {
                Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.cant_edit_patient_data)).show();
            } else {
                getCurrentStep().changeToInit();

                this.prescription = prescriptionService.getLastPatientPrescription(((PatientPanelActivity) getRelatedActivity()).getPatient());


                if (prescription != null) {
                    this.prescription.setDispenses(dispenseService.getAllNotVoidedDispenseByPrescription(this.prescription));

                    if (!this.prescription.isClosed()) {

                        this.prescription.setPrescribedDrugs(prescribedDrugService.getAllByPrescription(this.prescription));

                        newPrescriptionMustBeEspetial = true;
                        Utilities.displayConfirmationDialog(getRelatedActivity(), this.prescription.getPrescriptionAsString(), getRelatedActivity().getString(R.string.yes), getRelatedActivity().getString(R.string.no), PrescriptionVM.this).show();
                    } else {
                        doOnConfirmed();
                    }
                }
                else {
                    initNewRecord();
                }
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void initServices(@NonNull Application application) {
        prescriptionService = new PrescriptionService(application, getCurrentUser());
        regimenService = new TherapheuticRegimenService(application, getCurrentUser());
        lineService = new TherapeuthicLineService(application, getCurrentUser());
        dispenseTypeService = new DispenseTypeService(application, getCurrentUser());
        drugService = new DrugService(application, getCurrentUser());
        dispenseService = new DispenseService(application, getCurrentUser());
        prescribedDrugService = new PrescribedDrugService(application, getCurrentUser());
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
        return drugService.getAllByTherapeuticRegimen(therapeuticRegimen);
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

    private PrescribedDrug initNewPrescribedDrug(Drug drug) {
        return  new PrescribedDrug(drug, getPrescription());
    }

    private void doSave(){
        if (getCurrentStep().isApplicationStepSave()) getPrescription().generateNextSeq();

        List<PrescribedDrug> prescribedDrugs = new ArrayList<>();

        for (Listble drug : selectedDrugs){
            prescribedDrugs.add(initNewPrescribedDrug((Drug) drug));
        }

        getPrescription().setPrescribedDrugs(prescribedDrugs);

        if (getCurrentStep().isApplicationStepSave()) getPrescription().setUuid(Utilities.getNewUUID().toString());

        getPrescription().setSyncStatus(BaseModel.SYNC_SATUS_READY);

        if (newPrescriptionMustBeEspetial && !this.prescription.isUrgent()){
            Utilities.displayAlertDialog(getRelatedActivity(),getRelatedActivity().getString(R.string.prescription_must_be_urgent)).show();
            return;
        }

        String validationErrors = this.prescription.validate(getRelatedActivity());
        if (!Utilities.stringHasValue(validationErrors)) {
            try {
                if (getRelatedActivity().getApplicationStep().isApplicationStepSave()) {
                    if (oldPrescription != null) prescriptionService.closePrescription(oldPrescription);
                    
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

    public void save(){

        if(prescription.getId()==0) {


            if (getRelatedActivity().getApplicationStep().isApplicationStepRemove()) getCurrentStep().changetocreate();
                if (getRelatedActivity().getApplicationStep().isApplicationstepCreate()) getCurrentStep().changeToSave();

        }
        else {
            getCurrentStep().changeToEdit();
        }

        Utilities.displayConfirmationDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.confirm_prescription_save), getRelatedActivity().getString(R.string.yes), getRelatedActivity().getString(R.string.no), PrescriptionVM.this).show();

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
        this.prescription.setPrescribedDrugs(prescribedDrugService.getAllByPrescription(this.prescription));
    }

    public void loadLastPatientPrescription() throws SQLException {
        this.prescription = prescriptionService.getLastPatientPrescription(this.prescription.getPatient());
        this.prescription.setUrgentNotes(null);
        this.prescription.setUrgentPrescription(null);
        this.prescription.setPrescribedDrugs(prescribedDrugService.getAllByPrescription(this.prescription));
        this.prescription.setId(0);
    }

    public boolean checkIfPatientHasPrescriptions(){
        try {
            return prescriptionService.checkIfPatientHasPrescriptions(this.prescription.getPatient());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

        loadDrugs();
    }

    public void loadDrugs() {
        reloadDrugsSpinnerByRegime(this.prescription.getTherapeuticRegimen());
    }

    private void reloadDrugsSpinnerByRegime(TherapeuticRegimen regimen) {
        List<Drug> drugs = new ArrayList<>();
        try {
            if (regimen != null && isSeeOnlyOfRegime()) {
                drugs.addAll(getDrugsWithoutRectParanthesis(getAllDrugsOfTheraputicRegimen(regimen)));
            }
            else {
                drugs.addAll(getDrugsWithoutRectParanthesis(getAllDrugs()));
            }
            ((PrescriptionActivity)getRelatedActivity()).populateDrugs(drugs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String prescriptionCanBeEdited() throws SQLException {
        if (this.prescription.getExpiryDate() != null) return "Não é possivel alterar a prescrição "+this.prescription.getUiId()+ ", pois encontra-se expirada.";
        if (prescriptionHasDispenses()) return getRelatedActivity().getString(R.string.cant_edit_prescription_msg);
        if (!this.prescription.isSyncStatusReady(this.prescription.getSyncStatus())) return getRelatedActivity().getString(R.string.cant_edit_synced_prescription);
        return "";
    }

    @Override
    public void doOnConfirmed() {
        if (getCurrentStep().isApplicationStepInit()) {
            initNewRecord();
        }else if (getCurrentStep().isApplicationStepSave() || getCurrentStep().isApplicationStepEdit()){
            doSave();
        }
    }

    public List<Drug> getDrugsWithoutRectParanthesis(List<Drug> drugs) throws SQLException {
        return drugService.getDrugsWithoutRectParanthesis(drugs);
    }

    private void initNewRecord() {
        initNewPrescription();
        this.prescription.setPatient(((PatientPanelActivity) getRelatedActivity()).getPatient());

        getRelatedFragment().startPrescriptionActivity();
    }

    @Override
    public void doOnDeny() {
        if (getCurrentStep().isApplicationStepInit()) {
            getCurrentStep().changeToList();
        }else if (getCurrentStep().isApplicationStepSave()){
            getCurrentStep().changetocreate();
        }
    }



    public void checkIfMustBeUrgentPrescription() throws SQLException {
        oldPrescription = prescriptionService.getLastPatientPrescription(this.prescription.getPatient());

        oldPrescription.setDispenses(dispenseService.getAllNotVoidedDispenseByPrescription(oldPrescription));

        if (!oldPrescription.isClosed()) {
            newPrescriptionMustBeEspetial = true;
        }
    }

    public void checkInEditIfPrescriptionMustBeSpecial() {

        Prescription oldClosedPrescription= null;
        try {
            oldClosedPrescription = prescriptionService.getLastClosedPrescriptionByPatient(this.prescription.getPatient());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(oldClosedPrescription!= null){
            newPrescriptionMustBeEspetial = true;
            urgentPrescription=true;
        }

    }


    public void setPrescriptionSupply(Listble supply){
        getPrescription().setSupply(supply.getId());
        notifyPropertyChanged(BR.prescriptionSupply);
    }

    @Bindable
    public Listble getPrescriptionSupply(){
        return Utilities.findOnArray(this.durations, SimpleValue.fastCreate(getPrescription().getSupply()));
    }

    @Bindable
    public Listble getSelectedDrug() {
        return selectedDrug;
    }

    public void setUrgentNotes(Listble supply){
        getPrescription().setUrgentNotes(supply.getDescription());
        notifyPropertyChanged(BR.urgentNotes);
    }

    @Bindable
    public Listble getUrgentNotes(){
        return Utilities.findOnArray(this.motives, SimpleValue.fastCreate(getPrescription().getUrgentNotes()));
    }

    public void setSelectedDrug(Listble selectedDrug) {
        this.selectedDrug = (Drug) selectedDrug;
        notifyPropertyChanged(BR.selectedDrug);
    }

    @Bindable
    public Listble getPrescriptionDispenseType(){
        return this.prescription.getDispenseType();
    }

    public void setPrescriptionDispenseType(Listble precriptionType){
        this.prescription.setDispenseType((DispenseType) precriptionType);
        notifyPropertyChanged(BR.prescriptionDispenseType);
    }

    @Bindable
    public Listble getPrescriptionRegimen(){
        return this.prescription.getTherapeuticRegimen();
    }

    public void setPrescriptionRegimen(Listble regimen){
        this.prescription.setTherapeuticRegimen((TherapeuticRegimen) regimen);
        notifyPropertyChanged(BR.prescriptionRegimen);
        loadDrugs();
    }

    @Bindable
    public Listble getPrescriptionLine(){
        return this.prescription.getTherapeuticLine();
    }

    public void setPrescriptionLine(Listble line){
        this.prescription.setTherapeuticLine((TherapeuticLine) line);
        notifyPropertyChanged(BR.prescriptionLine);
    }

    public void addSelectedDrug(){
        if (selectedDrugs == null) selectedDrugs = new ArrayList<>();

        if(selectedDrug != null){
            if (!selectedDrugs.contains(selectedDrug)) {
                selectedDrug.setListPosition(selectedDrugs.size()+1);
                selectedDrug.setListType(Listble.PRESCRIPTION_DRUG_LISTING);
                selectedDrugs.add(selectedDrug);
                Collections.sort(selectedDrugs);

                ((PrescriptionActivity) getRelatedActivity()).displaySelectedDrugs();

                setSelectedDrug(null);

                notifyPropertyChanged(BR.selectedDrug);
            }else {
                Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.drug_data_duplication_msg)).show();
            }
        }else{
            Utilities.displayAlertDialog(getRelatedActivity(),"Campo medicamento está vazio. Por favor, seleccione um medicamento para adicionar à lista.").show();
        }


    }

    public List<Listble> getSelectedDrugs() {
        return selectedDrugs;
    }

    public List<SimpleValue> getDurations() {
        return durations;
    }

    public List<SimpleValue>  getMotives() {
        return motives;
    }

    public void setSelectedDrugs(List<Listble> drugs) {
        this.selectedDrugs = drugs;
    }

    public void changeInitialDataViewStatus(View view){
        ((PrescriptionActivity) getRelatedActivity()).changeFormSectionVisibility(view);
    }

    public void setPrescriptionDate(Date date) {
        this.prescription.setPrescriptionDate(date);
        notifyPropertyChanged(BR.prescriptionDate);
    }

    @Bindable
    public Date getPrescriptionDate() {
        return this.prescription.getPrescriptionDate();
    }
}
