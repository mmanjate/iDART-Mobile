package mz.org.fgh.idartlite.viewmodel.stock;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
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

    private List<Drug> bkpDrugs;

    private List<Listble> adjustmentList;

    private List<Listble> drugList;

    private int currentSelectedDrugPosition;

    private boolean geral;

    public InventoryVM(@NonNull Application application) {
        super(application);
    }

    @Override
    public void preInit() {
        try {
            if (getCurrentStep().isApplicationStepEdit() || getCurrentStep().isApplicationStepDisplay()){
                this.drugs = getRelatedService().getAllDrugsOnInventory(getRelatedRecord());
            }else
                this.drugs = getRelatedService().getAllDrugsWithExistingLote();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        determineSelectedDrug();
    }

    @Override
    protected IBaseService initRelatedService() {
        return getServiceProvider().get(IventoryService.class);
    }

    private void determineSelectedDrug() {
        try {
            List<StockAjustment> stockAjustments = new ArrayList<>();

            if (getSelectedRecord().getId() > 0) {
                stockAjustments = getRelatedService().getAllStockAjustmentsOfInventory(getSelectedRecord());
            }

            if (getCurrentStep().isApplicationStepDisplay() && Utilities.listHasElements(stockAjustments)) {
                this.adjustmentList = Utilities.parseList(stockAjustments, Listble.class);
            } else if (getCurrentStep().isApplicationStepEdit() || getCurrentStep().isApplicationStepDisplay()) {

                if (Utilities.listHasElements(stockAjustments)) {
                    for (Drug drug : this.drugs) {
                        for (StockAjustment ajustment : stockAjustments) {
                            if (ajustment.getStock().getDrug().equals(drug)) {
                                if (!Utilities.listHasElements(drug.getAjustmentInfo())) {
                                    drug.addAjustmentInfo(ajustment);
                                }else if (!drug.getAjustmentInfo().contains(ajustment)) {
                                    drug.addAjustmentInfo(ajustment);
                                }
                            }
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
                                    getRelatedService().saveAjustment(drug.getAjustmentInfo().get(drug.getAjustmentInfo().size()-1));
                                }
                            }
                        }
                    }
                }

                bkpDrugs = new ArrayList<>();

                for (Drug d : drugs){
                    bkpDrugs.add(d);
                }

                if (getSelectedRecord().getId() > 0) {

                    boolean found = false;

                    for (Drug drug : this.drugs) {
                        if (drug.getAjustmentInfo().get(0).getId() <= 0) {
                            setSelectedDrug(drug);
                            found = true;
                            break;
                        }
                    }

                    if (!found) setSelectedDrug(this.drugs.get(0));
                } else {
                    setSelectedDrug(this.drugs.get(0));
                }
            }

            } catch(SQLException e){
                e.printStackTrace();
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
            if (isGeral()) {
                getCurrentStep().changeToEdit();

                getRelatedService().initInventory(getRelatedRecord());

                getRelatedActivity().summarizeView(View.VISIBLE);

                this.drugs = getRelatedService().getAllDrugsWithExistingLote();

                determineSelectedDrug();

                getRelatedActivity().populateDrugs(this.drugs);

            }else {
                for (Drug drug : drugs){
                    drug.setListType(Listble.INVENTORY_SELECTION_LISTING);
                }

                getRelatedActivity().displaySelectedDrugsForSelection();
                getCurrentStep().changeToSelect();
            }

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

        notifyChange();

    }

    public void finalizeInventory(){
        if (mustConfirmSubmid()){
            Utilities.displayConfirmationDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.exists_fields_with_zero), getRelatedActivity().getString(R.string.yes), getRelatedActivity().getString(R.string.no), InventoryVM.this).show();
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
            Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.no_more_previous_drug)).show();
        }else {
            updateAjustmentInfo();

            setSelectedDrug(this.drugs.get(currentSelectedDrugPosition - 1));
        }
    }

    public void nextDrug(){

        if (this.drugs.size() != bkpDrugs.size()){
            this.drugs = bkpDrugs;
        }
        if (currentSelectedDrugPosition+1 >= this.drugs.size()) {
            Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.no_more_next_drug)).show();
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
        return getRelatedActivity().getString(R.string.drug)+" "+(currentSelectedDrugPosition+1) + " "+getRelatedActivity().getString(R.string.of)+ " "+drugs.size();
    }

    public void back() {

        if (getCurrentStep().isApplicationStepList()){
            determineSelectedDrug();
            getCurrentStep().changeToEdit();
            notifyChange();
        }
        else getRelatedActivity().finish();
    }

    public void printCountForm(){
        try {
            getRelatedActivity().createPdf(this.drugs);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Bindable
    public boolean isGeral() {
        return geral;
    }

    public void setGeral(boolean geral) {
        this.geral = geral;
        notifyPropertyChanged(BR.geral);
    }

    private boolean isGeneralSelected(String iventoryType){
        return getRelatedActivity().getString(R.string.geral).equalsIgnoreCase(iventoryType);
    }

    public void onInventoryTypeClick(String iventoryType) {
        if (isGeneralSelected(iventoryType)){
            setGeral(true);
        }else
            setGeral(false);

        notifyChange();
    }

    public void continueInventory(){
        try {
            getRelatedService().initInventory(getRelatedRecord());
            //getRelatedActivity().summarizeView(View.VISIBLE);

            if (!isGeral()) {
                this.drugs.clear();

                for (Listble listble : selectedListbles) {
                    this.drugs.add((Drug) listble);
                }
            }
            getCurrentStep().changeToEdit();

            determineSelectedDrug();

            notifyChange();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Drug> getSelectedDrugs() {
        //List<Drug> drugList = null;
        try {
            return getRelatedService().getAllDrugsOnInventory(getRelatedRecord());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
