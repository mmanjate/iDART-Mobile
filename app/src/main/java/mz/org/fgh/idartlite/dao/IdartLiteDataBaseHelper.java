package mz.org.fgh.idartlite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.DiseaseType;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispenseType;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Form;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.PharmacyType;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.RegimenDrug;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.TherapeuticLine;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.model.User;

public class IdartLiteDataBaseHelper extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME    = "idartlite.db";
    private static final int    DATABASE_VERSION = 1;

    private GenericDao genericDao;
    private UserDao userDao;
    private DispenseDao dispenseDao;
    private DispensedDrugDao dispensedDrugDao;
    private DrugDao drugDao;
    private EpisodeDao episodeDao;
    private PatientDao patientDao;
    private PharmacyTypeDao pharmacyTypeDao;
    private PrescriptionDao prescriptionDao;
    private StockDao stockDao;
    private TherapeuticLineDao therapeuticLineDao;
    private TherapeuticRegimenDao therapeuticRegimenDao;
    private ClinicDao clinicDao;

    public ClinicDao getClinicDao() throws SQLException {
        if(clinicDao == null){
            clinicDao = getDao(Clinic.class);
        }
        return clinicDao;
    }

    public GenericDao getGenericDao(BaseModel model) throws SQLException {
        if(genericDao == null){
            genericDao = getDao(model.getClass());
        }
        return genericDao;
    }

    public UserDao getUserDao() throws SQLException {
        if(userDao == null){
            userDao = getDao(User.class);
        }
        return userDao;
    }

    public DispenseDao getDispenseDao() throws SQLException {
        if(dispenseDao == null){
            dispenseDao = getDao(Dispense.class);
        }
        return dispenseDao;
    }

    public DispensedDrugDao getDispensedDrugDao() throws SQLException {
        if(dispensedDrugDao == null){
            dispensedDrugDao = getDao(DispensedDrug.class);
        }
        return dispensedDrugDao;
    }

    public DrugDao getDrugDao() throws SQLException {
        if(drugDao == null){
            drugDao = getDao(Drug.class);
        }
        return drugDao;
    }

    public EpisodeDao getEpisodeDao() throws SQLException {
        if(episodeDao == null){
            episodeDao = getDao(Episode.class);
        }
        return episodeDao;
    }

    public PatientDao getPatientDao() throws SQLException {
        if(patientDao == null){
            patientDao = getDao(Patient.class);
        }
        return patientDao;
    }

    public PharmacyTypeDao getPharmacyTypeDao() throws SQLException {
        if(pharmacyTypeDao == null){
            pharmacyTypeDao = getDao(PharmacyType.class);
        }
        return pharmacyTypeDao;
    }

    public PrescriptionDao getPrescriptionDao() throws SQLException {
        if(prescriptionDao == null){
            prescriptionDao = getDao(Prescription.class);
        }
        return prescriptionDao;
    }

    public StockDao getStockDao() throws SQLException {
        if(stockDao == null){
            stockDao = getDao(Stock.class);
        }
        return stockDao;
    }

    public TherapeuticLineDao getTherapeuticLineDao() throws SQLException {
        if(therapeuticLineDao == null){
            therapeuticLineDao = getDao(TherapeuticLine.class);
        }
        return therapeuticLineDao;
    }

    public TherapeuticRegimenDao getTherapeuticRegimenDao() throws SQLException {
        if(therapeuticRegimenDao == null){
            therapeuticRegimenDao = getDao(TherapeuticRegimen.class);
        }
        return therapeuticRegimenDao;
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
            TableUtils.createTable(connectionSource, Clinic.class);
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, DiseaseType.class);
            TableUtils.createTable(connectionSource, Dispense.class);
            TableUtils.createTable(connectionSource, DispensedDrug.class);
            TableUtils.createTable(connectionSource, DispenseType.class);
            TableUtils.createTable(connectionSource, Drug.class);
            TableUtils.createTable(connectionSource, Episode.class);
            TableUtils.createTable(connectionSource, Form.class);
            TableUtils.createTable(connectionSource, Patient.class);
            TableUtils.createTable(connectionSource, PharmacyType.class);
            TableUtils.createTable(connectionSource, Prescription.class);
            TableUtils.createTable(connectionSource, RegimenDrug.class);
            TableUtils.createTable(connectionSource, Stock.class);
            TableUtils.createTable(connectionSource, TherapeuticLine.class);
            TableUtils.createTable(connectionSource, TherapeuticRegimen.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
