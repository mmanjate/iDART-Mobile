package mz.org.fgh.idartlite.viewmodel.stock;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.BR;
import mz.org.fgh.idartlite.adapter.recyclerview.listable.Listble;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.model.DestroyedDrug;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.service.drug.DrugService;
import mz.org.fgh.idartlite.service.stock.DestroyedStockDrugService;
import mz.org.fgh.idartlite.service.stock.IDestroyedStockDrug;
import mz.org.fgh.idartlite.service.stock.StockService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.stock.destroy.DestroyStockActivity;

public class DestroyStockVM extends BaseViewModel {

    private Drug selectedDrug;

    private List<Drug> drugs;

    private List<Listble> stockList;

    public DestroyStockVM(@NonNull Application application) {
        super(application);
    }

    @Override
    protected IBaseService initRelatedService() {
        return getServiceProvider().get(DestroyedStockDrugService.class);
    }

    @Override
    protected BaseModel initRecord() {
        return new DestroyedDrug();
    }



    @Override
    protected void initFormData() {
        try {
            this.drugs = getRelatedService().getAllDrugsWithExistingLote();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected List<Drug> getAllDrugs() throws SQLException {
        return ((DrugService) serviceProvider.get(DrugService.class)).getAll();
    }

    @Override
    public void save() {
        try {

                List<DestroyedDrug> stocksToDestroy = new ArrayList<>();

                String errorMsg = getRelatedRecord().isValid(getRelatedActivity());

                if (Utilities.stringHasValue(errorMsg)) {
                    Utilities.displayAlertDialog(getRelatedActivity(), errorMsg).show();
                } else {
                    for (Listble listble : getRelatedActivity().getStockToDestroy()) {
                        if (listble.getQtyToModify() > 0) {
                            stocksToDestroy.add(((DestroyedDrug) listble));
                            stocksToDestroy.get(stocksToDestroy.size() - 1).setNotes(getRelatedRecord().getNotes());
                            stocksToDestroy.get(stocksToDestroy.size() - 1).setDate(getRelatedRecord().getDate());
                            stocksToDestroy.get(stocksToDestroy.size() - 1).setSyncStatus(BaseModel.SYNC_SATUS_READY);

                            if (getRelatedRecord().getStock() == null) getRelatedRecord().setStock(((DestroyedDrug) listble).getStock());
                        }
                    }

                    if (!Utilities.listHasElements(stocksToDestroy)) {
                        Utilities.displayAlertDialog(getRelatedActivity(), "Não indicou nenhuma quantidade de stock para destruir.").show();
                    } else {

                        List<DestroyedDrug> destroyedDrugs = getRelatedService().getAllRelatedDestroyedStocks(stocksToDestroy.get(0));
                        if (getCurrentStep().isApplicationstepCreate() && Utilities.listHasElements(destroyedDrugs)) {
                            Utilities.displayConfirmationDialog(getRelatedActivity(), "Ja existe um registo de destruição de stock para este medicamento nesta data, gostaria de editar o mesmo?", "SIM", "NÃO", DestroyStockVM.this).show();
                        }else {
                            getRelatedService().saveAll(stocksToDestroy);
                            getCurrentStep().changeToList();
                            Utilities.displayAlertDialog(getRelatedActivity(), "Operação efectuada com sucesso.", DestroyStockVM.this).show();
                        }
                    }

                }

            } catch(SQLException e){
                e.printStackTrace();
            }

    }

    @Override
    public void doOnConfirmed() {

        if (getCurrentStep().isApplicationStepEdit()){
            getRelatedActivity().finish();
        }else  if (getCurrentStep().isApplicationstepCreate()){
            try {
                setSelectedRecord(getRelatedService().getAllRelatedDestroyedStocks(getRelatedRecord()).get(0));
                getCurrentStep().changeToEdit();
                loadRelatedData();

                getRelatedActivity().recreate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else  if (getCurrentStep().isApplicationStepList()){
            getRelatedActivity().finish();
        }
    }

    @Override
    public IDestroyedStockDrug getRelatedService() {
        return (DestroyedStockDrugService) super.getRelatedService();
    }

    @Override
    public void preInit() {

    }

    @Override
    public DestroyedDrug getRelatedRecord() {
        return (DestroyedDrug) super.getRelatedRecord();
    }

    @Bindable
    public Drug getSelectedDrug() {
        return selectedDrug;
    }

    public List<Drug> getDrugs() {
        return drugs;
    }

    public void setDestryedDrugDate(Date date){
        getRelatedRecord().setDate(date);
        notifyPropertyChanged(BR.destryedDrugDate);
    }

    @Override
    public DestroyStockActivity getRelatedActivity() {
        return (DestroyStockActivity) super.getRelatedActivity();
    }

    @Bindable
    public Date getDestryedDrugDate(){
        return getRelatedRecord().getDate();
    }

    public void changeDataViewStatus(View view){
        getRelatedActivity().changeFormSectionVisibility(view);
    }

    public void setSelectedDrug(Drug selectedDrug) {
        this.selectedDrug = selectedDrug;
    }


    public void addSelectedDrug(){
        stockList = new ArrayList<>();

        try {
            List<Stock> drugStocks = ((StockService) serviceProvider.get(StockService.class)).getAll(selectedDrug);

            if (Utilities.listHasElements(drugStocks)) {
                for (Stock stock : drugStocks) {
                    DestroyedDrug destroyedDrug = initNewDestroiedDrug(stock);

                    destroyedDrug.setListType(Listble.STOCK_DESTROY_LISTING);
                    stockList.add(destroyedDrug);

                    notifyPropertyChanged(BR.selectedDrug);
                }
                getRelatedActivity().displaySelectedDrugs();
            }else {
                Utilities.displayAlertDialog(getRelatedActivity(), "Medicamento seleccionado não possui stock.").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DestroyedDrug initNewDestroiedDrug(Stock stock) {
        return new DestroyedDrug(stock);
    }

    @Bindable
    public String getNotes() {
        return getRelatedRecord().getNotes();
    }

    public void setNotes(String notes) {
        getRelatedRecord().setNotes(notes);
        notifyPropertyChanged(BR.notes);
    }

    public List<Listble> getStockList() {
        return stockList;
    }

    public void loadRelatedData() {
        this.stockList = new ArrayList<>();

        try {
            List<DestroyedDrug> destroyedDrugs = getRelatedService().getAllRelatedDestroyedStocks(getRelatedRecord());

            if (Utilities.listHasElements(destroyedDrugs)) {
                for (DestroyedDrug destroyedDrug : destroyedDrugs) {
                    stockList.add(destroyedDrug);
                }

                getRelatedActivity().displaySelectedDrugs();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
