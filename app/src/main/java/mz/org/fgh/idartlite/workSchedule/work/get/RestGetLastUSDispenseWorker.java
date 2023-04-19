package mz.org.fgh.idartlite.workSchedule.work.get;

import static mz.org.fgh.idartlite.base.application.IdartLiteApplication.CHANNEL_2_ID;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import mz.org.fgh.idartlite.base.work.BaseWorker;
import mz.org.fgh.idartlite.model.patient.Patient;
import mz.org.fgh.idartlite.rest.service.Dispense.RestDispenseService;

public class RestGetLastUSDispenseWorker extends BaseWorker<Patient> {

    public RestGetLastUSDispenseWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    protected void doOnStart() {
        try {
            issueNotification(CHANNEL_2_ID, "Sincronização de Dispensas da Unidade Sanitária Iniciada", true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doOnFinish() {
        try {
            issueNotification(CHANNEL_2_ID, "Sincronização de Dispensas da Unidade Sanitária Terminada", false);
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
            RestDispenseService.restGetAllLastUSDispense(this, offset, limit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
