package mz.org.fgh.idartlite.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.BaseViewModel;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.service.PatientService;

public class PatientVM extends BaseViewModel {

    private Patient patient;
    private PatientService patientService;

    public PatientVM(@NonNull Application application) {
        super(application);
        patientService = new PatientService(application,getCurrentUser());
    }

    public List<Patient> searchPatient(String param) throws SQLException {
        return patientService.search(param);
    }

    public List<Patient> getAllPatient() throws SQLException {
        return patientService.getALLPatient();
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
