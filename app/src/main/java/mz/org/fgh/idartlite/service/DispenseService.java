package mz.org.fgh.idartlite.service;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.User;

import static mz.org.fgh.idartlite.model.Dispense.COLUMN_SYNC_STATUS;

public class DispenseService extends BaseService {

    private StockService stockService;

    private PrescriptionService prescriptionService;

    private DispenseDrugService dispenseDrugService;

    public DispenseService(Application application, User currUser) {
        super(application, currUser);

        this.stockService = new StockService(application, currUser);
        this.prescriptionService = new PrescriptionService(application, currUser);
        this.dispenseDrugService = new DispenseDrugService(application, currUser);

    }

    public List<Dispense> getAllDispenseByPrescription(Prescription prescription) throws SQLException {
        return getDataBaseHelper().getDispenseDao().getAllByPrescription(prescription);
    }

    public void createDispense(Dispense dispense) throws SQLException {

        getDataBaseHelper().getDispenseDao().create(dispense);

        if (dispense.getDispensedDrugs() != null) {
            this.saveDispensedDrugs(dispense.getDispensedDrugs(), dispense);
        }
    }

    public void saveDispensedDrugs(List<DispensedDrug> dispensedDrugs, Dispense dispense) throws SQLException {
        for (DispensedDrug dispensedDrug : dispensedDrugs) {
            dispensedDrug.setDispense(dispense);
            getDataBaseHelper().getDispensedDrugDao().create(dispensedDrug);

            this.updateStock(dispensedDrug);
        }
    }

    public void udpateDispense(Dispense dispense) throws SQLException {
        getDataBaseHelper().getDispenseDao().update(dispense);
    }

    public void deleteDispense(Dispense dispense) throws SQLException {
        getDataBaseHelper().getDispenseDao().delete(dispense);
        this.putBackStockMovimentForDispensedDrug(dispense);

        Prescription prescription = dispense.getPrescription();
        if (prescription.getExpiryDate() != null) {
            prescription.setExpiryDate(null);
            this.prescriptionService.updatePrescriptionEntity(prescription);
        }
    }


    public List<Dispense> getAllOfPatient(Patient patient) throws SQLException {

        return getDataBaseHelper().getDispenseDao().getAllOfPatient(getApplication(), patient);
    }

    public Dispense getLastDispenseFromPrescription(Prescription prescription) throws SQLException {

        return getDataBaseHelper().getDispenseDao().getLastDispensePrescription(prescription);
    }

    public long countAllOfPrescription(Prescription prescription) throws SQLException {
        return getDataBaseHelper().getDispenseDao().countAllOfPrescription(prescription);
    }

    public void saveOrUpdateDispense(Dispense dispense) throws SQLException {
        getDataBaseHelper().getDispenseDao().createOrUpdate(dispense);
        List<DispensedDrug> dispensedDrugs = this.dispenseDrugService.findDispensedDrugByDispenseId(dispense.getId());

        if (dispensedDrugs.size() == 0) {
            this.saveOrUpdateDispensedDrugs(dispense.getDispensedDrugs(), dispense);
        } else {
            this.saveOrUpdateDispensedDrugs(dispensedDrugs, dispense);
        }

        if (dispense.getPrescription().getExpiryDate() != null) {
            Prescription prescription = dispense.getPrescription();
            prescription.setExpiryDate(dispense.getPickupDate());
            prescription.setSyncStatus(BaseModel.SYNC_SATUS_SENT);
            this.prescriptionService.updatePrescriptionEntity(prescription);
        }
    }

    public void saveOrUpdateDispensedDrugs(List<DispensedDrug> dispensedDrugs, Dispense dispense) throws SQLException {
        for (DispensedDrug dispensedDrug : dispensedDrugs) {
            dispensedDrug.setDispense(dispense);
            getDataBaseHelper().getDispensedDrugDao().createOrUpdate(dispensedDrug);

            this.updateStock(dispensedDrug);
        }
    }

    public List<Dispense> getAllDispenseByStatus(String status) throws SQLException {

        List<Dispense> typeList = getDataBaseHelper().getDispenseDao().queryForEq(COLUMN_SYNC_STATUS, status);

        if (typeList != null)
            if (!typeList.isEmpty())
                return typeList;

        return null;
    }

    private void updateStock(DispensedDrug dispensedDrug) throws SQLException {

        Stock stock = dispensedDrug.getStock();
        int actualStockMoviment = stock.getStockMoviment();
        int qtySupplied = dispensedDrug.getQuantitySupplied();
        int finalStockMoviment = actualStockMoviment - qtySupplied;

        stock.setStockMoviment(finalStockMoviment);

        if (stock.isSyncStatusReady(BaseModel.SYNC_SATUS_READY))
            stock.setSyncStatus(BaseModel.SYNC_SATUS_READY);
        else if (stock.isSyncStatusReady(BaseModel.SYNC_SATUS_SENT))
            stock.setSyncStatus(BaseModel.SYNC_SATUS_UPDATED);

        this.stockService.updateStock(stock);
    }

    private void putBackStockMovimentForDispensedDrug(Dispense dispense) throws SQLException {

        List<DispensedDrug> dispensedDrugs = this.dispenseDrugService.findDispensedDrugByDispenseId(dispense.getId());

        for (DispensedDrug dispensedDrug : dispensedDrugs
        ) {
            Stock stock = dispensedDrug.getStock();
            int currentStockMoviment = stock.getStockMoviment();
            int qtySupplied = dispensedDrug.getQuantitySupplied();
            int finalStockMoviment = currentStockMoviment + qtySupplied;

            stock.setStockMoviment(finalStockMoviment);
            if (stock.isSyncStatusReady(BaseModel.SYNC_SATUS_READY))
                stock.setSyncStatus(BaseModel.SYNC_SATUS_READY);
            else if (stock.isSyncStatusReady(BaseModel.SYNC_SATUS_SENT))
                stock.setSyncStatus(BaseModel.SYNC_SATUS_UPDATED);

            this.stockService.updateStock(stock);
        }
    }

}
