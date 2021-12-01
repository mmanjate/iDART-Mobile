package mz.org.fgh.idartlite.workSchedule.work.get;

import static mz.org.fgh.idartlite.base.application.IdartLiteApplication.CHANNEL_1_ID;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.work.BaseWorker;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.rest.service.Patient.RestPatientService;

public class PatientWorker extends BaseWorker<Patient> {


    public PatientWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    protected void doOnStart() {
        try {
            issueNotification(CHANNEL_1_ID, "Sincronização de Pacientes Iniciada", true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doOnlineSearch(long offset, long limit) throws SQLException {
        RestPatientService.getAllPatient(this, offset, limit);
    }

    @Override
    protected void doOnFinish() {
        try {
            String msg = "Sincronização de Pacientes Terminada, Não Foram Encontrados Novos Pacientes";
            if (hasNewRescs()) {
                msg = "Sincronização de Pacientes Terminada, Tem " + getNewRecsQty() ;
                msg = msg + (getNewRecsQty() > 1 ? " novos pacientes" : " novo paciente");
            }
            issueNotification(CHANNEL_1_ID, msg, false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doSave(List<Patient> recs) {

    }
}
