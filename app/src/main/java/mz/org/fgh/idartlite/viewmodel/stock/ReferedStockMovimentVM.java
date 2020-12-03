package mz.org.fgh.idartlite.viewmodel.stock;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.ArrayList;
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
            List<Drug> drugs = getRelatedService().getAllDrugsWithStock();
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

        referedStockMovimentList = new ArrayList<>();
    }

    public void addSelectedDrug(){
        ReferedStockMoviment referedStockMoviment = initNewReferedStockMoviment();

        if (!referedStockMovimentList.contains(referedStockMoviment)) {
            referedStockMovimentList.add(referedStockMoviment);

            setSelectedDrug(null);

            getRelatedActivity().displaySelectedDrugs();

            notifyPropertyChanged(BR.selectedDrug);
        }else {
            Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.drug_data_duplication_msg)).show();
        }
    }

    private ReferedStockMoviment initNewReferedStockMoviment() {
        getRelatedRecord().getStock().setDrug(selectedDrug);
        getRelatedRecord().clone();

        setSelectedRecord(initRecord());

        notifyPropertyChanged(BR.relatedRecord);

        return getRelatedRecord().clone();
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
        return Utilities.parseIntToString(getRelatedRecord().getStock().getUnitsReceived());
    }

    public void setUnitsReceived(String unitsReceived){
        getRelatedRecord().getStock().setUnitsReceived(Integer.valueOf(unitsReceived));
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
    public String getOrderNumber(){
        return getRelatedRecord().getStock().getOrderNumber();
    }

    public void setOrderNumber(String orderNumber){
        getRelatedRecord().getStock().setOrderNumber(orderNumber);
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
        try {
            getRelatedService().saveMany(this.referedStockMovimentList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Bindable
    public Drug getSelectedDrug() {
        return selectedDrug;
    }

    public void setSelectedDrug(Listble selectedDrug) {
        this.selectedDrug = (Drug) selectedDrug;
        notifyPropertyChanged(BR.selectedDrug);
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
}