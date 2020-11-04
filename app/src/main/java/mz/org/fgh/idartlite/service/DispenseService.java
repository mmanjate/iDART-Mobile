package mz.org.fgh.idartlite.service;

import android.app.Application;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.StockReportData;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.util.DateUtilitis;

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

    public DispenseService(Application application) {
        super(application);
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

    public List<Dispense> getDispensesBetweenStartDateAndEndDateWithLimit(Date startDate, Date endDate, long offset, long limit) throws SQLException{
        return getDataBaseHelper().getDispenseDao().getDispensesBetweenStartDateAndEndDateWithLimit(startDate,endDate,offset,limit);
    }

    public List<Dispense> getDispensesBetweenStartDateAndEndDate(Date startDate, Date endDate) throws SQLException{
        return getDataBaseHelper().getDispenseDao().getDispensesBetweenStartDateAndEndDate(startDate,endDate);
    }


    public List<StockReportData> getStockAlertReportLastThreeMonthsPeriod() throws SQLException{

        List<Dispense> dispenses= getDispensesBetweenStartDateAndEndDate(DateUtilitis.getDateOfPreviousDays(DateUtilitis.getCurrentDate(),90),DateUtilitis.getCurrentDate());

        List<DispensedDrug> dispensedDrugs = getDataBaseHelper().getDispensedDrugDao().getDispensedDrugsByDispenses(dispenses);

        Map<Drug,List<DispensedDrug>> drugsMap=new HashMap<>();
        Set<Stock> stocksList=new HashSet<>();
        List<StockReportData> stockReportData= new ArrayList<>();

        for (int i=0;i<dispensedDrugs.size();i++){
            Drug value = null;

            value = dispensedDrugs.get(i).getStock().getDrug();
            stocksList.add(dispensedDrugs.get(i).getStock());

            Collection<DispensedDrug> al = drugsMap.get(value);

            if (al == null)
            {
                drugsMap.put(value, (List<DispensedDrug>) (al = new ArrayList<DispensedDrug>()));
            }

            al.add(dispensedDrugs.get(i));
        }

        for (Drug drug: drugsMap.keySet()) {
            StockReportData stockReport= new StockReportData();

            stockReport.setDrugDescription(drug.getDescription());
            List<DispensedDrug> dispenseDrugs= drugsMap.get(drug);
            int quantityDispensed=0;
            int totalUnits=0;
            for (DispensedDrug dispenseD:
                    dispenseDrugs) {


                quantityDispensed+=dispenseD.getQuantitySupplied();

                for (Stock stock : stocksList){

                    if(!stock.equals(dispenseD.getStock()) && drug.equals(stock.getDrug())){
                        totalUnits +=  dispenseD.getStock().getUnitsReceived()+stock.getUnitsReceived();
                    }

                }


            }
            if(totalUnits==0){
                totalUnits=dispenseDrugs.get(0).getStock().getUnitsReceived();
            }
            totalUnits-=quantityDispensed;
            int maxConsumption= quantityDispensed/drug.getPackSize();


            int stockActual=totalUnits;

            int validStock=maxConsumption/3;


            stockReport.setMaximumConsumption(Integer.toString(maxConsumption));
            stockReport.setActualStock(Integer.toString(stockActual));
            stockReport.setValidStock(Integer.toString(validStock));

            if(validStock >= stockActual) {
                stockReport.setStockDescription("Ruptura de Stock");
            }
            else if(maxConsumption >= stockActual) {
                stockReport.setStockDescription("Stock Baixo");
            }
            else if(validStock < stockActual) {
                stockReport.setStockDescription(" Acima do Consumo Máximo");
            }
            stockReportData.add(stockReport);
        }

        return stockReportData;


    }

}
