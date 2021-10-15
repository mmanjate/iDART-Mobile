package mz.org.fgh.idartlite.workSchedule.work;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.rest.ServiceWatcher;
import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.model.AppSettings;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.inventory.Iventory;
import mz.org.fgh.idartlite.rest.service.ClinicInfo.RestClinicInfoService;
import mz.org.fgh.idartlite.rest.service.Dispense.RestDispenseService;
import mz.org.fgh.idartlite.rest.service.Episode.RestEpisodeService;
import mz.org.fgh.idartlite.rest.service.Patient.RestPatientService;
import mz.org.fgh.idartlite.rest.service.Stock.RestStockService;
import mz.org.fgh.idartlite.service.clinic.ClinicSectorService;
import mz.org.fgh.idartlite.service.clinic.ClinicService;
import mz.org.fgh.idartlite.service.clinic.IClinicSectorService;
import mz.org.fgh.idartlite.service.clinic.IClinicService;
import mz.org.fgh.idartlite.service.clinicInfo.ClinicInfoService;
import mz.org.fgh.idartlite.service.clinicInfo.IClinicInfoService;
import mz.org.fgh.idartlite.service.dispense.DispenseService;
import mz.org.fgh.idartlite.service.dispense.IDispenseService;
import mz.org.fgh.idartlite.service.episode.EpisodeService;
import mz.org.fgh.idartlite.service.episode.IEpisodeService;
import mz.org.fgh.idartlite.service.patient.IPatientService;
import mz.org.fgh.idartlite.service.patient.PatientService;
import mz.org.fgh.idartlite.service.prescription.IPrescriptionService;
import mz.org.fgh.idartlite.service.prescription.PrescriptionService;
import mz.org.fgh.idartlite.service.settings.AppSettingsService;
import mz.org.fgh.idartlite.service.settings.IAppSettingsService;
import mz.org.fgh.idartlite.service.stock.IIventoryService;
import mz.org.fgh.idartlite.service.stock.IStockService;
import mz.org.fgh.idartlite.service.stock.IventoryService;
import mz.org.fgh.idartlite.service.stock.StockService;
import mz.org.fgh.idartlite.service.territory.CountryService;
import mz.org.fgh.idartlite.service.territory.ICountryService;
import mz.org.fgh.idartlite.util.DateUtilities;
import mz.org.fgh.idartlite.util.SecurePreferences;
import mz.org.fgh.idartlite.util.Utilities;

import static mz.org.fgh.idartlite.view.home.ui.settings.AppSettingsFragment.UPLOAD_MESSAGE_STATUS;

public class RemoveDataSyncWorker extends Worker {

    public static final String TAG = "data_removal_job";
    private static final String PROGRESS = "PROGRESS";
    private static final long DELAY = 6000;

    public RemoveDataSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }


    @NonNull
    @Override
    public Result doWork() {
        IDispenseService dispenseService = new DispenseService(BaseService.getApp(), null);
        IPrescriptionService prescriptionService = new PrescriptionService(BaseService.getApp(), null);
        IAppSettingsService appSettings = new AppSettingsService(BaseService.getApp(), null);
        IIventoryService inventoryService = new IventoryService(BaseService.getApp(), null);



        List<Dispense> dispenseList;
        List<Prescription> prescriptionList;
        Date removeDate= null;

        ServiceWatcher watcher = ServiceWatcher.fastCreate(ServiceWatcher.TYPE_DOWNLOAD);

        try {

            AppSettings appSetting= appSettings.getRemovalDataSettings();

            if(Integer.parseInt(appSetting.getValue())==1){
                removeDate= DateUtilities.getDateOfPreviousDays(DateUtilities.getCurrentDate(),30);
            }else if(Integer.parseInt(appSetting.getValue())==2){
                removeDate= DateUtilities.getDateOfPreviousDays(DateUtilities.getCurrentDate(),60);
            }
            else if(Integer.parseInt(appSetting.getValue())==3){
                removeDate= DateUtilities.getDateOfPreviousDays(DateUtilities.getCurrentDate(),90);
            }
            else if(Integer.parseInt(appSetting.getValue())==4){
                removeDate= DateUtilities.getDateOfPreviousDays(DateUtilities.getCurrentDate(),120);
            }
            else {
                throw new Exception();
            }

            try {
                dispenseList = dispenseService.getAllDispensesToRemoveByDates(removeDate);
                if (dispenseList != null)
                    if (dispenseList.size() > 0) {
                        for (Dispense dispense : dispenseList) {
                            dispenseService.deleteDispenseAndDispensedDrugs(dispense);
                        }
                    }
            } catch (SQLException e) {
                e.printStackTrace();
            }

           /* try {
                prescriptionList = prescriptionService.getAllPrescriptionToRemoveByDate(removeDate);
                if (prescriptionList != null)
                    if (prescriptionList.size() > 0) {
                        for (Prescription prescription : prescriptionList) {
                            prescriptionService.deletePrescriptionAndPrescribedDrugs(prescription);
                        }
                    }
            } catch (SQLException e) {
                e.printStackTrace();
            }*/

            try {
                List<Iventory> inventorysToRemove = inventoryService.getPastInventoryToRemove(removeDate);
                if (inventorysToRemove != null)
                    if (inventorysToRemove.size() > 0) {
                        for (Iventory iventory : inventorysToRemove) {
                            inventoryService.removeInventoryAndStockAdjusment(iventory);
                        }
                    }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        while (!watcher.hasUpdates()){
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException exception) {

            }
        }

        Data outputData = new Data.Builder().putString(UPLOAD_MESSAGE_STATUS, watcher.getUpdates()).build();

        return Result.success(outputData);
    }
}
