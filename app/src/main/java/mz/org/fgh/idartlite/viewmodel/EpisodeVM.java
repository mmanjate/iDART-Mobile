package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.service.EpisodeService;

public class EpisodeVM extends BaseViewModel {

    private EpisodeService episodeService;

    public EpisodeVM(@NonNull Application application) {
        super(application);

        episodeService = new EpisodeService(application, getCurrentUser());
    }

    public List<Episode> gatAllOfPatient(Patient selectedPatient) throws SQLException {
        return episodeService.getAllEpisodesByPatient(selectedPatient);
    }
}
