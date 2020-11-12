package mz.org.fgh.idartlite.viewmodel.stock;

import android.app.Application;

import androidx.annotation.NonNull;

import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.service.stock.IStockService;
import mz.org.fgh.idartlite.service.stock.StockService;
import mz.org.fgh.idartlite.view.stock.StockActivity;

public class StockVM extends BaseViewModel {

    private IStockService stockService;

    public StockVM(@NonNull Application application) {
        super(application);
        stockService = new StockService(application, getCurrentUser());
    }

    public StockActivity getRelatedActivity() {
        return (StockActivity) super.getRelatedActivity();
    }
}
