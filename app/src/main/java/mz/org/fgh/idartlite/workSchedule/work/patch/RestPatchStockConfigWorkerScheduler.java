package mz.org.fgh.idartlite.workSchedule.work.patch;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.List;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.rest.helper.RESTServiceHandler;
import mz.org.fgh.idartlite.service.stock.StockService;
import mz.org.fgh.idartlite.rest.service.RestStockService;

public class RestPatchStockConfigWorkerScheduler extends Worker {

    private static final String TAG = "RestPatchStockConfigWor";

    private List<Stock> stockList;
    private StockService stockService;

    public RestPatchStockConfigWorkerScheduler(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            stockService = new StockService(BaseService.getApp(), null);
            if (RESTServiceHandler.getServerStatus(BaseService.baseUrl)) {
                Log.d(TAG, "doWork: Sync Stock Data");
                stockList = stockService.getStockByStatus(BaseModel.SYNC_SATUS_UPDATED);
                if (stockList != null)
                    if (stockList.size() > 0) {
                        for (Stock stock : stockList) {
                            RestStockService.restGetAndPatchStockLevel(stock);
                        }
                    } else
                        Log.d(TAG, "doWork: Sem Stock para sincronizar");
            } else {
                Log.e(TAG, "Response Servidor Offline");
                return Result.failure();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success();
    }
}
