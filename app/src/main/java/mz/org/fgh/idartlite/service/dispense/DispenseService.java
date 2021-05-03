package mz.org.fgh.idartlite.service.dispense;

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

import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.StockReportData;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.service.prescription.PrescriptionService;
import mz.org.fgh.idartlite.service.stock.StockService;
import mz.org.fgh.idartlite.util.DateUtilities;

import static mz.org.fgh.idartlite.model.Dispense.COLUMN_SYNC_STATUS;

public class DispenseService extends BaseService<Dispense> implements IDispenseService {

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

    @Override
    public void save(Dispense record) throws SQLException {

    }

    @Override
    public void update(Dispense record) throws SQLException {

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
        //    prescription.setSyncStatus(BaseModel.SYNC_SATUS_SENT);
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

    public List<Dispense> getAllDispensesByStatus(String status) throws SQLException {

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

    public List<Dispense> getDispensesNonSyncBetweenStartDateAndEndDateWithLimit(Date startDate, Date endDate, long offset, long limit) throws SQLException{
        return getDataBaseHelper().getDispenseDao().getDispensesNonSyncBetweenStartDateAndEndDateWithLimit(startDate,endDate,offset,limit);
    }

    public List<Dispense> getDispensesBetweenStartDateAndEndDate(Date startDate, Date endDate) throws SQLException{
        return getDataBaseHelper().getDispenseDao().getDispensesBetweenStartDateAndEndDate(startDate,endDate);
    }


    public List<StockReportData> getStockAlertReportLastThreeMonthsPeriod() throws SQLException{

        List<Dispense> dispenses= getDispensesBetweenStartDateAndEndDate(DateUtilities.getDateOfPreviousDays(DateUtilities.getCurrentDate(),90), DateUtilities.getCurrentDate());

        List<DispensedDrug> dispensedDrugs = getDataBaseHelper().getDispensedDrugDao().getDispensedDrugsByDispenses(dispenses);

        Map<Drug,List<DispensedDrug>> drugsMap=new HashMap<>();
        Set<Stock> stocksList=new HashSet<>();
        List<StockReportData> stockReportData= new ArrayList<>();

        for (int i=0;i<dispensedDrugs.size();i++){
            Drug value = null;

            value = dispensedDrugs.get(i).getStock().getDrug();
            List<Stock> stocks= stockService.getAllStocksByDrug(dispensedDrugs.get(i).getStock().getDrug());

            stocksList.add(dispensedDrugs.get(i).getStock());
            stocksList.addAll(stocks);
            Collection<DispensedDrug> al = drugsMap.get(value);

            if (al == null)
            {
                drugsMap.put(value, (List<DispensedDrug>) (al = new ArrayList<DispensedDrug>()));
            }


                al.add(dispensedDrugs.get(i));

        }

        Set<Drug> drugs=drugsMap.keySet();

        for (Drug drug: drugsMap.keySet()) {
            StockReportData stockReport= new StockReportData();

            stockReport.setDrugDescription(drug.getDescription());
            List<DispensedDrug> dispenseDrugs= drugsMap.get(drug);
            int quantityDispensed=0; // same as maxConsumption
            int stockActual=0;
            for (DispensedDrug dispenseD:
                    dispenseDrugs) {


                quantityDispensed+=dispenseD.getQuantitySupplied();



            }
            for (Stock stock : stocksList){

                if( drug.equals(stock.getDrug())){

                    stockActual +=  stock.getStockMoviment();

                }


            }


            if(stockActual==0){
                stockActual=dispenseDrugs.get(0).getStock().getStockMoviment();
            }
        //    stockActual-=quantityDispensed;


            int validStock=quantityDispensed/3;


            stockReport.setMaximumConsumption(Integer.toString(quantityDispensed));
            stockReport.setActualStock(Integer.toString(stockActual));
            stockReport.setValidStock(Integer.toString(validStock));

            if(validStock >= stockActual) {
                stockReport.setStockDescription("Ruptura de Stock");
            }
            else if(quantityDispensed >= stockActual) {
                stockReport.setStockDescription("Stock Baixo");
            }
            else if(validStock < stockActual) {
                stockReport.setStockDescription(" Acima do Consumo Máximo");
            }
            stockReportData.add(stockReport);
        }

        return stockReportData;



    }

    @Override
    public List<Dispense> getDispensesBetweenNextPickupDateStartDateAndEndDateWithLimit(Date startDate, Date endDate, long offset, long limit) throws SQLException {
        return getDataBaseHelper().getDispenseDao().getDispensesBetweenNextPickppDateStartDateAndEndDateWithLimit(getApplication(),startDate,endDate,offset,limit);
    }

    @Override
    public List<Dispense> getAbsentPatientsBetweenNextPickppDateStartDateAndEndDateWithLimit(Date startDate, Date endDate, long offset, long limit) throws SQLException {

        return getDataBaseHelper().getDispenseDao().getAbsentPatientsBetweenNextPickppDateStartDateAndEndDateWithLimit(getApplication(),startDate,endDate,offset,limit);
    }

}
