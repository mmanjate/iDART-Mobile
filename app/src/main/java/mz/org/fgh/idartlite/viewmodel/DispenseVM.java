package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.service.DispenseService;

public class DispenseVM extends BaseViewModel {

    private DispenseService dispenseService;

    public DispenseVM(@NonNull Application application) {
        super(application);

        dispenseService = new DispenseService(application, getCurrentUser());
    }

    public List<Dispense> gatAllOfPrescription(Prescription prescription) throws SQLException {
        return dispenseService.getAllOfPrescription(prescription);
    }

    public List<Dispense> gatAllOfPatient(Patient patient) throws SQLException {
        return dispenseService.getAllOfPatient(patient);
    }
}
