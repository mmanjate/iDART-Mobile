package mz.org.fgh.idartlite.viewmodel.stock;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.io.Serializable;
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
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.OperationType;
import mz.org.fgh.idartlite.model.ReferedStockMoviment;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.service.stock.IReferedStockService;
import mz.org.fgh.idartlite.service.stock.ReferedStockMovimentServiceImpl;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.stock.referedstock.ReferedStockMovimentActivity;


public class ReferedStockMovimentVM extends BaseViewModel {

    private List<Listble> drugList;
    private List<Listble> operationTypeList;

    private List<ReferedStockMoviment> referedStockMovimentList;

    private Drug selectedDrug;

    public ReferedStockMovimentVM(@NonNull Application application) {
        super(application);
    }

    @Override
    protected IBaseService initRelatedService() {
        return getServiceProvider().get(ReferedStockMovimentServiceImpl.class);
    }

    @Override
    protected BaseModel initRecord() {
        ReferedStockMoviment referedStockMoviment = new ReferedStockMoviment();
        referedStockMoviment.setStock(new Stock());

        return referedStockMoviment;
    }

    @Override
    protected void initFormData() {
        try {

            getRelatedService().tempCreateOperationTypes();

            List<Drug> drugs = getRelatedService().getallDrugs();
            List<OperationType> operationTypes = getRelatedService().getAllOperationTypes();

            this.drugList = Utilities.parseList(drugs, Listble.class);
            this.operationTypeList = Utilities.parseList(operationTypes, Listble.class);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IReferedStockService getRelatedService() {
        return (IReferedStockService) super.getRelatedService();
    }

    @Override
    @Bindable
    public ReferedStockMoviment getRelatedRecord() {
        return (ReferedStockMoviment) super.getRelatedRecord();
    }

    @Override
    public void preInit() {

        if (getRelatedRecord().getStock() != null) getRelatedRecord().getStock().setClinic(getCurrentClinic());
        referedStockMovimentList = new ArrayList<>();

        if (getRelatedRecord().getId() > 0){
            try {
                referedStockMovimentList = getRelatedService().getAllRelatedReferedStockMoviments(getRelatedRecord());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (getCurrentStep().isApplicationstepCreate() || getCurrentStep().isApplicationStepEdit() || getCurrentStep().isApplicationStepList()){
            setViewListEditButton(true);
            setViewListRemoveButton(true);
        }
    }

    @Override
    public void setSelectedListble(Listble selectedListble) {

        super.setSelectedListble(selectedListble);

        try {
            if (getCurrentStep().isApplicationStepRemove()){

                if (selectedListble.getId() > 0) getRelatedService().delete((ReferedStockMoviment) selectedListble);

                if (Utilities.listHasElements(referedStockMovimentList)){
                    getCurrentStep().changeToList();
                }else getRelatedActivity().finish();
            }else {
                setSelectedRecord((Serializable) selectedListble);

                setSelectedDrug(getRelatedRecord().getStock().getDrug());

                notifyPropertyChanged(BR.currentStep);
                notifyChange();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSelectedDrug(){

        getRelatedRecord().getStock().setDrug(selectedDrug);
        ReferedStockMoviment clone = getRelatedRecord().clone();

        String validationErrors = clone.isValid(getRelatedActivity());

        if (Utilities.stringHasValue(validationErrors)){
            Utilities.displayAlertDialog(getRelatedActivity(), validationErrors).show();
        }else {
            ReferedStockMoviment referedStockMoviment = initNewReferedStockMoviment();

        if (!referedStockMovimentList.contains(referedStockMoviment)) {
            referedStockMoviment.setListType(Listble.REFERED_STOCK_LISTING);
            referedStockMoviment.setSyncStatus(BaseModel.SYNC_SATUS_READY);
            referedStockMovimentList.add(referedStockMoviment);

            setSelectedDrug(null);

            getRelatedActivity().displayReferedStockMoviments();

            notifyChange();
        }

        /**
         * Update header info
         */
        if (getCurrentStep().isApplicationStepEdit()){
            for (ReferedStockMoviment r : referedStockMovimentList){
                r.setDate(getRelatedRecord().getDate());
                r.setOperationType(getRelatedRecord().getOperationType());
                r.setOrderNumber(getRelatedRecord().getOrderNumber());
            }
        }
        }
    }

    private ReferedStockMoviment initNewReferedStockMoviment() {
        getRelatedRecord().getStock().setDrug(selectedDrug);
        ReferedStockMoviment clone = getRelatedRecord().clone();

        setSelectedRecord(initRecord());

        getRelatedRecord().setDate(clone.getDate());
        getRelatedRecord().setOperationType(clone.getOperationType());
        getRelatedRecord().setOrderNumber(clone.getOrderNumber());
        getRelatedRecord().setOrigin(clone.getOrigin());
        getRelatedRecord().getStock().setClinic(getCurrentClinic());

        return clone;
    }

    @Bindable
    public Stock getCurStock(){
        return getRelatedRecord().getStock();
    }

    public void setCurStock(Stock stock){
        getRelatedRecord().setStock(stock);
        notifyPropertyChanged(BR.curStock);
    }

    @Bindable
    public String getBatchNumber(){
        return getRelatedRecord().getStock().getBatchNumber();
    }

    public void setBatchNumber(String batchNumber){
        getRelatedRecord().getStock().setBatchNumber(batchNumber);
        notifyPropertyChanged(BR.batchNumber);
    }

    @Bindable
    public String getUnitsReceived(){
        if (getRelatedRecord().getStock().getUnitsReceived() == 0) return "";

        return Utilities.parseIntToString(getRelatedRecord().getStock().getUnitsReceived());
    }

    public void setUnitsReceived(String unitsReceived){
        getRelatedRecord().getStock().setUnitsReceived(Utilities.stringHasValue(unitsReceived) ? Integer.parseInt(unitsReceived) : 0);
        getRelatedRecord().setQuantity(getRelatedRecord().getStock().getUnitsReceived());
        notifyPropertyChanged(BR.unitsReceived);
    }

    @Bindable
    public Listble getOperationType(){
        return (Listble) getRelatedRecord().getOperationType();
    }

    public void setOperationType(Listble operationType){
        getRelatedRecord().setOperationType((OperationType) operationType);
        notifyPropertyChanged(BR.operationType);
    }

    @Bindable
    public Date getRegistrationDate(){
        return getRelatedRecord().getDate();
    }

    public void setRegistrationDate(Date registrationDate){
        getRelatedRecord().setDate(registrationDate);
        getRelatedRecord().getStock().setDateReceived(registrationDate);
        notifyPropertyChanged(BR.registrationDate);
    }

    @Bindable
    public Date getExpireDate(){
        return getRelatedRecord().getStock().getExpiryDate();
    }

    public void setExpireDate(Date expireDate){
        getRelatedRecord().getStock().setExpiryDate(expireDate);
        notifyPropertyChanged(BR.expireDate);
    }

    @Bindable
    public String getOrderNumber(){
        return getRelatedRecord().getOrderNumber();
    }

    public void setOrderNumber(String orderNumber){
        getRelatedRecord().getStock().setOrderNumber(orderNumber);
        getRelatedRecord().setOrderNumber(orderNumber);
        notifyPropertyChanged(BR.orderNumber);
    }

    @Override
    public ReferedStockMovimentActivity getRelatedActivity() {
        return (ReferedStockMovimentActivity) super.getRelatedActivity();
    }

    public void changeDataViewStatus(View view){
        getRelatedActivity().changeFormSectionVisibility(view);
    }

    @Override
    public void save() {
        if (getCurrentStep().isApplicationStepEdit()){
            addSelectedDrug();
            getCurrentStep().changeToList();
            notifyChange();
        }

        try {
            getRelatedService().saveMany(this.referedStockMovimentList);

            Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.operation_success), ReferedStockMovimentVM.this).show();

            getRelatedActivity().displayReferedStockMoviments();

        } catch (SQLException e) {
            Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.operation_failure) + e.getLocalizedMessage()).show();
            e.printStackTrace();
        }

    }

    @Override
    public void doOnConfirmed() {
        if (getCurrentStep().isApplicationStepEdit() || getCurrentStep().isApplicationstepCreate()){
            getRelatedActivity().finish();
        }
    }

    @Bindable
    public Drug getSelectedDrug() {
        return selectedDrug;
    }

    public void setSelectedDrug(Listble selectedDrug) {
        this.selectedDrug = (Drug) selectedDrug;
        notifyChange();
    }

    public List<Listble> getDrugList() {
        return drugList;
    }

    public List<Listble> getOperationTypeList() {
        return operationTypeList;
    }

    public List<Listble> getReferedStockMovimentList() {
        return Utilities.parseList(referedStockMovimentList, Listble.class);
    }

    public void cancel(){
        if (getCurrentStep().isApplicationStepEdit()){
            preInit();
            getCurrentStep().changeToList();

            getRelatedActivity().displayReferedStockMoviments();
            notifyChange();
        }else {
            getRelatedActivity().finish();
        }
    }

    @Bindable
    public boolean isHideList(){
        return getCurrentStep().isApplicationStepEdit();
    }
}