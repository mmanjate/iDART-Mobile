package mz.org.fgh.idartlite.service;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.PrescribedDrug;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.User;

public class PrescriptionService extends BaseService {

    public PrescriptionService(Application application, User currUser) {
        super(application, currUser);
    }


    public List<Prescription> getAllPrescriptionsByPatient(Patient patient) throws SQLException{
        return getDataBaseHelper().getPrescriptionDao().getAllByPatient(patient);
    }


    public void createPrescription(Prescription prescription) throws SQLException {
        getDataBaseHelper().getPrescriptionDao().create(prescription);
        if(prescription.getPrescribedDrugs() != null)
        savePriscribedDrus(prescription.getPrescribedDrugs());
    }



    public void savePriscribedDrus(List<PrescribedDrug> prescribedDrugs) throws SQLException {
        for (PrescribedDrug prescribedDrug: prescribedDrugs) {
            getDataBaseHelper().getPrescribedDrugDao().create(prescribedDrug);
        }
    }

    public List<PrescribedDrug> getAllOfPrescription(Prescription prescription) throws SQLException {
        return getDataBaseHelper().getPrescribedDrugDao().queryForEq(PrescribedDrug.COLUMN_PRESCRIPTION, prescription.getId());
    }


    public void updatePrescription(Prescription prescription) throws SQLException {
        getDataBaseHelper().getPrescriptionDao().update(prescription);
        getDataBaseHelper().getPrescribedDrugDao().deleteBuilder().where().eq(PrescribedDrug.COLUMN_PRESCRIPTION, prescription.getId());
        getDataBaseHelper().getPrescribedDrugDao().create(prescription.getPrescribedDrugs());
    }


    public void deletePrescription(Prescription prescription) throws SQLException {
        getDataBaseHelper().getPrescriptionDao().delete(prescription);
    }

    public Prescription getLastPatientPrescription(Patient patient) throws SQLException {

        return getDataBaseHelper().getPrescriptionDao().getLastPatientPrescription(patient);
    }
}
