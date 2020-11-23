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

public class InventoryVM extends BaseViewModel {

    private Drug selectedDrug;

    private List<Drug> drugs;

    private List<Listble> adjustmentList;

    private int currentSelectedDrugPosition;

    public InventoryVM(@NonNull Application application) {
        super(application);

        determineSelectedDrug();
    }

    @Override
    protected IBaseService initRelatedService() {
        return getServiceProvider().get(IventoryService.class);
    }

    private void determineSelectedDrug() {
        if (getSelectedRecord().getId() > 0){
            try {
                List<StockAjustment> stockAjustments = getRelatedService().getAllStockAjustmentsOfInventory(getSelectedRecord());

                for (Drug drug : this.drugs){
                    for (StockAjustment ajustment : stockAjustments){
                        if (ajustment.getStock().getDrug().equals(drug)){
                            drug.addAjustmentInfo(ajustment);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            for (Drug drug : this.drugs){
                if (drug.getAjustmentInfo() == null){
                    setSelectedDrug(drug);
                    break;
                }
            }
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

        try {
            if (Utilities.listHasElements(selectedDrug.getAjustmentInfo())){
                adjustmentList.addAll(selectedDrug.getAjustmentInfo());
            }else {
                List<Stock> drugStocks = getRelatedService().getAllOfDrug(selectedDrug);

                if (Utilities.listHasElements(drugStocks)) {
                    for (Stock stock : drugStocks) {
                        StockAjustment stockAjustment = initNewStockAjustment(stock);

                        if (!adjustmentList.contains(stockAjustment)) {

                            stockAjustment.setListType(Listble.INVENTORY_LISTING);
                            adjustmentList.add(stockAjustment);

                        } else {
                            Utilities.displayAlertDialog(getRelatedActivity(), "Ja se encontra a visualizar todos os lotes do medicamento seleccionado").show();
                        }
                    }

                } else {
                    Utilities.displayAlertDialog(getRelatedActivity(), "Medicamento seleccionado não possui stock registado.").show();
                }
            }
            notifyPropertyChanged(BR.selectedDrug);
            notifyPropertyChanged(BR.drugAutocompleteLabel);

            if (getRelatedActivity() != null) getRelatedActivity().displaySelectedDrugStockAjustmentInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            getRelatedService().initInventory(getRelatedRecord());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void finalizeInventory(){
        try {
            getRelatedService().closeInventory(getRelatedRecord());
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
            setSelectedDrug(this.drugs.get(currentSelectedDrugPosition - 1));
        }
    }

    public void nextDrug(){
        if (currentSelectedDrugPosition+1 >= this.drugs.size()) {
            Utilities.displayAlertDialog(getRelatedActivity(), "Não há mais medicamentos por visualizar, o corrente é o último da lista").show();
            
        }else {
            setSelectedDrug(this.drugs.get(currentSelectedDrugPosition + 1));
        }
    }

    @Bindable
    public String getDrugAutocompleteLabel(){
        return "Medicamento "+(currentSelectedDrugPosition+1) +" de "+drugs.size();
    }
}
