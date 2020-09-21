package mz.org.fgh.idartlite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.model.*;

public class IdartLiteDataBaseHelper extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME    = "idartlite.db";
    private static final int    DATABASE_VERSION = 1;


    private UserDao userDao;
    private GenericDao genericDao;

    private ClinicDao clinicDao;
    private DiseaseTypeDao diseaseTypeDao;
    private DispenseDao dispenseDao;
    private DispensedDrugDao dispensedDrugDao;
    private DispenseTypeDao dispenseTypeDao;
    private DrugDao drugDao;

    private EpisodeDao episodeDao;
    private FormDao formDao;
    private PatientDao patientDao;
    private PharmacyTypeDao pharmacyTypeDao;
    private PrescribedDrugDao prescribedDrugDao;
    private PrescriptionDao prescriptionDao;
    private RegimenDrugDao regimenDrugDao;
    private StockDao stockDao;
    private TherapeuticLineDao therapeuticLineDao;
    private TherapeuticRegimenDao therapeuticRegimenDao;

    public ClinicDao getClinicDao() throws SQLException {
        if(clinicDao == null){
            clinicDao = getDao(Clinic.class);
        }
        return clinicDao;
    }

    public DiseaseTypeDao getDiseaseTypeDao() throws SQLException {
        if(diseaseTypeDao == null){
            diseaseTypeDao = getDao(DiseaseType.class);
        }
        return diseaseTypeDao;
    }

    public DispensedDrugDao getDispensedDrugDao() throws SQLException {
        if(dispensedDrugDao == null){
            dispensedDrugDao = getDao(DispensedDrug.class);
        }
        return dispensedDrugDao;
    }

    public DispenseTypeDao getDispenseTypeDao() throws SQLException {
        if(dispenseTypeDao == null){
            dispenseTypeDao = getDao(DispenseType.class);
        }
        return dispenseTypeDao;
    }

    public DrugDao getDrugDao() throws SQLException {
        return drugDao;
    }

    public FormDao getFormDao() throws SQLException {
        if(formDao == null){
            formDao = getDao(Form.class);
        }
        return formDao;
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

    public PrescribedDrugDao getPrescribedDrugDao() throws SQLException {
        if(prescribedDrugDao == null){
            prescribedDrugDao = getDao(PrescribedDrug.class);
        }
        return prescribedDrugDao;
    }

    public RegimenDrugDao getRegimenDrugDao() throws SQLException {
        return regimenDrugDao;
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

    public EpisodeDao getEpisodeDao() throws SQLException {
        if(episodeDao == null){
            episodeDao = getDao(Episode.class);
        }
        return episodeDao;
    }

    public PrescriptionDao getPrescriptionDao() throws SQLException {
        if(prescriptionDao == null){
            prescriptionDao = getDao(Prescription.class);
        }
        return prescriptionDao;
    }

    public DispenseDao getDispenseDao() throws SQLException {
        if(dispenseDao == null){
            dispenseDao = getDao(Dispense.class);
        }
        return dispenseDao;
    }

    public StockDao getStockDao() throws SQLException {
        if(stockDao == null){
            stockDao = getDao(Stock.class);
        }
        return stockDao;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
