package mz.org.fgh.idartlite.workSchedule.work;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import mz.org.fgh.idartlite.base.rest.ServiceWatcher;
import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.rest.service.Disease.RestDiseaseTypeService;
import mz.org.fgh.idartlite.rest.service.Dispense.RestDispenseTypeService;
import mz.org.fgh.idartlite.rest.service.Drug.RestDrugService;
import mz.org.fgh.idartlite.rest.service.Form.RestFormService;
import mz.org.fgh.idartlite.rest.service.Regimen.RestTherapeuticRegimenService;
import mz.org.fgh.idartlite.rest.service.RestTerritoryService;
import mz.org.fgh.idartlite.rest.service.TherapeuticLine.RestTherapeuticLineService;
import mz.org.fgh.idartlite.rest.service.clinic.RestPharmacyTypeService;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.SecurePreferences;

import static mz.org.fgh.idartlite.view.home.ui.settings.AppSettingsFragment.DOWNLOAD_MESSAGE_STATUS;

public class MetaDataSyncWorker extends Worker {

    public static final String TAG = "download_job";
    private static final String PROGRESS = "PROGRESS";
    private static final long DELAY = 6000;
    private static final String SETTINGS_PREF = "settings_pref";
    private static final String APP_LAST_SYNC_DATE = "app_last_sync";


    public MetaDataSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        setProgressAsync(new Data.Builder().putInt(PROGRESS, 0).build());
    }

    @NonNull
    @Override
    public Result doWork() {

        ServiceWatcher watcher = ServiceWatcher.fastCreate(ServiceWatcher.TYPE_DOWNLOAD);

        try {
            RestTerritoryService.restGetAllCountries();
            RestTerritoryService.restGetAllProvinces();
            RestTerritoryService.restGetAllDistricts();

            RestPharmacyTypeService.restGetAllPharmacyType(watcher);
            RestFormService.restGetAllForms(watcher);
            RestDrugService.restGetAllDrugs(watcher);
            RestDiseaseTypeService.restGetAllDiseaseType(watcher);
            RestDispenseTypeService.restGetAllDispenseType(watcher);
            RestTherapeuticRegimenService.restGetAllTherapeuticRegimen(watcher);
            RestTherapeuticLineService.restGetAllTherapeuticLine(watcher);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException exception) {
            // ... handle exception
        }

        SecurePreferences settingsPreferences = new SecurePreferences(BaseService.getApp(), SETTINGS_PREF,  true);
        settingsPreferences.put(APP_LAST_SYNC_DATE, DateUtilities.formatToDDMMYYYY_HHMISS(DateUtilities.getCurrentDate()));
        Data outputData = new Data.Builder().putString(DOWNLOAD_MESSAGE_STATUS, watcher.getUpdates()).build();

        return Result.success(outputData);
    }
}
