package mz.org.fgh.idartlite.service.dispense;

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
import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.StockReportData;
import mz.org.fgh.idartlite.util.DateUtilities;

import static mz.org.fgh.idartlite.model.Dispense.COLUMN_SYNC_STATUS;


public interface IDispenseService extends IBaseService {

    public List<Dispense> getAllDispenseByPrescription(Prescription prescription) throws SQLException;

    public void createDispense(Dispense dispense) throws SQLException;

    public void saveDispensedDrugs(List<DispensedDrug> dispensedDrugs, Dispense dispense) throws SQLException ;

    public void udpateDispense(Dispense dispense) throws SQLException;

    public void deleteDispense(Dispense dispense) throws SQLException;

    public List<Dispense> getAllOfPatient(Patient patient) throws SQLException;

    public Dispense getLastDispenseFromPrescription(Prescription prescription) throws SQLException;

    public long countAllOfPrescription(Prescription prescription) throws SQLException ;

    public void saveOrUpdateDispense(Dispense dispense) throws SQLException;

    public void saveOrUpdateDispensedDrugs(List<DispensedDrug> dispensedDrugs, Dispense dispense) throws SQLException;

    public List<Dispense> getAllDispenseByStatus(String status) throws SQLException;

    public List<Dispense> getDispensesBetweenStartDateAndEndDateWithLimit(Date startDate, Date endDate, long offset, long limit) throws SQLException;

    public List<Dispense> getDispensesBetweenStartDateAndEndDate(Date startDate, Date endDate) throws SQLException;

    public List<StockReportData> getStockAlertReportLastThreeMonthsPeriod() throws SQLException;
}
