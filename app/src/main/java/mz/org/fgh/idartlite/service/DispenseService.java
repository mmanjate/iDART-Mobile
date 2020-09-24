package mz.org.fgh.idartlite.service;

import android.app.Application;
import com.j256.ormlite.stmt.QueryBuilder;
import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.dao.DispenseDao;
import mz.org.fgh.idartlite.model.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DispenseService extends BaseService {

    public DispenseService(Application application, User currUser) {
        super(application, currUser);
    }

    public List<Dispense> getAllDispenseByPrescription(Prescription prescription) throws SQLException{
        return getDataBaseHelper().getDispenseDao().getAllByPrescription(prescription);
    }

    public void createDispense(Dispense dispense) throws SQLException {
        getDataBaseHelper().getGenericDao(dispense).saveGenericObjectByClass(dispense);
    }

    public void udpateDispense(Dispense dispense) throws SQLException {
        getDataBaseHelper().getGenericDao(dispense).updateGenericObjectByClass(dispense);
    }

    public void deleteDispense(Dispense dispense) throws SQLException {
        getDataBaseHelper().getGenericDao(dispense).deleteGenericObjectByClass(dispense);
    }

    public List<Dispense> getAllOfPrescription(Prescription prescription) throws SQLException {
        return getDataBaseHelper().getDispenseDao().queryForEq(Dispense.COLUMN_PRESCRIPTION, prescription.getId());
    }

    public List<Dispense> getAllOfPatient(Patient patient) throws SQLException {

        QueryBuilder<Prescription, Integer> prescriptionQb = getDataBaseHelper().getPrescriptionDao().queryBuilder();
        prescriptionQb.where().eq(Prescription.COLUMN_PATIENT_ID, patient.getId());

        QueryBuilder<TherapeuticLine, Integer> therapeuticLineQb = getDataBaseHelper().getTherapeuticLineDao().queryBuilder();
        prescriptionQb.join(therapeuticLineQb);

        QueryBuilder<Dispense, Integer> dispenseQb =  getDataBaseHelper().getDispenseDao().queryBuilder();
        dispenseQb.join(prescriptionQb);

        List<Dispense> dispenses = dispenseQb.orderBy(Dispense.COLUMN_NEXT_PICKUP_DATE,false).query();

        return dispenses;
    }

}
