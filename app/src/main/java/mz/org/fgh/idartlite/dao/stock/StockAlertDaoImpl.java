package mz.org.fgh.idartlite.dao.stock;

import android.app.Application;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.databasehelper.IdartLiteDataBaseHelper;
import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.StockReportData;
import mz.org.fgh.idartlite.util.DateUtilities;

public class StockAlertDaoImpl extends GenericDaoImpl<StockReportData, Integer> implements IStockAlertDao{
    public StockAlertDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public StockAlertDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public StockAlertDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public List<StockReportData> getAllWithStock(Application application) throws SQLException {
        /*QueryBuilder<StockReportData, Integer> qb = queryBuilder();
        QueryBuilder<Stock, Integer> stockQb =  IdartLiteDataBaseHelper.getInstance(application.getApplicationContext()).getStockDao().queryBuilder();
        stockQb.where().gt(Stock.COLUMN_EXPIRY_DATE, DateUtilities.getCurrentDate());

        qb.join(stockQb);*/
        return queryForAll();
    }
}
