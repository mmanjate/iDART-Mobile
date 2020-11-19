package mz.org.fgh.idartlite.rest.service;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.rest.BaseRestService;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.service.clinic.ClinicService;
import mz.org.fgh.idartlite.service.clinic.IClinicService;
import mz.org.fgh.idartlite.service.dispense.DispenseService;
import mz.org.fgh.idartlite.service.dispense.IDispenseService;
import mz.org.fgh.idartlite.service.episode.EpisodeService;
import mz.org.fgh.idartlite.service.episode.IEpisodeService;
import mz.org.fgh.idartlite.service.patient.IPatientService;
import mz.org.fgh.idartlite.service.patient.PatientService;
import mz.org.fgh.idartlite.service.stock.IStockService;
import mz.org.fgh.idartlite.service.stock.StockService;

public class RestRunDataForTestService extends BaseRestService {
    public RestRunDataForTestService(Application application, User currentUser) {
        super(application, currentUser);

        IDispenseService dispenseService = new DispenseService(application, currentUser);
        IStockService stockService = new StockService(application, currentUser);
        IEpisodeService episodeService = new EpisodeService(application, currentUser);

        IClinicService clinicService = new ClinicService(application, currentUser);
        IPatientService patientService = new PatientService(application, currentUser);
        List<Stock> stockList;
        List<Dispense> dispenseList;
        List<Patient> patientList;
        Episode episode;
        RestPharmacyTypeService.restGetAllPharmacyType();
        RestFormService.restGetAllForms();
        RestDrugService.restGetAllDrugs();
        RestDiseaseTypeService.restGetAllDiseaseType();
        RestDispenseTypeService.restGetAllDispenseType();
        RestTherapeuticRegimenService.restGetAllTherapeuticRegimen();
        RestTherapeuticLineService.restGetAllTherapeuticLine();
        RestPatientService.restGetAllPatient(null);
        RestEpisodeService.restGetAllReadyEpisodes(null);
        RestEpisodeService.restGetAllEpisodes(null);
        try {
            RestStockService.restGetStock(clinicService.getAllClinics().get(0));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            stockList = stockService.getStockByStatus(BaseModel.SYNC_SATUS_READY);
            if (stockList != null)
                if (stockList.size() > 0) {
                    for (Stock stock : stockList) {
                        RestStockService.restPostStock(stock);
                    }
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            stockList = stockService.getStockByStatus(BaseModel.SYNC_SATUS_UPDATED);
            if (stockList != null)
                if (stockList.size() > 0) {
                    for (Stock stock : stockList) {
                        RestStockService.restGetAndPatchStockLevel(stock);
                    }
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            dispenseList = dispenseService.getAllDispensesByStatus(BaseModel.SYNC_SATUS_READY);
            if (dispenseList != null)
                if (dispenseList.size() > 0) {
                    for (Dispense dispense : dispenseList) {
                        RestDispenseService.restPostDispense(dispense);
                    }
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {

            List<Episode> episodeList=episodeService.getAllEpisodeByStatus(BaseModel.SYNC_SATUS_READY);
            if(episodeList != null && episodeList.size() > 0) {
                for (Episode episode1 : episodeList) {
                    RestEpisodeService.restPostEpisode(episode1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            patientList = patientService.getALLPatient();
            if (patientList != null)
                if (patientList.size() > 0) {
                    for (Patient patient : patientList) {
                        episode = episodeService.findEpisodeWithStopReasonByPatient(patient);
                        if (episode == null)
                            RestDispenseService.restGetLastDispense(patient);
                    }
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
