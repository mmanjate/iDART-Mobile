package mz.org.fgh.idartlite.workSchedule.executor;

import android.content.Context;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import mz.org.fgh.idartlite.workSchedule.work.get.RestGetConfigWorkerScheduler;
import mz.org.fgh.idartlite.workSchedule.work.get.RestGetEpisodeWorkerScheduler;
import mz.org.fgh.idartlite.workSchedule.work.get.RestGetPatientDataWorkerScheduler;
import mz.org.fgh.idartlite.workSchedule.work.get.RestGetPatientDispensationWorkerScheduler;
import mz.org.fgh.idartlite.workSchedule.work.get.RestGetStockWorkerScheduler;
import mz.org.fgh.idartlite.workSchedule.work.patch.RestPatchStockConfigWorkerScheduler;
import mz.org.fgh.idartlite.workSchedule.work.post.RestPostNewPatientWorkerScheduler;
import mz.org.fgh.idartlite.workSchedule.work.post.RestPostStockWorkerScheduler;
import mz.org.fgh.idartlite.workSchedule.work.post.RestPostPatientDataWorkerScheduler;

public class WorkerScheduleExecutor {

    private static final String TAG = "ExecutePostWorkerSchedu";
    public static final int JOB_ID = 1000;
    private Context context;

    public WorkerScheduleExecutor(Context context) {

        this.context = context;
    }

    public void initConfigTaskWork() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest periodicConfigDataWorkRequest = new PeriodicWorkRequest.Builder(RestGetConfigWorkerScheduler.class, 7, TimeUnit.DAYS)
                .setConstraints(constraints)
                .setInitialDelay(7,TimeUnit.DAYS)
                .addTag("CONF_ID " + JOB_ID)
                .build();

        WorkManager.getInstance(context).enqueue(periodicConfigDataWorkRequest);
    }

    public void initDataTaskWork() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest periodicPatientDataWorkRequest = new PeriodicWorkRequest.Builder(RestGetPatientDataWorkerScheduler.class, 8, TimeUnit.HOURS)
                .setConstraints(constraints)
                .setInitialDelay(8,TimeUnit.HOURS)
                .addTag("PATIENT_ID " + JOB_ID)
                .build();

        WorkManager.getInstance(context).enqueue(periodicPatientDataWorkRequest);
    }

    public void initStockTaskWork() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest periodicConfigDataWorkRequest = new PeriodicWorkRequest.Builder(RestGetStockWorkerScheduler.class, 1, TimeUnit.HOURS)
                .setConstraints(constraints)
                .setInitialDelay(1,TimeUnit.HOURS)
                .addTag("INIT_STOCK_ID " + JOB_ID)
                .build();

        WorkManager.getInstance(context).enqueue(periodicConfigDataWorkRequest);
    }

    public void initPatientDispenseTaskWork() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest periodicConfigDataWorkRequest = new PeriodicWorkRequest.Builder(RestGetPatientDispensationWorkerScheduler.class, 1, TimeUnit.HOURS)
                .setConstraints(constraints)
                .setInitialDelay(1,TimeUnit.HOURS)
                .addTag("INIT_PATIENT_DISPENSE_ID " + JOB_ID)
                .build();

        WorkManager.getInstance(context).enqueue(periodicConfigDataWorkRequest);
    }

    public void initPostNewPatientDataTaskWork() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest periodicPostNewPatienWorkRequest = new PeriodicWorkRequest.Builder(RestPostNewPatientWorkerScheduler.class, 1, TimeUnit.HOURS)
                .setConstraints(constraints)
                .setInitialDelay(1,TimeUnit.HOURS)
                .addTag("NEW_PATIENT_ID " + JOB_ID)
                .build();

        WorkManager.getInstance(context).enqueue(periodicPostNewPatienWorkRequest);
    }

    public void initPostPatientDataTaskWork() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest periodicPostDataWorkRequest = new PeriodicWorkRequest.Builder(RestPostPatientDataWorkerScheduler.class, 2, TimeUnit.HOURS)
                .setConstraints(constraints)
                .setInitialDelay(2,TimeUnit.HOURS)
                .addTag("DISPENSE_ID " + JOB_ID)
                .build();

        WorkManager.getInstance(context).enqueue(periodicPostDataWorkRequest);
    }


    public void initPostStockDataTaskWork() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest periodicPostDataWorkRequest = new PeriodicWorkRequest.Builder(RestPostStockWorkerScheduler.class, 8, TimeUnit.HOURS)
                .setConstraints(constraints)
                 .setInitialDelay(1,TimeUnit.HOURS)
                .addTag("STOCK_ID " + JOB_ID)
                .build();

        WorkManager.getInstance(context).enqueue(periodicPostDataWorkRequest);
    }

    public void initPatchStockDataTaskWork() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest periodicPostDataWorkRequest = new PeriodicWorkRequest.Builder(RestPatchStockConfigWorkerScheduler.class, 1, TimeUnit.HOURS)
                .setConstraints(constraints)
                 .setInitialDelay(8,TimeUnit.HOURS)
                .addTag("STOCK_LEVEL_ID " + JOB_ID)
                .build();

        WorkManager.getInstance(context).enqueue(periodicPostDataWorkRequest);
    }


    public void initEpisodeTaskWork() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest periodicEpisodeDataWorkRequest = new PeriodicWorkRequest.Builder(RestGetEpisodeWorkerScheduler.class, 8, TimeUnit.HOURS)
                .setConstraints(constraints)
                .setInitialDelay(8,TimeUnit.HOURS)
                .addTag("EPISODE ID " + JOB_ID)
                .build();

        WorkManager.getInstance(context).enqueue(periodicEpisodeDataWorkRequest);
    }
}
