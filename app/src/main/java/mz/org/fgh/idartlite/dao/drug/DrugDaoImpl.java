package mz.org.fgh.idartlite.dao.drug;

import android.app.Application;

import com.j256.ormlite.stmt.ColumnArg;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.databasehelper.IdartLiteDataBaseHelper;
import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.DestroyedDrug;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.RegimenDrug;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.StockAjustment;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.model.inventory.Iventory;
import mz.org.fgh.idartlite.util.DateUtilities;

public class DrugDaoImpl extends GenericDaoImpl<Drug, Integer> implements IDrugDao {

    public DrugDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public DrugDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public DrugDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public List<Drug> getAll() throws SQLException {
        return queryForAll();
    }

    @Override
    public List<Drug> getAllByTherapeuticRegimen(Application application,TherapeuticRegimen therapeuticRegimen) throws SQLException {
        QueryBuilder<RegimenDrug, Integer> regimeDrugQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getRegimenDrugDao().queryBuilder();
        regimeDrugQb.where().eq(RegimenDrug.COLUMN_THERAPEUTIC_REGIMEN_ID, therapeuticRegimen.getId());

        QueryBuilder<Drug, Integer> drugQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getDrugDao().queryBuilder();
        return drugQb.join(regimeDrugQb).query();
    }

    @Override
    public Drug getDrugByFNMCode(String code) throws SQLException {
        return queryBuilder().where().eq(Drug.COLUMN_FNMCODE, code).queryForFirst();
    }

    @Override
    public Drug getDrugByDescription(String description) throws SQLException {
        return queryBuilder().where().eq(Drug.COLUMN_DESCRIPTION, description).queryForFirst();
    }

    @Override
    public Drug getDrugByRestID(int restId) throws SQLException {
        return queryBuilder().where().eq(Drug.COLUMN_REST_ID, restId).queryForFirst();
    }

    @Override
    public List<Drug> getAllWithLote(Application application) throws SQLException {

        QueryBuilder<Stock, Integer> stockQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getStockDao().queryBuilder();
        stockQb.where().eq(Stock.COLUMN_DRUG, new ColumnArg(Drug.TABLE_NAME, Drug.COLUMN_ID)).and().gt(Stock.COLUMN_UNITS_RECEIVED, 0);

        QueryBuilder<Drug, Integer> drugQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getDrugDao().queryBuilder();
        drugQb.where().exists(stockQb);
        drugQb.orderBy(Drug.COLUMN_DESCRIPTION, true);
        return drugQb.query();
    }

    @Override
    public List<Drug> getAllDestroyedDrugs(Application application) throws SQLException {

        QueryBuilder<DestroyedDrug, Integer> destroyedQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getDestroyedStockDrugDao().queryBuilder();

        QueryBuilder<Stock, Integer> stockQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getStockDao().queryBuilder();
        stockQb.where().eq(Stock.COLUMN_DRUG, new ColumnArg(Drug.TABLE_NAME, Drug.COLUMN_ID));
        stockQb.join(destroyedQb);

        QueryBuilder<Drug, Integer> drugQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getDrugDao().queryBuilder();
        drugQb.where().exists(stockQb);
        drugQb.orderBy(Drug.COLUMN_DESCRIPTION, true);
        return drugQb.query();
    }

    @Override
    public List<Drug> getAllOnInventory(Iventory iventory, Application application) throws SQLException {

        QueryBuilder<StockAjustment, Integer> destroyedQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getStockAjustmentDao().queryBuilder();
        destroyedQb.where().eq(StockAjustment.COLUMN_IVENTORY_ID, iventory.getId());

        QueryBuilder<Stock, Integer> stockQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getStockDao().queryBuilder();
        stockQb.where().eq(Stock.COLUMN_DRUG, new ColumnArg(Drug.TABLE_NAME, Drug.COLUMN_ID));
        stockQb.join(destroyedQb);

        QueryBuilder<Drug, Integer> drugQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getDrugDao().queryBuilder();
        drugQb.distinct();
        drugQb.where().exists(stockQb);
        drugQb.orderBy(Drug.COLUMN_DESCRIPTION, true);
        return drugQb.query();
    }

    @Override
    public List<Drug> getAllWithLoteAndNotExpired(Application application) throws SQLException {
        QueryBuilder<Stock, Integer> stockQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getStockDao().queryBuilder();
        stockQb.where().eq(Stock.COLUMN_DRUG, new ColumnArg(Drug.TABLE_NAME, Drug.COLUMN_ID)).and().gt(Stock.COLUMN_UNITS_RECEIVED, 0).and().gt(Stock.COLUMN_EXPIRY_DATE, DateUtilities.getCurrentDate());

        QueryBuilder<Drug, Integer> drugQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getDrugDao().queryBuilder();
        drugQb.where().exists(stockQb);
        drugQb.orderBy(Drug.COLUMN_DESCRIPTION, true);
        return drugQb.query();
    }

}
