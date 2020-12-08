package mz.org.fgh.idartlite.viewmodel.stock;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.StockAjustment;
import mz.org.fgh.idartlite.model.inventory.Iventory;
import mz.org.fgh.idartlite.service.stock.IIventoryService;
import mz.org.fgh.idartlite.service.stock.IventoryService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.stock.inventory.IventoryActivity;
import mz.org.fgh.idartlite.view.stock.panel.StockActivity;

public class InventoryVM extends BaseViewModel {

    private Drug selectedDrug;

    private List<Drug> drugs;

    private List<Listble> adjustmentList;

    private int currentSelectedDrugPosition;

    public InventoryVM(@NonNull Application application) {
        super(application);
    }

    @Override
    public void preInit() {
        determineSelectedDrug();
    }

    @Override
    protected IBaseService initRelatedService() {
        return getServiceProvider().get(IventoryService.class);
    }

    private void determineSelectedDrug() {
        try {
            List<StockAjustment> stockAjustments = getRelatedService().getAllStockAjustmentsOfInventory(getSelectedRecord());

            for (Drug drug : this.drugs){
                for (StockAjustment ajustment : stockAjustments){
                    if (ajustment.getStock().getDrug().equals(drug)){
                        drug.addAjustmentInfo(ajustment);
                    }
                }
            }

            if (Utilities.listHasElements(drugs)) {
                for (Drug drug : drugs) {
                    if (!Utilities.listHasElements(drug.getAjustmentInfo())) {
                        List<Stock> drugStocks = getRelatedService().getAllOfDrug(drug);

                        if (Utilities.listHasElements(drugStocks)) {
                            for (Stock stock : drugStocks) {

                                drug.setAjustmentInfo(new ArrayList<>());
                                drug.getAjustmentInfo().add(initNewStockAjustment(stock));
                            }
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (getSelectedRecord().getId() > 0){

            boolean found = false;

            for (Drug drug : this.drugs){
                if (drug.getAjustmentInfo().get(0).getId() <= 0){
                    setSelectedDrug(drug);
                    found = true;
                    break;
                }
            }

            if (!found) setSelectedDrug(this.drugs.get(0));
        }else {
            setSelectedDrug(this.drugs.get(0));
        }
    }

    @Override
    protected BaseModel initRecord() {
        return new Iventory();
    }

    @Override
    public IIventoryService getRelatedService() {
        return (IIventoryService) super.getRelatedService();
    }

    @Override
    public Iventory getSelectedRecord() {
        return (Iventory) super.getSelectedRecord();
    }



    @Override
    protected void initFormData() {
        try {
            this.drugs = getRelatedService().getAllDrugsWithExistingLote();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IventoryActivity getRelatedActivity() {
        return (IventoryActivity) super.getRelatedActivity();
    }

    @Bindable
    public Drug getSelectedDrug() {
        return selectedDrug;
    }

    public void setSelectedDrug(Drug selectedDrug) {
        this.selectedDrug = selectedDrug;

        currentSelectedDrugPosition = this.drugs.indexOf(selectedDrug);

        if (this.adjustmentList == null) this.adjustmentList = new ArrayList<>();

        this.adjustmentList.clear();

        adjustmentList.addAll(selectedDrug.getAjustmentInfo());
        notifyPropertyChanged(BR.selectedDrug);
        notifyPropertyChanged(BR.drugAutocompleteLabel);

        if (getRelatedActivity() != null) getRelatedActivity().displaySelectedDrugStockAjustmentInfo();

    }

    private StockAjustment initNewStockAjustment(Stock stock) {
        return new StockAjustment(stock, getSelectedRecord());
    }

    public List<Drug> getDrugs() {
        return drugs;
    }

    public void changeDataViewStatus(View view){
        getRelatedActivity().changeFormSectionVisibility(view);
    }

    public List<Listble> getAdjustmentList() {
        return adjustmentList;
    }

    public void initInventory() {
        try {
            getCurrentStep().changeToEdit();

            getRelatedService().initInventory(getRelatedRecord());
            getRelatedActivity().summarizeView(View.VISIBLE);

            notifyPropertyChanged(BR.currentStep);
            notifyChange();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void summarizeInventory(){
        getCurrentStep().changeToList();

        this.adjustmentList = new ArrayList<>();
        for (Drug drug : drugs){
            if (Utilities.listHasElements(drug.getAjustmentInfo())) adjustmentList.addAll(drug.getAjustmentInfo());
        }
        getRelatedActivity().summarizeView(View.GONE);
        getRelatedActivity().displayResumeStockAjustmentInfo();

        notifyPropertyChanged(BR.currentStep);
        notifyChange();

    }

    public void finalizeInventory(){
        if (mustConfirmSubmid()){
            Utilities.displayConfirmationDialog(getRelatedActivity(), "Existem campos com quantidade zero (0) o stock actual existente para os respectivos lotes será reduzido a zero (0), prosseguir?", "SIM", "NÃO", InventoryVM.this).show();
        }else {
            doOnConfirmed();
        }
    }

    private boolean mustConfirmSubmid() {
        for (Drug drug : drugs){
            for (StockAjustment ajustment : drug.getAjustmentInfo()){
                if (ajustment.getStockCount() == 0){
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void doOnConfirmed() {
        super.doOnConfirmed();

        try {
            for (Drug drug : drugs){
                for (StockAjustment ajustment : drug.getAjustmentInfo()){
                   getSelectedRecord().addAjustment(ajustment);
                }
            }

            getRelatedService().closeInventory(getRelatedRecord());

            getRelatedActivity().nextActivityFinishingCurrentWithGenericParams(StockActivity.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iventory getRelatedRecord() {
        return (Iventory) super.getRelatedRecord();
    }

    public void previousDrug(){
        if (currentSelectedDrugPosition-1 < 0) {
            Utilities.displayAlertDialog(getRelatedActivity(), "Não há mais medicamentos por visualizar, o corrente é o primeiro da lista.").show();
        }else {
            updateAjustmentInfo();

            setSelectedDrug(this.drugs.get(currentSelectedDrugPosition - 1));
        }
    }

    public void nextDrug(){
        if (currentSelectedDrugPosition+1 >= this.drugs.size()) {
            Utilities.displayAlertDialog(getRelatedActivity(), "Não há mais medicamentos por visualizar, o corrente é o último da lista").show();
            
        }else {
            updateAjustmentInfo();
            setSelectedDrug(this.drugs.get(currentSelectedDrugPosition + 1));
        }
    }

    private void updateAjustmentInfo() {
        try {
        if (this.drugs.get(currentSelectedDrugPosition).getAjustmentInfo() == null){
            this.drugs.get(currentSelectedDrugPosition).setAjustmentInfo(new ArrayList<>());
            for (Listble listble : this.adjustmentList) {
                this.drugs.get(currentSelectedDrugPosition).getAjustmentInfo().add((StockAjustment) listble);
                if (getSelectedRecord().isOpen()) getRelatedService().saveAjustment((StockAjustment) listble);
            }
        }else {
            if (getSelectedRecord().isOpen()) {
                for (Listble listble : this.adjustmentList) {
                    getRelatedService().saveAjustment((StockAjustment) listble);
                }
            }
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Bindable
    public String getDrugAutocompleteLabel(){
        return "Medicamento "+(currentSelectedDrugPosition+1) +" de "+drugs.size();
    }

}
