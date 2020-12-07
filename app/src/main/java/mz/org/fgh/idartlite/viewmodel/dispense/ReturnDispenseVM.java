package mz.org.fgh.idartlite.viewmodel.dispense;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.listener.dialog.IDialogListener;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.ReturnedDrug;
import mz.org.fgh.idartlite.service.dispense.DispenseDrugService;
import mz.org.fgh.idartlite.service.dispense.DispenseService;
import mz.org.fgh.idartlite.service.dispense.IDispenseDrugService;
import mz.org.fgh.idartlite.service.dispense.IDispenseService;
import mz.org.fgh.idartlite.service.dispense.IReturnedDrugService;
import mz.org.fgh.idartlite.service.dispense.ReturnedDrugService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.dispense.ReturnDispenseActivity;

public class ReturnDispenseVM extends BaseViewModel implements IDialogListener {


    private IDispenseService dispenseService;

    private IDispenseDrugService dispenseDrugService;

    private IReturnedDrugService returnedDrugService;

    private Dispense dispense;

    List<Listble> returnedDrugs;

    private Date returnDate;

    private String notes;


    public ReturnDispenseVM(@NonNull Application application) {
        super(application);
        dispenseService = new DispenseService(application, getCurrentUser());
        dispenseDrugService= new DispenseDrugService(application,getCurrentUser());
        returnedDrugService=new ReturnedDrugService(application,getCurrentUser());

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
        returnedDrugs=new ArrayList<>();
    }

    public void populateFormData(){
        List<DispensedDrug> dispenseDrugs =new ArrayList<>();
        try {
            dispenseDrugs=  this.findDispensedDrugByDispense(getDispense());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // List<Listble> dispensedDrugs= new ArrayList<>();
        for (DispensedDrug dispenseDrug:
                dispenseDrugs)
        {
            ReturnedDrug returnedDrug = new ReturnedDrug();

            returnedDrug.setListType(Listble.RETURN_DRUG_LISTING);
            returnedDrug.setDispensedDrug(dispenseDrug);

            returnedDrugs.add(returnedDrug);

        }
    }


    public void doSave() {

       // Utilities.displayConfirmationDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.confirm_prescription_save), getRelatedActivity().getString(R.string.yes), getRelatedActivity().getString(R.string.no), ReturnDispenseVM.this).show();

        List<ReturnedDrug> drugsToReturn = new ArrayList<>();

        for (Listble returnDrug:
                returnedDrugs) {

            if (((ReturnedDrug) returnDrug).getQtyToModify() > 0) {
                ((ReturnedDrug) returnDrug).setDateReturned(returnDate);
                ((ReturnedDrug) returnDrug).setNotes(notes);
                drugsToReturn.add((ReturnedDrug) returnDrug);
            }

        }

        for (ReturnedDrug returnedDrug:drugsToReturn){

            String validationErros=returnedDrug.validate(getRelatedActivity());
            if(validationErros.isEmpty()){
                try {
                    returnedDrugService.createReturnedDrug(returnedDrug);
                    Utilities.displayConfirmationDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.would_like_to_create_new_prescription), getRelatedActivity().getString(R.string.yes), getRelatedActivity().getString(R.string.no), ((ReturnDispenseActivity)getRelatedActivity())).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            else {
                Utilities.displayAlertDialog( getRelatedActivity(),validationErros).show();
            }

        }





    }

    public void save(){
       // getCurrentStep().changeToSave();

        Utilities.displayConfirmationDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.confirm_return_drugs_save), getRelatedActivity().getString(R.string.yes), getRelatedActivity().getString(R.string.no), ReturnDispenseVM.this).show();

    }

    public List<DispensedDrug> findDispensedDrugByDispense(Dispense dispense) throws SQLException {
        return dispenseDrugService.findDispensedDrugByDispenseId(dispense.getId());
    }

    public ReturnDispenseActivity getRelatedActivity() {
        return (ReturnDispenseActivity) super.getRelatedActivity();
    }

    @Bindable
    public Clinic getClinic(){
        return getCurrentClinic();
    }

    public Dispense getDispense() {
        return dispense;
    }

    public void setDispense(Dispense dispense) {
        this.dispense = dispense;
    }



    @Bindable
    public String getNotes() {
        return notes;

    }

    public void setNotes(String notes) {
        this.notes = notes;
        notifyPropertyChanged(BR.notes);
    }


    @Bindable
    public Date getReturnDate() {
        return returnDate;

    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
        notifyPropertyChanged(BR.returnDate);
    }

    public List<Listble> getReturnedDrugs() {
        return returnedDrugs;
    }

    public void setReturnedDrugs(List<Listble> returnedDrugs) {
        this.returnedDrugs = returnedDrugs;
    }

    @Override
    public void doOnConfirmed() {
        getCurrentStep().changeToSave();
        doSave();
    }

    @Override
    public void doOnDeny() {

    }

    @Override
    public void preInit() {

    }
}
