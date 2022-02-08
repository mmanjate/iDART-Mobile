package mz.org.fgh.idartlite.workSchedule.work.patch;

import static mz.org.fgh.idartlite.base.application.IdartLiteApplication.CHANNEL_1_ID;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.rest.BaseRestService;
import mz.org.fgh.idartlite.base.work.BaseWorker;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.patient.Patient;
import mz.org.fgh.idartlite.model.patient.PatientAttribute;
import mz.org.fgh.idartlite.rest.helper.RESTServiceHandler;
import mz.org.fgh.idartlite.rest.service.Patient.RestPatientService;
import mz.org.fgh.idartlite.service.episode.EpisodeService;
import mz.org.fgh.idartlite.util.Utilities;

public class RestPacthPatientWorker extends BaseWorker<Patient> {
    private static final String TAG = "RestPacthPatientWorker";
    private List<Episode> episodeList;
    private EpisodeService episodeService;

    public RestPacthPatientWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        episodeService = new EpisodeService(BaseRestService.getApp(), null);
    }

    @Override
    protected void doOnStart() {
        try {
            episodeList = episodeService.getAllEpisodeByStatusAndDispenseStatus(BaseModel.SYNC_SATUS_READY, PatientAttribute.PATIENT_DISPENSATION_STATUS_FALTOSO);
            String msg = Utilities.listHasElements(episodeList) ? "Envio de Pacientes iniciado." : "Não possui pacientes para enviar.";
            try {
                issueNotification(CHANNEL_1_ID, msg, Utilities.listHasElements(episodeList));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void doOnlineSearch(long offset, long limit) throws SQLException {
        try {
            if (RESTServiceHandler.getServerStatus(BaseRestService.baseUrl)) {
                Log.d(TAG, "doWork: Sync Patient Data");
                if (Utilities.listHasElements(episodeList)) {
                    for (Episode episode : episodeList) {
                        RestPatientService.restPostPatientFaltosoOrAbandono(episode.getPatient());
                    }
                } else
                    Log.d(TAG, "doWork: Sem Novos Pacientes para sincronizar");
            } else {
                Log.e(TAG, "Response Servidor Offline");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doOnFinish() {
        try {
            if (Utilities.listHasElements(episodeList)) {
                issueNotification(CHANNEL_1_ID, "Envio de Pacientes Terminado, " + episodeList.size() + " Pacientes enviados.", false);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doSave(List<Patient> recs) {

    }
}
