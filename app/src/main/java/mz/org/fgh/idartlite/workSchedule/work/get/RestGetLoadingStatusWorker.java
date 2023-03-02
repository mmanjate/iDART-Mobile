package mz.org.fgh.idartlite.workSchedule.work.get;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import mz.org.fgh.idartlite.rest.service.Patient.RestPatientService;

public class RestGetLoadingStatusWorker extends Worker {
    public RestGetLoadingStatusWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }


    @NonNull
    @Override
    public Result doWork() {
        try {
            RestPatientService.checkLoadingStatus();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success();
    }



}
