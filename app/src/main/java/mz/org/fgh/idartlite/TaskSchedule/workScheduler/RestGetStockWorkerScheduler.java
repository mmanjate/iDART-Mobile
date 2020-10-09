package mz.org.fgh.idartlite.TaskSchedule.workScheduler;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.rest.RESTServiceHandler;
import mz.org.fgh.idartlite.service.ClinicService;
import mz.org.fgh.idartlite.service.restService.RestStockService;

public class RestGetStockWorkerScheduler extends Worker {

    private static final String TAG = "RestGetStockWorkerSched";
    private  ClinicService clinicService;

    public RestGetStockWorkerScheduler(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        clinicService = new ClinicService(BaseService.getApp(), null);
        try {
            if (RESTServiceHandler.getServerStatus(BaseService.baseUrl)) {
                Log.d(TAG, "doWork: Sync Stock Data");
                RestStockService.restGetStock(clinicService.getCLinic().get(0));
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
