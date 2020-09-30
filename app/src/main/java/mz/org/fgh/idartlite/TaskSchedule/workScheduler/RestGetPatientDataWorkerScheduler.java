package mz.org.fgh.idartlite.TaskSchedule.workScheduler;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.rest.RESTServiceHandler;
import mz.org.fgh.idartlite.service.restService.RestPatientService;

public class RestGetPatientDataWorkerScheduler extends Worker {
    private static final String TAG = "RestGetPatientDataWorke";

    public RestGetPatientDataWorkerScheduler(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            if (RESTServiceHandler.getServerStatus(BaseService.baseUrl)) {
                Log.d(TAG, "doWork: Sync Patient Data");
                RestPatientService.restGetAllPatient();
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
