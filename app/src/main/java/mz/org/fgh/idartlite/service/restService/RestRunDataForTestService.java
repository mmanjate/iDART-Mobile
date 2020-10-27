package mz.org.fgh.idartlite.service.restService;

import android.app.Application;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.service.ClinicService;
import mz.org.fgh.idartlite.service.DispenseService;
import mz.org.fgh.idartlite.service.EpisodeService;
import mz.org.fgh.idartlite.service.StockService;

public class RestRunDataForTestService extends BaseService {
    public RestRunDataForTestService(Application application, User currentUser) {
        super(application, currentUser);

        DispenseService dispenseService = new DispenseService(application, currentUser);
        StockService stockService = new StockService(application, currentUser);
        EpisodeService episodeService = new EpisodeService(application, currentUser);

        ClinicService clinicService = new ClinicService(application, currentUser);
        List<Stock> stockList;
        List<Dispense> dispenseList;
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
            RestStockService.restGetStock(clinicService.getCLinic().get(0));
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
            dispenseList = dispenseService.getAllDispenseByStatus(BaseModel.SYNC_SATUS_READY);
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
                for (Episode episode : episodeList) {
                    RestEpisodeService.restPostEpisode(episode);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
