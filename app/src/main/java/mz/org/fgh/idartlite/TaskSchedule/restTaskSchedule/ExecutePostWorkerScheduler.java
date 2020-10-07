package mz.org.fgh.idartlite.TaskSchedule.restTaskSchedule;

import android.content.Context;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import mz.org.fgh.idartlite.TaskSchedule.workScheduler.RestPostConfigDataWorkerScheduler;
import mz.org.fgh.idartlite.TaskSchedule.workScheduler.RestPostPatientDataWorkerScheduler;

public class ExecutePostWorkerScheduler {

    private static final String TAG = "ExecutePostWorkerSchedu";
    public static final int JOB_ID = 1000;
    private Context context;

    public ExecutePostWorkerScheduler(Context context) {

        this.context = context;
    }

    public void initPostPatientDataTaskWork() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest periodicPostDataWorkRequest = new PeriodicWorkRequest.Builder(RestPostPatientDataWorkerScheduler.class, 8, TimeUnit.HOURS)
                .setConstraints(constraints)
               // .setInitialDelay(8,TimeUnit.HOURS)
                .addTag("DISPENSE_ID " + JOB_ID)
                .build();

        WorkManager.getInstance(context).enqueue(periodicPostDataWorkRequest);
    }


    public void initPostStockDataTaskWork() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest periodicPostDataWorkRequest = new PeriodicWorkRequest.Builder(RestPostConfigDataWorkerScheduler.class, 8, TimeUnit.HOURS)
                .setConstraints(constraints)
                // .setInitialDelay(8,TimeUnit.HOURS)
                .addTag("STOCK_ID " + JOB_ID)
                .build();

        WorkManager.getInstance(context).enqueue(periodicPostDataWorkRequest);
    }

}
