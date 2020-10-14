package mz.org.fgh.idartlite.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Stock;

import java.sql.SQLException;
import java.util.List;

public class StockDaoImpl extends GenericDaoImpl<Stock, Integer> implements StockDao {

    public StockDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public StockDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public StockDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public List<Stock> getStockByClinic(Clinic clinic, long offset, long limit) throws SQLException {
        return queryBuilder().offset(offset).limit(limit).distinct().selectColumns(Stock.COLUMN_ORDER_NUMBER).selectColumns(Stock.COLUMN_DATE_RECEIVED).selectColumns(Stock.COLUMN_SYNC_STATUS).selectColumns(Stock.COLUMN_BATCH_NUMBER).selectColumns(Stock.COLUMN_CLINIC).selectColumns(Stock.COLUMN_DRUG).selectColumns(Stock.COLUMN_EXPIRY_DATE).selectColumns(Stock.COLUMN_PRICE).selectColumns(Stock.COLUMN_REST_ID).selectColumns(Stock.COLUMN_SHELF_NUMBER).selectColumns(Stock.COLUMN_STOCK_ADJUSTMENTS).selectColumns(Stock.COLUMN_STOCK_MOVIMENT).selectColumns(Stock.COLUMN_UNITS_RECEIVED).selectColumns(Stock.COLUMN_UUID).selectColumns(Stock.COLUMN_ID).orderBy(Stock.COLUMN_ORDER_NUMBER, true).where().eq(Stock.COLUMN_CLINIC, clinic.getId()).query();
    }

    @Override
    public List<Stock> getStockByOrderNumber(String orderNumber, Clinic clinic) throws SQLException {
        return queryBuilder().where().eq(Stock.COLUMN_CLINIC, clinic.getId()).and().eq(Stock.COLUMN_ORDER_NUMBER, orderNumber).query();
    }

    @Override
    public boolean checkStockExist(String orderNumber, Clinic clinic) throws SQLException {
        return queryBuilder().where().eq(Stock.COLUMN_CLINIC, clinic.getId()).and().like(Stock.COLUMN_ORDER_NUMBER, "%" + orderNumber + "%").query().isEmpty();
    }

    @Override
    public List<Stock> getAllStocksByClinicAndDrug(Clinic clinic, Drug drug) throws SQLException {
        return queryBuilder().where().eq(Stock.COLUMN_CLINIC,clinic.getId()).and().eq(Stock.COLUMN_DRUG, drug.getId()).query();
    }
}
