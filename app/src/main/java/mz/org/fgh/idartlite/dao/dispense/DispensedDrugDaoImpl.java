package mz.org.fgh.idartlite.dao.dispense;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Stock;


import java.sql.SQLException;
import java.util.List;

public class DispensedDrugDaoImpl extends GenericDaoImpl<DispensedDrug, Integer> implements IDispensedDrugDao {

    public DispensedDrugDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public DispensedDrugDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public DispensedDrugDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public boolean checkStockIsDispensedDrug(Stock stock) throws SQLException {
        return queryBuilder().where().eq(DispensedDrug.COLUMN_STOCK, stock.getId()).query().isEmpty();
    }

    @Override
    public List<DispensedDrug> getDispensedDrugsByDispenses(List<Dispense> dispenses) throws SQLException {
        return queryBuilder().where().in(DispensedDrug.COLUMN_DISPENSE,dispenses).query();
    }
}
