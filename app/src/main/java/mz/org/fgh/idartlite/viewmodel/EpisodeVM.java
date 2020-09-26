package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.service.DispenseService;
import mz.org.fgh.idartlite.service.EpisodeService;
import mz.org.fgh.idartlite.util.DateUtilitis;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.view.patient.EpisodeActivity;
import mz.org.fgh.idartlite.view.patient.EpisodeFragment;
import mz.org.fgh.idartlite.view.patient.PrescriptionActivity;

public class EpisodeVM extends BaseViewModel {

    private EpisodeService episodeService;
    private DispenseService dispenseService;
    private Patient patient;
    private Episode episode;


    public EpisodeVM(@NonNull Application application) {
        super(application);
        initNewEpisode();
        episodeService = new EpisodeService(application, getCurrentUser());
        dispenseService = new DispenseService(application, getCurrentUser());
    }

    private void initNewEpisode() {
       this.episode = new Episode();
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

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    @Override
    public EpisodeActivity getRelatedActivity() {
        return (EpisodeActivity) super.getRelatedActivity();
    }

    public void save1(){



        getRelatedActivity().populateEpisode();
        try {
        if(DateUtilitis.dateDiff(getEpisode().getEpisodeDate(),
                gatAllOfPatient(getPatient()).get(0).getEpisodeDate(), DateUtilitis.DAY_FORMAT) > 0){
            episodeService.createEpisode(this.episode);
            Utilities.displayAlertDialog( getRelatedActivity(),"Episodio Criado Com Sucesso").show();
        }

            else {
            Utilities.displayAlertDialog( getRelatedActivity(),"Data de Visita nao pode ser menor que a do primeiro episodio").show();
        }

        } catch (SQLException e) {
            e.printStackTrace();
            Utilities.displayAlertDialog(getRelatedActivity(), getRelatedActivity().getString(R.string.save_error_msg)+e.getLocalizedMessage()).show();
        }
    }
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
