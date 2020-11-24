package mz.org.fgh.idartlite.dao.dispense;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.ReturnedDrug;
import mz.org.fgh.idartlite.model.Stock;

public class ReturnedDrugDaoImpl extends GenericDaoImpl<ReturnedDrug, Integer> implements IReturnedDrugDao {

    public ReturnedDrugDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public ReturnedDrugDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public ReturnedDrugDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

}
