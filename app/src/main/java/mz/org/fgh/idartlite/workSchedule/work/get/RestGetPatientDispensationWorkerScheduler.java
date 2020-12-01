package mz.org.fgh.idartlite.workSchedule.work.get;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.List;

import mz.org.fgh.idartlite.base.rest.BaseRestService;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.rest.helper.RESTServiceHandler;
import mz.org.fgh.idartlite.rest.service.Dispense.RestDispenseService;
import mz.org.fgh.idartlite.service.episode.EpisodeService;
import mz.org.fgh.idartlite.service.patient.PatientService;

public class RestGetPatientDispensationWorkerScheduler extends Worker {
    private static final String TAG = "RestGetPatientDispense";
    private List<Patient> patientList;
    protected PatientService patientService;
    protected EpisodeService episodeService;
    protected Episode episode;

    public RestGetPatientDispensationWorkerScheduler(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            patientService = new PatientService(BaseRestService.getApp(), null);
            episodeService = new EpisodeService(BaseRestService.getApp(), null);
            if (RESTServiceHandler.getServerStatus(BaseRestService.baseUrl)) {
                Log.d(TAG, "doWork: Sync Patient Data");
                patientList = patientService.getALLPatient();
                if (patientList != null)
                    if (patientList.size() > 0) {
                        for (Patient patient : patientList) {
                            episode = episodeService.findEpisodeWithStopReasonByPatient(patient);
                            if (episode == null)
                                RestDispenseService.restGetLastDispense(patient);
                        }
                    } else
                        Log.d(TAG, "doWork: Sem Dispensas para syncronizar");
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
