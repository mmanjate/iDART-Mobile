package mz.org.fgh.idartlite.TaskSchedule.workScheduler;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.List;

import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.rest.RESTServiceHandler;
import mz.org.fgh.idartlite.service.StockService;
import mz.org.fgh.idartlite.service.restService.RestStockService;

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
