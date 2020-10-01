package mz.org.fgh.idartlite.service;

import android.app.Application;

import com.j256.ormlite.stmt.QueryBuilder;
import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.*;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.TherapeuticLine;
import mz.org.fgh.idartlite.model.User;

import static mz.org.fgh.idartlite.model.Clinic.COLUMN_UUID;
import static mz.org.fgh.idartlite.model.Dispense.COLUMN_SYNC_STATUS;

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

        getDataBaseHelper().getDispenseDao().create(dispense);

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
        getDataBaseHelper().getDispenseDao().update(dispense);
    }

    public void deleteDispense(Dispense dispense) throws SQLException {
        getDataBaseHelper().getDispenseDao().delete(dispense);
    }


    public List<Dispense> getAllOfPatient(Patient patient) throws SQLException {

       return getDataBaseHelper().getDispenseDao().getAllOfPatient(getApplication(),patient);
    }

    public long countAllOfPrescription(Prescription prescription) throws SQLException {
        return getDataBaseHelper().getDispenseDao().countAllOfPrescription(prescription);
    }

    public void saveOrUpdateDispense(Dispense dispense) throws SQLException {
        getDataBaseHelper().getDispenseDao().createOrUpdate(dispense);

        if (dispense.getDispensedDrugs() != null) {
            this.saveOrUpdateDispensedDrugs(dispense.getDispensedDrugs(), dispense);
        }
    }

    public void saveOrUpdateDispensedDrugs(List<DispensedDrug> dispensedDrugs, Dispense dispense) throws SQLException {
        for (DispensedDrug dispensedDrug: dispensedDrugs) {
            dispensedDrug.setDispense(dispense);
            getDataBaseHelper().getDispensedDrugDao().createOrUpdate(dispensedDrug);

            this.stockService.updateStock(dispensedDrug);
        }
    }

    public List<Dispense> getAllDispenseByStatus(String status) throws SQLException {

        List<Dispense> typeList = getDataBaseHelper().getDispenseDao().queryForEq(COLUMN_SYNC_STATUS, status);

        if (typeList != null)
            if (!typeList.isEmpty())
                return typeList;

        return null;
    }

}
