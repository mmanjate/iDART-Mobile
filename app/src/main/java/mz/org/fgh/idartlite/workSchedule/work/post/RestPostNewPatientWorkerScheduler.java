package mz.org.fgh.idartlite.workSchedule.work.post;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.List;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.rest.BaseRestService;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.rest.helper.RESTServiceHandler;
import mz.org.fgh.idartlite.rest.service.Patient.RestPatientService;
import mz.org.fgh.idartlite.service.episode.EpisodeService;

public class RestPostNewPatientWorkerScheduler extends Worker {
    private static final String TAG = "RestPostNewPatientWorke";
    private List<Episode> episodeList;
    private EpisodeService episodeService;

    public RestPostNewPatientWorkerScheduler(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            episodeService = new EpisodeService(BaseRestService.getApp(), null);
            if (RESTServiceHandler.getServerStatus(BaseRestService.baseUrl)) {
                Log.d(TAG, "doWork: Sync New Patient Data");
                episodeList = episodeService.getAllEpisodeByStatus(BaseModel.SYNC_SATUS_READY);
                if (episodeList != null)
                    if (episodeList.size() > 0) {
                        for (Episode episode : episodeList) {
                            RestPatientService.restPostPatientSector(episode.getPatient());
                        }
                    } else
                        Log.d(TAG, "doWork: Sem Novos Pacientes para sincronizar");
            } else {
                Log.e(TAG, "Response Servidor Offline");
                return Result.failure();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success();
    }
}
