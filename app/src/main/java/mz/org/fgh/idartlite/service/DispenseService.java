package mz.org.fgh.idartlite.service;

import android.app.Application;

import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.TherapeuticLine;
import mz.org.fgh.idartlite.model.User;

public class DispenseService extends BaseService {

    private StockService stockService;

    public DispenseService(Application application, User currUser) {
        super(application, currUser);

        this.stockService = new StockService(application, currUser);
    }

    public List<Dispense> getAllDispenseByPrescription(Prescription prescription) throws SQLException{
        return getDataBaseHelper().getDispenseDao().getAllByPrescription(prescription);
    }

    public List<Dispense> getCountAllOfPrescription(Prescription prescription) throws SQLException{
        return getDataBaseHelper().getDispenseDao().getAllByPrescription(prescription);
    }


    public void createDispense(Dispense dispense) throws SQLException {
        getDataBaseHelper().getGenericDao(dispense).saveGenericObjectByClass(dispense);
        if (dispense.getDispensedDrugs() != null) {
            this.saveDispensedDrugs(dispense.getDispensedDrugs(), dispense);
        }
    }

    public void saveDispensedDrugs(List<DispensedDrug> dispensedDrugs, Dispense dispense) throws SQLException {
        for (DispensedDrug dispensedDrug: dispensedDrugs) {
            dispensedDrug.setDispense(dispense);
            getDataBaseHelper().getDispensedDrugDao().create(dispensedDrug);

            this.stockService.updateStock(dispensedDrug);
        }
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

    public long countAllOfPrescription(Prescription prescription) throws SQLException {
        return getDataBaseHelper().getDispenseDao().countAllOfPrescription(prescription);
    }

}
