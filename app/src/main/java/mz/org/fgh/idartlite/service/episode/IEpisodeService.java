package mz.org.fgh.idartlite.service.episode;

import android.app.Application;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.patient.Patient;


public interface IEpisodeService extends IBaseService<Episode> {

    public List<Episode> getAllEpisodesByPatient(Patient patient) throws SQLException;

    public Episode getLatestByPatient(Patient patient) throws SQLException;

    public Episode findEpisodeWithStopReasonByPatient(Patient patient) throws SQLException;


    public void createEpisode(Episode episode) throws SQLException ;
    public void udpateEpisode(Episode episode) throws SQLException ;
    public void deleteEpisode(Episode episode) throws SQLException ;

    public void saveEpisodeFromRest(LinkedTreeMap<String, Object> patient, Patient localPatient) ;

    public boolean patientHasEndingEpisode(Patient patient);

    public List<Episode> getAllEpisodeByStatus(String status) throws SQLException ;

    public void saveOnEpisodeEnding(LinkedTreeMap<String, Object> episode) ;

    public boolean checkEpisodeExists(LinkedTreeMap<String, Object> episode);

    public List<Episode> getAllStartEpisodesBetweenStartDateAndEndDate(Date start, Date end,long limit,long offset) throws SQLException;


    List<Episode> getAllStartEpisodesBetweenStartDateAndEndDate(Date start, Date end) throws SQLException;

    List<Episode> getAllEpisodeByStatusAndDispenseStatus(String status, String dispenseStatus) throws SQLException;
}
