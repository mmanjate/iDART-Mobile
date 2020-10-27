package mz.org.fgh.idartlite.TaskSchedule.restTaskSchedule;

import android.content.Context;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import mz.org.fgh.idartlite.TaskSchedule.workScheduler.RestGetConfigWorkerScheduler;
import mz.org.fgh.idartlite.TaskSchedule.workScheduler.RestGetEpisodeWorkerScheduler;
import mz.org.fgh.idartlite.TaskSchedule.workScheduler.RestGetPatientDataWorkerScheduler;
import mz.org.fgh.idartlite.TaskSchedule.workScheduler.RestGetStockWorkerScheduler;
import mz.org.fgh.idartlite.TaskSchedule.workScheduler.RestPatchStockConfigWorkerScheduler;
import mz.org.fgh.idartlite.TaskSchedule.workScheduler.RestPostStockWorkerScheduler;
import mz.org.fgh.idartlite.TaskSchedule.workScheduler.RestPostPatientDataWorkerScheduler;

public class ExecuteWorkerScheduler {

    private static final String TAG = "ExecutePostWorkerSchedu";
    public static final int JOB_ID = 1000;
    private Context context;

    public ExecuteWorkerScheduler(Context context) {

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
