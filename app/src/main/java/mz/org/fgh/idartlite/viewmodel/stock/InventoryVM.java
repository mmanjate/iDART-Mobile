package mz.org.fgh.idartlite.viewmodel.stock;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Iventory;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.StockAjustment;
import mz.org.fgh.idartlite.service.drug.DrugService;
import mz.org.fgh.idartlite.service.stock.IventoryService;
import mz.org.fgh.idartlite.service.stock.StockService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.stock.inventory.IventoryActivity;

public class InventoryVM extends BaseViewModel {

    private Drug selectedDrug;

    private List<Drug> drugs;

    private List<Listble> adjustmentList;

    private int currentSelectedDrugPosition;

    public InventoryVM(@NonNull Application application) {
        super(application);
    }

    @Override
    protected BaseModel initRecord() {
        return new Iventory();
    }

    @Override
    protected Class<IventoryService> getRecordServiceClass() {
        return IventoryService.class;
    }

    @Override
    public IventoryService getRecordService() {
        return (IventoryService) super.getRecordService();
    }

    @Override
    public Iventory getSelectedRecord() {
        return (Iventory) super.getSelectedRecord();
    }

    @Override
    protected void initFormData() {
        try {
            this.drugs = getAllDrugs();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IventoryActivity getRelatedActivity() {
        return (IventoryActivity) super.getRelatedActivity();
    }

    protected List<Drug> getAllDrugs() throws SQLException {
        return ((DrugService)baseServiceFactory.get(DrugService.class)).getAll();
    }

    public Drug getSelectedDrug() {
        return selectedDrug;
    }

    public void setSelectedDrug(Drug selectedDrug) {
        this.selectedDrug = selectedDrug;

        currentSelectedDrugPosition = this.drugs.indexOf(selectedDrug);

        if (adjustmentList == null) adjustmentList = new ArrayList<>();

        try {
            List<Stock> drugStocks = ((StockService)baseServiceFactory.get(StockService.class)).getAll(selectedDrug);

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

                getRelatedActivity().displaySelectedDrugs();

            }else {
                Utilities.displayAlertDialog(getRelatedActivity(), "Medicamento seleccionado não possui stock registado.").show();
            }
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

    public void setDrugs(List<Drug> drugs) {
        this.drugs = drugs;
    }

    public void changeDataViewStatus(View view){
        getRelatedActivity().changeFormSectionVisibility(view);
    }

    public List<Listble> getAdjustmentList() {
        return adjustmentList;
    }

    public void initInventory(){

    }

    public void finalizeInventory(){

    }

    public void previousDrug(){
        setSelectedDrug(this.drugs.get(currentSelectedDrugPosition-1));
    }

    public void nextDrug(){
        setSelectedDrug(this.drugs.get(currentSelectedDrugPosition+1));
    }
}
