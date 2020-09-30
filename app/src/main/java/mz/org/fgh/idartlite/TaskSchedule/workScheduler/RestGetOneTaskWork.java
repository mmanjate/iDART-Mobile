package mz.org.fgh.idartlite.TaskSchedule.workScheduler;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import mz.org.fgh.idartlite.service.restService.RestPatientService;

public class RestGetOneTaskWork extends Worker {
    private static final String TAG = "RestGetOneTaskWork";

    public RestGetOneTaskWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        try {
            Log.d(TAG, "doWork: Runnig Patients");
            RestPatientService.restGetAllPatient();
            Log.d(TAG, "doWork: Runnig Clinics");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }
}
