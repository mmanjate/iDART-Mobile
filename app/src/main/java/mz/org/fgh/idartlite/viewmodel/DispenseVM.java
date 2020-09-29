package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.service.DispenseDrugService;
import mz.org.fgh.idartlite.service.DispenseService;
import mz.org.fgh.idartlite.service.DrugService;
import mz.org.fgh.idartlite.service.PrescriptionService;
import mz.org.fgh.idartlite.service.StockService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.HomeActivity;
import mz.org.fgh.idartlite.view.patient.CreateDispenseActivity;

public class DispenseVM extends BaseViewModel {

    private DispenseService dispenseService;

    private Dispense dispense;

    private boolean initialDataVisible;

    private boolean drugDataVisible;

    private PrescriptionService prescriptionService;

    private DispenseDrugService dispenseDrugService;

    private DrugService drugService;

    private StockService stockService;


    public DispenseVM(@NonNull Application application) {
        super(application);

        this.dispense = new Dispense();
        dispenseService = new DispenseService(application, getCurrentUser());
        prescriptionService = new PrescriptionService(application, getCurrentUser());
        dispenseDrugService = new DispenseDrugService(application, getCurrentUser());
        this.drugService = new DrugService(application, getCurrentUser());
        this.stockService = new StockService(application, getCurrentUser());

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

    public List<Drug> getAllDrugs() throws SQLException {
        return drugService.getAll();
    }

    public List<Stock> getAllStocksByClinicAndDrug(Clinic clinic, Drug drug) {

        List<Stock> stocks = new ArrayList<>();

        try {
            stocks = this.stockService.getAllStocksByClinicAndDrug(clinic, drug);
        } catch (SQLException e) {
            e.printStackTrace();
            Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.find_stock_list_error)+e.getLocalizedMessage()).show();
        }

        return stocks;
    }

    @Override
    public CreateDispenseActivity getRelatedActivity(){
        return (CreateDispenseActivity) super.getRelatedActivity();
    }

    public void save() {

        try {

            getRelatedActivity().loadFormData();

            this.dispenseService.createDispense(this.dispense);
            Utilities.displayConfirmationDialog(getRelatedActivity(), "Pretende aviar estes medicamentos?", "Sim", "Não", getRelatedActivity()).show();
        } catch (SQLException e) {
            e.printStackTrace();
            Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.save_error_msg)+e.getLocalizedMessage()).show();
        }
    }

}
