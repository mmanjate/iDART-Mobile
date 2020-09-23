package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.service.DispenseService;
import mz.org.fgh.idartlite.service.EpisodeService;
import mz.org.fgh.idartlite.util.DateUtilitis;

public class EpisodeVM extends BaseViewModel {

    private EpisodeService episodeService;
    private DispenseService dispenseService;
    private Patient patient;
    private Episode episode;


    public EpisodeVM(@NonNull Application application) {
        super(application);

        episodeService = new EpisodeService(application, getCurrentUser());
        dispenseService = new DispenseService(application, getCurrentUser());
    }

    public List<Episode> gatAllOfPatient(Patient selectedPatient) throws SQLException {
        return episodeService.getAllEpisodesByPatient(selectedPatient);
    }

    public Dispense getLastDispenseOfPatient(Patient patient) throws SQLException {
        return dispenseService.getAllOfPatient(patient).get(0);
    }

    public void createEpisode(Episode episode) throws SQLException {
        episodeService.createEpisode(episode);
    }
    public void deleteEpisode(Episode episode) throws SQLException {
        episodeService.deleteEpisode(episode);
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
