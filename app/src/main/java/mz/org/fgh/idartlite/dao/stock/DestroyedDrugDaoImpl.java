package mz.org.fgh.idartlite.dao.stock;

import android.app.Application;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.base.databasehelper.IdartLiteDataBaseHelper;
import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.DestroyedDrug;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Stock;

public class DestroyedDrugDaoImpl extends GenericDaoImpl<DestroyedDrug, Integer> implements IDestroyedDrugDao {

    public DestroyedDrugDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public DestroyedDrugDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public DestroyedDrugDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public List<DestroyedDrug> searchRecords(long offset, long limit) throws SQLException {
        return super.searchRecords(offset, limit);
    }

    @Override
    public List<DestroyedDrug> searchDestructions(Application application, long offset, long limit) throws SQLException {
        QueryBuilder<DestroyedDrug, Integer> destroyedQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getDestroyedStockDrugDao().queryBuilder();

        QueryBuilder<Stock, Integer> stockQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getStockDao().queryBuilder();

        QueryBuilder<Drug, Integer> drugQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getDrugDao().queryBuilder();

        stockQb.join(drugQb);
        destroyedQb.join(stockQb);
        destroyedQb.orderBy(DestroyedDrug.COLUMN_DATE, true);
        destroyedQb.groupBy(DestroyedDrug.COLUMN_DATE);
        if (offset > 0 && limit > 0) destroyedQb.offset(offset).limit(limit);

        return destroyedQb.query();

    }

    @Override
    public List<DestroyedDrug> getByDateAndDrug(Application application, Date date, Drug drug) throws SQLException {
        QueryBuilder<DestroyedDrug, Integer> destroyedQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getDestroyedStockDrugDao().queryBuilder();
        destroyedQb.where().eq(DestroyedDrug.COLUMN_DATE, date);

        QueryBuilder<Stock, Integer> stockQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getStockDao().queryBuilder();

        QueryBuilder<Drug, Integer> drugQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getDrugDao().queryBuilder();
        drugQb.where().eq(Drug.COLUMN_ID, drug.getId());

        stockQb.join(drugQb);
        destroyedQb.join(stockQb);
        destroyedQb.orderBy(DestroyedDrug.COLUMN_ID, true);

        return destroyedQb.query();

    }

}
