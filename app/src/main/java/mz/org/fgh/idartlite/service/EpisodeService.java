package mz.org.fgh.idartlite.service;

import android.app.Application;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.util.Utilities;

public class EpisodeService extends BaseService {

    public EpisodeService(Application application, User currUser) {
        super(application, currUser);
    }

    public List<Episode> getAllEpisodesByPatient(Patient patient) throws SQLException{
        return getDataBaseHelper().getEpisodeDao().getAllByPatient(patient);
    }

    public Episode getLatestByPatientAndSanitryUuid(Patient patient,String uuid) throws SQLException{
        return getDataBaseHelper().getEpisodeDao().getLatestByPatientAndSanitryUuid(patient,uuid);
    }


    public void createEpisode(Episode episode) throws SQLException {

        episode.setSyncStatus("R");
        episode.setUuid(Utilities.getNewUUID().toString());
        getDataBaseHelper().getEpisodeDao().create(episode);
    }
    public void udpateEpisode(Episode episode) throws SQLException {
        getDataBaseHelper().getEpisodeDao().update(episode);
    }
    public void deleteEpisode(Episode episode) throws SQLException {
        getDataBaseHelper().getEpisodeDao().delete(episode);
    }

    public void saveEpisodeFromRest(LinkedTreeMap<String, Object> patient, Patient localPatient) {

        try {
//            Patient localPatient = patientService.getPatient(Objects.requireNonNull(patient.get("uuidopenmrs")).toString());

            Episode episode = new Episode();
            episode.setEpisodeDate(getSqlDateFromString(Objects.requireNonNull(patient.get("prescriptiondate")).toString(), "yyyy-MM-dd'T'HH:mm:ss"));
            episode.setPatient(localPatient);
            episode.setStartReason("Referido De");
            episode.setNotes("Referido De");
            episode.setSyncStatus("U");
            episode.setUuid(UUID.randomUUID().toString());
            createEpisode(episode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
