package mz.org.fgh.idartlite.workSchedule.executor;

import android.content.Context;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.google.common.util.concurrent.ListenableFuture;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import mz.org.fgh.idartlite.model.AppSettings;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.rest.service.Stock.RestStockService;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.workSchedule.work.DataSyncWorker;
import mz.org.fgh.idartlite.workSchedule.work.get.PatientWorker;
import mz.org.fgh.idartlite.workSchedule.work.get.RestGetConfigWorkerScheduler;
import mz.org.fgh.idartlite.workSchedule.work.get.RestGetEpisodeWorkerScheduler;
import mz.org.fgh.idartlite.workSchedule.work.get.RestGetPatientDispensationWorkerScheduler;
import mz.org.fgh.idartlite.workSchedule.work.get.StockWorker;
import mz.org.fgh.idartlite.workSchedule.work.patch.RestPatchStockConfigWorkerScheduler;
import mz.org.fgh.idartlite.workSchedule.work.patch.RestPacthPatientWorker;
import mz.org.fgh.idartlite.workSchedule.work.post.RestPostNewPatientWorkerScheduler;
import mz.org.fgh.idartlite.workSchedule.work.post.RestPostPatientDataWorkerScheduler;
import mz.org.fgh.idartlite.workSchedule.work.post.RestPostStockWorkerScheduler;

public class WorkerScheduleExecutor {

    private static final String TAG = "ExecutePostWorkerSchedu";
    public static final int JOB_ID = 1000;
    public static final int ONE_TIME_REQUEST_JOB_ID = 1001;
    private Context context;
    private List<AppSettings> appSettings;
    private static int daysOfWeek = 7;
    private WorkManager workManager;
    private static WorkerScheduleExecutor instance;
    private Clinic currClinic;


    private WorkerScheduleExecutor(Context context, Clinic currClinic, List<AppSettings> appSettings) {
        this.workManager = WorkManager.getInstance(context);
        this.context = context;
        this.appSettings = appSettings;
        this.currClinic = currClinic;
    }

    public static WorkerScheduleExecutor getInstance(Context context, Clinic currClinic, List<AppSettings> appSettings) {
        if (instance == null) {
            instance = new WorkerScheduleExecutor(context, currClinic, appSettings);
        }
        return instance;
    }

    public Clinic getCurrClinic() {
        return currClinic;
    }

    public void setCurrClinic(Clinic currClinic) {
        this.currClinic = currClinic;
    }

    public WorkManager getWorkManager() {
        return workManager;
    }

    private int getMetadataSyncInterval(){
        for (AppSettings setting : this.appSettings){
            if (setting.isMetadataSyncPediorSetting()){
                return Integer.parseInt(setting.getValue()) * daysOfWeek;
            }
        }
        throw new RuntimeException("Metadata Sync Interval not set");
    }

    private int getDataSyncInterval(){
        for (AppSettings setting : this.appSettings){
            if (setting.isDataSyncPeriodSetting()){
                return Integer.parseInt(setting.getValue());
            }
        }
        throw new RuntimeException("Data Sync Interval not set");
    }

    public void initConfigTaskWork() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest periodicConfigDataWorkRequest = new PeriodicWorkRequest.Builder(RestGetConfigWorkerScheduler.class, getMetadataSyncInterval(), TimeUnit.DAYS)
                .setConstraints(constraints)
                .setInitialDelay(7,TimeUnit.DAYS)
                .addTag("CONF_ID " + JOB_ID)
                .build();

        workManager.enqueue(periodicConfigDataWorkRequest);
    }

    public void initDataTaskWork() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();

            PeriodicWorkRequest periodicPatientDataWorkRequest = new PeriodicWorkRequest.Builder(PatientWorker.class, getDataSyncInterval(), TimeUnit.HOURS)
                .setConstraints(constraints)
                .setInitialDelay(8,TimeUnit.HOURS)
                .addTag("PATIENT_ID " + JOB_ID)
                .build();

        workManager.enqueue(periodicPatientDataWorkRequest);
    }

    public void initStockTaskWork() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest periodicConfigDataWorkRequest = new PeriodicWorkRequest.Builder(StockWorker.class, getDataSyncInterval(), TimeUnit.HOURS)
                .setConstraints(constraints)
                .setInitialDelay(1,TimeUnit.HOURS)
                .addTag("INIT_STOCK_ID " + JOB_ID)
                .build();

        workManager.enqueue(periodicConfigDataWorkRequest);
    }

    public void initPatientDispenseTaskWork() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest periodicConfigDataWorkRequest = new PeriodicWorkRequest.Builder(RestGetPatientDispensationWorkerScheduler.class, getDataSyncInterval(), TimeUnit.HOURS)
                .setConstraints(constraints)
                .setInitialDelay(1,TimeUnit.HOURS)
                .addTag("INIT_PATIENT_DISPENSE_ID " + JOB_ID)
                .build();

        workManager.enqueue(periodicConfigDataWorkRequest);
    }

    public void initPostNewPatientDataTaskWork() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest periodicPostNewPatienWorkRequest = new PeriodicWorkRequest.Builder(RestPostNewPatientWorkerScheduler.class, getDataSyncInterval(), TimeUnit.HOURS)
                .setConstraints(constraints)
                .setInitialDelay(1,TimeUnit.HOURS)
                .addTag("NEW_PATIENT_ID " + JOB_ID)
                .build();

        workManager.enqueue(periodicPostNewPatienWorkRequest);
    }

    public void initPostPatientDataTaskWork() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest periodicPostDataWorkRequest = new PeriodicWorkRequest.Builder(RestPostPatientDataWorkerScheduler.class, getDataSyncInterval(), TimeUnit.HOURS)
                .setConstraints(constraints)
                .setInitialDelay(2,TimeUnit.HOURS)
                .addTag("DISPENSE_ID " + JOB_ID)
                .build();

        workManager.enqueue(periodicPostDataWorkRequest);
    }

    public void initPostPatientFaltosoOrAbandonoDataTaskWork() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest periodicPostDataWorkRequest = new PeriodicWorkRequest.Builder(RestPacthPatientWorker.class, getDataSyncInterval(), TimeUnit.HOURS)
                .setConstraints(constraints)
                .setInitialDelay(2,TimeUnit.HOURS)
                .addTag("FALTOSOS " + JOB_ID)
                .build();

        workManager.enqueue(periodicPostDataWorkRequest);
    }


    public void initPostStockDataTaskWork() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest periodicPostDataWorkRequest = new PeriodicWorkRequest.Builder(RestPostStockWorkerScheduler.class, getDataSyncInterval(), TimeUnit.HOURS)
                .setConstraints(constraints)
                .setInitialDelay(1,TimeUnit.HOURS)
                .addTag("STOCK_ID " + JOB_ID)
                .build();

        workManager.enqueue(periodicPostDataWorkRequest);
    }

    public void initPatchStockDataTaskWork() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest periodicPostDataWorkRequest = new PeriodicWorkRequest.Builder(RestPatchStockConfigWorkerScheduler.class, getDataSyncInterval(), TimeUnit.HOURS)
                .setConstraints(constraints)
                 .setInitialDelay(8,TimeUnit.HOURS)
                .addTag("STOCK_LEVEL_ID " + JOB_ID)
                .build();

        workManager.enqueue(periodicPostDataWorkRequest);
    }


    public void initEpisodeTaskWork() {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresCharging(true)
                .build();

        PeriodicWorkRequest periodicEpisodeDataWorkRequest = new PeriodicWorkRequest.Builder(RestGetEpisodeWorkerScheduler.class, getDataSyncInterval(), TimeUnit.HOURS)
                .setConstraints(constraints)
                .setInitialDelay(8,TimeUnit.HOURS)
                .addTag("EPISODE ID " + JOB_ID)
                .build();

        workManager.enqueue(periodicEpisodeDataWorkRequest);
    }

    public void runOneTimePatientSync() {
        OneTimeWorkRequest patientOneTimeWorkRequest = new OneTimeWorkRequest.Builder(PatientWorker.class).addTag("ONE_TIME_PATIENT_ID" + ONE_TIME_REQUEST_JOB_ID).build();
        if (!Utilities.isWorkRunning("ONE_TIME_PATIENT_ID" + ONE_TIME_REQUEST_JOB_ID, workManager) && !Utilities.isWorkRunning("PATIENT_ID " + JOB_ID, workManager)) {
            workManager.enqueue(patientOneTimeWorkRequest);
        } else {
            Utilities.displayAlertDialog(this.context, "Existe neste momento em andamento uma sincronização em curso, impossível iniciar uma nova!").show();
        }
    }

    public void runOneTimeStockSync() {
        OneTimeWorkRequest stockOneTimeWorkRequest = new OneTimeWorkRequest.Builder(StockWorker.class).addTag("ONE_TIME_STOCK_ID" + ONE_TIME_REQUEST_JOB_ID).build();
        if (!Utilities.isWorkRunning("ONE_TIME_STOCK_ID" + ONE_TIME_REQUEST_JOB_ID, workManager) && !Utilities.isWorkRunning("INIT_STOCK_ID " + JOB_ID, workManager)) {
            workManager.enqueue(stockOneTimeWorkRequest);
            try {
                RestStockService.restPostStockCenter(currClinic);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void runPatinetAndStockSync() {
        this.runOneTimePatientSync();
        this.runOneTimeStockSync();
    }

    public void runOneTimeFaltososSync() {
        OneTimeWorkRequest faltososRequest = new OneTimeWorkRequest.Builder(RestPacthPatientWorker.class).addTag("ONE_TIME_FALTOSO_ID" + ONE_TIME_REQUEST_JOB_ID).build();
        if (!Utilities.isWorkRunning("ONE_TIME_FALTOSO_ID" + ONE_TIME_REQUEST_JOB_ID, workManager)) {
            workManager.enqueue(faltososRequest);
        }
    }

    public void runOneTimeDataSync() {
        OneTimeWorkRequest dataRequest = new OneTimeWorkRequest.Builder(DataSyncWorker.class).addTag("ONE_TIME_DATA_ID" + ONE_TIME_REQUEST_JOB_ID).build();
        if (!Utilities.isWorkRunning("ONE_TIME_DATA_ID" + ONE_TIME_REQUEST_JOB_ID, workManager)) {
            workManager.enqueue(dataRequest);
        }
    }

    public void runDataSyncNow() {
        this.runOneTimePatientSync();
        this.runOneTimeStockSync();
        this.runOneTimeFaltososSync();
        this.runOneTimeDataSync();
    }
}
