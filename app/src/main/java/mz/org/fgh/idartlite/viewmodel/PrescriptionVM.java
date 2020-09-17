package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.service.PrescriptionService;

public class PrescriptionVM extends BaseViewModel {

    private PrescriptionService prescriptionService;

    public PrescriptionVM(@NonNull Application application) {
        super(application);

        prescriptionService = new PrescriptionService(application, getCurrentUser());
    }

    public List<Prescription> gatAllOfPatient(Patient patient) throws SQLException {
        return prescriptionService.getAllOfPatient(patient);
    }

}
