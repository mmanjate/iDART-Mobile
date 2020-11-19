package mz.org.fgh.idartlite.base.databasehelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.dao.clinic.IClinicDao;
import mz.org.fgh.idartlite.dao.clinic.IPharmacyTypeDao;
import mz.org.fgh.idartlite.dao.dispense.IDispenseDao;
import mz.org.fgh.idartlite.dao.dispense.IDispenseTypeDao;
import mz.org.fgh.idartlite.dao.dispense.IDispensedDrugDao;
import mz.org.fgh.idartlite.dao.drug.IDiseaseTypeDao;
import mz.org.fgh.idartlite.dao.drug.IDrugDao;
import mz.org.fgh.idartlite.dao.drug.IFormDao;
import mz.org.fgh.idartlite.dao.drug.IRegimenDrugDao;
import mz.org.fgh.idartlite.dao.drug.ITherapeuticLineDao;
import mz.org.fgh.idartlite.dao.drug.ITherapeuticRegimenDao;
import mz.org.fgh.idartlite.dao.episode.IEpisodeDao;
import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.dao.patient.IPatientDao;
import mz.org.fgh.idartlite.dao.prescription.IPrescribedDrugDao;
import mz.org.fgh.idartlite.dao.prescription.IPrescriptionDao;
import mz.org.fgh.idartlite.dao.stock.IDestroyedDrugDao;
import mz.org.fgh.idartlite.dao.stock.IIventoryDao;
import mz.org.fgh.idartlite.dao.stock.IStockAjustmentDao;
import mz.org.fgh.idartlite.dao.stock.IStockDao;
import mz.org.fgh.idartlite.dao.user.IUserDao;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.DestroyedDrug;
import mz.org.fgh.idartlite.model.DiseaseType;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispenseType;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Form;
import mz.org.fgh.idartlite.model.Iventory;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.PharmacyType;
import mz.org.fgh.idartlite.model.PrescribedDrug;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.RegimenDrug;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.StockAjustment;
import mz.org.fgh.idartlite.model.TherapeuticLine;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.model.User;

public class IdartLiteDataBaseHelper extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME    = "idartlite.db";
    private static final int    DATABASE_VERSION = 3;


    private IUserDao userDao;
    private IGenericDao genericDao;

    private IClinicDao IClinicDao;
    private IDiseaseTypeDao IDiseaseTypeDao;
    private IDispenseDao dispenseDao;
    private IDispensedDrugDao dispensedDrugDao;
    private IDispenseTypeDao dispenseTypeDao;
    private IDrugDao drugDao;

    private IEpisodeDao episodeDao;
    private IFormDao formDao;
    private IPatientDao patientDao;
    private IPharmacyTypeDao pharmacyTypeDao;
    private IPrescribedDrugDao prescribedDrugDao;
    private IPrescriptionDao prescriptionDao;
    private IRegimenDrugDao regimenDrugDao;
    private IStockDao stockDao;
    private ITherapeuticLineDao therapeuticLineDao;
    private ITherapeuticRegimenDao therapeuticRegimenDao;
    private IDestroyedDrugDao destroyedDrugDao;
    private IIventoryDao iventoryDao;
    private IStockAjustmentDao stockAjustmentDao;

    public IClinicDao getIClinicDao() throws SQLException {
        if(IClinicDao == null){
            IClinicDao = getDao(Clinic.class);
        }
        return IClinicDao;
    }

    public IDiseaseTypeDao getIDiseaseTypeDao() throws SQLException {
        if(IDiseaseTypeDao == null){
            IDiseaseTypeDao = getDao(DiseaseType.class);
        }
        return IDiseaseTypeDao;
    }

    public IDispensedDrugDao getDispensedDrugDao() throws SQLException {
        if(dispensedDrugDao == null){
            dispensedDrugDao = getDao(DispensedDrug.class);
        }
        return dispensedDrugDao;
    }

    public IDispenseTypeDao getDispenseTypeDao() throws SQLException {
        if(dispenseTypeDao == null){
            dispenseTypeDao = getDao(DispenseType.class);
        }
        return dispenseTypeDao;
    }

    public IDrugDao getDrugDao() throws SQLException {
        if(drugDao == null){
            drugDao = getDao(Drug.class);
        }
        return drugDao;
    }

    public IFormDao getFormDao() throws SQLException {
        if(formDao == null){
            formDao = getDao(Form.class);
        }
        return formDao;
    }

    public IPatientDao getPatientDao() throws SQLException {
        if(patientDao == null){
            patientDao = getDao(Patient.class);
        }
        return patientDao;
    }

    public IPharmacyTypeDao getPharmacyTypeDao() throws SQLException {
        if(pharmacyTypeDao == null){
            pharmacyTypeDao = getDao(PharmacyType.class);
        }
        return pharmacyTypeDao;
    }

    public IPrescribedDrugDao getPrescribedDrugDao() throws SQLException {
        if(prescribedDrugDao == null){
            prescribedDrugDao = getDao(PrescribedDrug.class);
        }
        return prescribedDrugDao;
    }

    public IRegimenDrugDao getRegimenDrugDao() throws SQLException {
        if(regimenDrugDao == null){
            regimenDrugDao = getDao(RegimenDrug.class);
        }
        return regimenDrugDao;
    }

    public ITherapeuticLineDao getTherapeuticLineDao() throws SQLException {
        if(therapeuticLineDao == null){
            therapeuticLineDao = getDao(TherapeuticLine.class);
        }
        return therapeuticLineDao;
    }

    public ITherapeuticRegimenDao getTherapeuticRegimenDao() throws SQLException {
        if(therapeuticRegimenDao == null){
            therapeuticRegimenDao = getDao(TherapeuticRegimen.class);
        }
        return therapeuticRegimenDao;
    }

    public <T extends BaseModel> IGenericDao getGenericDao(Class<T> clazz) throws SQLException {
        if(genericDao == null){
            genericDao = getDao(clazz);
        }
        return genericDao;
    }

    public IUserDao getUserDao() throws SQLException {
        if(userDao == null){
            userDao = getDao(User.class);
        }
        return userDao;
    }

    public IEpisodeDao getEpisodeDao() throws SQLException {
        if(episodeDao == null){
            episodeDao = getDao(Episode.class);
        }
        return episodeDao;
    }

    public IPrescriptionDao getPrescriptionDao() throws SQLException {
        if(prescriptionDao == null){
            prescriptionDao = getDao(Prescription.class);
        }
        return prescriptionDao;
    }

    public IDispenseDao getDispenseDao() throws SQLException {
        if(dispenseDao == null){
            dispenseDao = getDao(Dispense.class);
        }
        return dispenseDao;
    }

    public IStockDao getStockDao() throws SQLException {
        if(stockDao == null){
            stockDao = getDao(Stock.class);
        }
        return stockDao;
    }

    public IDestroyedDrugDao getDestroyedStockDrugDao() throws SQLException {
        if(destroyedDrugDao == null){
            destroyedDrugDao = getDao(DestroyedDrug.class);
        }
        return destroyedDrugDao;
    }

    public IIventoryDao getIventoryDao() throws SQLException {
        if(iventoryDao == null){
            iventoryDao = getDao(Iventory.class);
        }
        return iventoryDao;
    }

    public IStockAjustmentDao getStockAjustmentDao() throws SQLException {
        if(stockAjustmentDao == null){
            stockAjustmentDao = getDao(StockAjustment.class);
        }
        return stockAjustmentDao;
    }

    private static IdartLiteDataBaseHelper dataBaseHelper;

    private IdartLiteDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    public static IdartLiteDataBaseHelper getInstance(Context context) {
        if (dataBaseHelper == null){
            dataBaseHelper = new IdartLiteDataBaseHelper(context);
        }
        return dataBaseHelper;
    }

    public IdartLiteDataBaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion, int configFileId) {
        super(context, databaseName, factory, databaseVersion, configFileId);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {

            TableUtils.createTable(connectionSource, DispenseType.class);
            TableUtils.createTable(connectionSource, DiseaseType.class);
            TableUtils.createTable(connectionSource, PharmacyType.class);
            TableUtils.createTable(connectionSource, Clinic.class);
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, TherapeuticRegimen.class);
            TableUtils.createTable(connectionSource, TherapeuticLine.class);
            TableUtils.createTable(connectionSource, Patient.class);
            TableUtils.createTable(connectionSource, Prescription.class);
            TableUtils.createTable(connectionSource, Dispense.class);
            TableUtils.createTable(connectionSource, Form.class);
            TableUtils.createTable(connectionSource, Drug.class);
            TableUtils.createTable(connectionSource, Stock.class);
            TableUtils.createTable(connectionSource, DispensedDrug.class);
            TableUtils.createTable(connectionSource, Episode.class);
            TableUtils.createTable(connectionSource, PrescribedDrug.class);
            TableUtils.createTable(connectionSource, RegimenDrug.class);
            TableUtils.createTable(connectionSource, DestroyedDrug.class);
            TableUtils.createTable(connectionSource, StockAjustment.class);
            TableUtils.createTable(connectionSource, Iventory.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        
    }
}
