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
import mz.org.fgh.idartlite.dao.clinic.IClinicSectorDao;
import mz.org.fgh.idartlite.dao.clinic.IPharmacyTypeDao;
import mz.org.fgh.idartlite.dao.clinicInfo.IClinicInfoDao;
import mz.org.fgh.idartlite.dao.dispense.IDispenseDao;
import mz.org.fgh.idartlite.dao.dispense.IDispenseTypeDao;
import mz.org.fgh.idartlite.dao.dispense.IDispensedDrugDao;
import mz.org.fgh.idartlite.dao.dispense.IReturnedDrugDao;
import mz.org.fgh.idartlite.dao.drug.IDiseaseTypeDao;
import mz.org.fgh.idartlite.dao.drug.IDrugDao;
import mz.org.fgh.idartlite.dao.drug.IFormDao;
import mz.org.fgh.idartlite.dao.drug.IRegimenDrugDao;
import mz.org.fgh.idartlite.dao.drug.ITherapeuticLineDao;
import mz.org.fgh.idartlite.dao.drug.ITherapeuticRegimenDao;
import mz.org.fgh.idartlite.dao.episode.IEpisodeDao;
import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.dao.param.operationtype.IOperationTypeDao;
import mz.org.fgh.idartlite.dao.patient.IPatientDao;
import mz.org.fgh.idartlite.dao.prescription.IPrescribedDrugDao;
import mz.org.fgh.idartlite.dao.prescription.IPrescriptionDao;
import mz.org.fgh.idartlite.dao.stock.IDestroyedDrugDao;
import mz.org.fgh.idartlite.dao.stock.IIventoryDao;
import mz.org.fgh.idartlite.dao.stock.IReferedStockMovimentDao;
import mz.org.fgh.idartlite.dao.stock.IStockAjustmentDao;
import mz.org.fgh.idartlite.dao.stock.IStockDao;
import mz.org.fgh.idartlite.dao.territory.ICountryDao;
import mz.org.fgh.idartlite.dao.territory.IDistrictDao;
import mz.org.fgh.idartlite.dao.territory.IProvinceDao;
import mz.org.fgh.idartlite.dao.territory.ISubdistrictDao;
import mz.org.fgh.idartlite.dao.user.IUserDao;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.ClinicInformation;
import mz.org.fgh.idartlite.model.ClinicSector;
import mz.org.fgh.idartlite.model.Country;
import mz.org.fgh.idartlite.model.DestroyedDrug;
import mz.org.fgh.idartlite.model.DiseaseType;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispenseType;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.District;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Form;
import mz.org.fgh.idartlite.model.OperationType;
import mz.org.fgh.idartlite.model.Patient;
import mz.org.fgh.idartlite.model.PharmacyType;
import mz.org.fgh.idartlite.model.PrescribedDrug;
import mz.org.fgh.idartlite.model.Prescription;
import mz.org.fgh.idartlite.model.Province;
import mz.org.fgh.idartlite.model.ReferedStockMoviment;
import mz.org.fgh.idartlite.model.RegimenDrug;
import mz.org.fgh.idartlite.model.ReturnedDrug;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.StockAjustment;
import mz.org.fgh.idartlite.model.Subdistrict;
import mz.org.fgh.idartlite.model.TherapeuticLine;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.model.inventory.Iventory;

public class IdartLiteDataBaseHelper extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME    = "idartlite.db";
    private static final int    DATABASE_VERSION = 6;


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

    private ICountryDao countryDao;
    private IProvinceDao provinceDao;
    private IDistrictDao districtDao;
    private ISubdistrictDao subdistrictDao;

    private IClinicSectorDao clinicSectorDao;
    private IReturnedDrugDao returnedDrugDao;

    private IReferedStockMovimentDao referedStockMovimentDao;
    private IOperationTypeDao operationTypeDao;

    private IClinicInfoDao clinicInfoDao;

 //   private IPatientSectorDao patientSectorDao;


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

    public ICountryDao getCountryDao() throws SQLException {
        if(countryDao == null){
            countryDao = getDao(Country.class);
        }
        return countryDao;
    }

    public IProvinceDao getProvinceDao() throws SQLException {
        if(provinceDao == null){
            provinceDao = getDao(Province.class);
        }
        return provinceDao;
    }

    public IDistrictDao getDistrictDao() throws SQLException {
        if(districtDao == null){
            districtDao = getDao(District.class);
        }
        return districtDao;
    }

    public ISubdistrictDao getSubdistrictDao() throws SQLException {
        if(subdistrictDao == null){
            subdistrictDao = getDao(Subdistrict.class);
        }
        return subdistrictDao;
    }

    public IReferedStockMovimentDao getReferedStockMovimentDao() throws SQLException {
        if(referedStockMovimentDao == null){
            referedStockMovimentDao = getDao(ReferedStockMoviment.class);
        }
        return referedStockMovimentDao;
    }

    public IOperationTypeDao getOperationTypeDao() throws SQLException {
        if(operationTypeDao == null){
            operationTypeDao = getDao(OperationType.class);
        }
        return operationTypeDao;
    }

    /* public IPatientSectorDao getPatientSectorDao() throws SQLException {
        if(patientSectorDao == null){
            patientSectorDao = getDao(PatientSector.class);
        }
        return patientSectorDao;
    }*/



    public IClinicSectorDao getClinicSectorDao() throws SQLException {
        if(clinicSectorDao == null){
            clinicSectorDao = getDao(ClinicSector.class);
        }
        return clinicSectorDao;
    }

    public IReturnedDrugDao getReturnedDrugDao() throws SQLException {
        if(returnedDrugDao == null){
            returnedDrugDao = getDao(ReturnedDrug.class);
        }
        return returnedDrugDao;
    }


    public IClinicInfoDao getClinicInfoDao() throws SQLException {
        if(clinicInfoDao == null){
            clinicInfoDao = getDao(ClinicInformation.class);
        }
        return clinicInfoDao;
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

            TableUtils.createTableIfNotExists(connectionSource, DispenseType.class);
            TableUtils.createTableIfNotExists(connectionSource, DiseaseType.class);
            TableUtils.createTableIfNotExists(connectionSource, PharmacyType.class);
            TableUtils.createTableIfNotExists(connectionSource, Clinic.class);
            TableUtils.createTableIfNotExists(connectionSource, User.class);
            TableUtils.createTableIfNotExists(connectionSource, TherapeuticRegimen.class);
            TableUtils.createTableIfNotExists(connectionSource, TherapeuticLine.class);
            TableUtils.createTableIfNotExists(connectionSource, Patient.class);
            TableUtils.createTableIfNotExists(connectionSource, Prescription.class);
            TableUtils.createTableIfNotExists(connectionSource, Dispense.class);
            TableUtils.createTableIfNotExists(connectionSource, Form.class);
            TableUtils.createTableIfNotExists(connectionSource, Drug.class);
            TableUtils.createTableIfNotExists(connectionSource, Stock.class);
            TableUtils.createTableIfNotExists(connectionSource, DispensedDrug.class);
            TableUtils.createTableIfNotExists(connectionSource, Episode.class);
            TableUtils.createTableIfNotExists(connectionSource, RegimenDrug.class);
            TableUtils.createTableIfNotExists(connectionSource, DestroyedDrug.class);
            TableUtils.createTableIfNotExists(connectionSource, StockAjustment.class);
            TableUtils.createTableIfNotExists(connectionSource, Iventory.class);
            TableUtils.createTableIfNotExists(connectionSource, PrescribedDrug.class);
            TableUtils.createTableIfNotExists(connectionSource, Country.class);
            TableUtils.createTableIfNotExists(connectionSource, Province.class);
            TableUtils.createTableIfNotExists(connectionSource, District.class);
            TableUtils.createTableIfNotExists(connectionSource, Subdistrict.class);
            TableUtils.createTableIfNotExists(connectionSource, ClinicSector.class);
            TableUtils.createTableIfNotExists(connectionSource, ReturnedDrug.class);
            TableUtils.createTableIfNotExists(connectionSource, ReferedStockMoviment.class);
            TableUtils.createTableIfNotExists(connectionSource, OperationType.class);
            TableUtils.createTableIfNotExists(connectionSource, ClinicInformation.class);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        //dropTables();
        //onCreate(database,connectionSource);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //dropTables();
        //onCreate(db,connectionSource);
    }

    private void dropTables() {
        try {
            TableUtils.dropTable(connectionSource, DispenseType.class, true);

            TableUtils.dropTable(connectionSource, DiseaseType.class, true);
            TableUtils.dropTable(connectionSource, PharmacyType.class, true);
            TableUtils.dropTable(connectionSource, Clinic.class, true);
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, TherapeuticRegimen.class, true);
            TableUtils.dropTable(connectionSource, TherapeuticLine.class, true);
            TableUtils.dropTable(connectionSource, Patient.class, true);
            TableUtils.dropTable(connectionSource, Prescription.class, true);
            TableUtils.dropTable(connectionSource, Dispense.class, true);
            TableUtils.dropTable(connectionSource, Form.class, true);
            TableUtils.dropTable(connectionSource, Drug.class, true);
            TableUtils.dropTable(connectionSource, Stock.class, true);
            TableUtils.dropTable(connectionSource, ReturnedDrug.class, true);
            TableUtils.dropTable(connectionSource, DispensedDrug.class, true);
            TableUtils.dropTable(connectionSource, Episode.class, true);
            TableUtils.dropTable(connectionSource, RegimenDrug.class, true);
            TableUtils.dropTable(connectionSource, DestroyedDrug.class, true);
            TableUtils.dropTable(connectionSource, StockAjustment.class, true);
            TableUtils.dropTable(connectionSource, Iventory.class, true);
            TableUtils.dropTable(connectionSource, PrescribedDrug.class, true);
            TableUtils.dropTable(connectionSource, Country.class, true);
            TableUtils.dropTable(connectionSource, Province.class, true);
            TableUtils.dropTable(connectionSource, District.class, true);
            TableUtils.dropTable(connectionSource, Subdistrict.class, true);
            TableUtils.dropTable(connectionSource, ClinicSector.class, true);
            TableUtils.dropTable(connectionSource, ReferedStockMoviment.class, true);
            TableUtils.dropTable(connectionSource, OperationType.class, true);

            TableUtils.dropTable(connectionSource, ClinicInformation.class, true);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
