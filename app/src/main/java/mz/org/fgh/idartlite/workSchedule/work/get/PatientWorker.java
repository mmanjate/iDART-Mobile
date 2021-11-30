package mz.org.fgh.idartlite.workSchedule.work.get;

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
    public void doOnlineSearch(long offset, long limit) throws SQLException {
        //RestPatientService.restGetAllPatient(offset, limit, this);
    }

    @Override
    protected void doOnFinish() {

    }

    @Override
    protected void doSave(List<Patient> recs) {

    }
}
