package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.service.StockService;
import mz.org.fgh.idartlite.view.stock.StockActivity;

public class StockVM extends BaseViewModel {

    private Clinic clinic;
    private StockService stockService;

    public StockVM(@NonNull Application application) {
        super(application);
        stockService = new StockService(application, getCurrentUser());
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public StockActivity getRelatedActivity() {
        return (StockActivity) super.getRelatedActivity();
    }
}
