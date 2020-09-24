package mz.org.fgh.idartlite.service;

import android.app.Application;
import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.PrescribedDrug;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.User;

import java.sql.SQLException;
import java.util.List;

public class PrescriptionService extends BaseService {

    public PrescriptionService(Application application, User currUser) {
        super(application, currUser);
    }


    public List<Prescription> getAllPrescriptionsByPatient(Patient patient) throws SQLException{
        return getDataBaseHelper().getPrescriptionDao().getAllByPatient(patient);
    }


    public void createPrescription(Prescription prescription) throws SQLException {
        getDataBaseHelper().getPrescriptionDao().create(prescription);
        savePriscribedDrus(prescription.getPrescribedDrugs());
    }

    public void savePriscribedDrus(List<PrescribedDrug> prescribedDrugs) throws SQLException {
        for (PrescribedDrug prescribedDrug: prescribedDrugs) {
            getDataBaseHelper().getPrescribedDrugDao().create(prescribedDrug);
        }
    }


    public void updatePrescription(Prescription prescription) throws SQLException {
        getDataBaseHelper().getPrescriptionDao().update(prescription);
    }


    public void deletePrescription(Prescription prescription) throws SQLException {
        getDataBaseHelper().getPrescriptionDao().delete(prescription);
    }

    public Prescription getLastPatientPrescription(Patient patient) throws SQLException {

        return getDataBaseHelper().getPrescriptionDao().getLastPatientPrescription(patient);
    }
}
