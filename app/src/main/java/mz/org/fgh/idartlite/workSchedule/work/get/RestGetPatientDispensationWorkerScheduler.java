package mz.org.fgh.idartlite.workSchedule.work.get;

import static mz.org.fgh.idartlite.base.application.IdartLiteApplication.CHANNEL_1_ID;
import static mz.org.fgh.idartlite.base.application.IdartLiteApplication.CHANNEL_2_ID;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.rest.BaseRestService;
import mz.org.fgh.idartlite.base.work.BaseWorker;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.patient.Patient;
import mz.org.fgh.idartlite.rest.helper.RESTServiceHandler;
import mz.org.fgh.idartlite.rest.service.Dispense.RestDispenseService;
import mz.org.fgh.idartlite.rest.service.Patient.RestPatientService;
import mz.org.fgh.idartlite.service.episode.EpisodeService;
import mz.org.fgh.idartlite.service.patient.PatientService;
import mz.org.fgh.idartlite.util.Utilities;

public class RestGetPatientDispensationWorkerScheduler extends BaseWorker<Patient> {
    private static final String TAG = "RestGetPatientDispense";

    public RestGetPatientDispensationWorkerScheduler(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    protected void doOnStart() {
        try {
            issueNotification(CHANNEL_2_ID, "Sincronização de Dispensas Iniciada", true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doOnFinish() {
        try {
            issueNotification(CHANNEL_2_ID, "Sincronização de Dispensas Terminada", false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doSave(List<Patient> recs) {
    }

    @Override
    public void doOnlineSearch(long offset, long limit) throws SQLException {
        try {
            RestDispenseService.restGetAllLastDispense(this, offset, limit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
