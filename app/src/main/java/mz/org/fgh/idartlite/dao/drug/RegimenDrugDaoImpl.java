package mz.org.fgh.idartlite.dao.drug;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.RegimenDrug;

public class RegimenDrugDaoImpl extends GenericDaoImpl<RegimenDrug, Integer> implements IRegimenDrugDao {
    public RegimenDrugDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public RegimenDrugDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public RegimenDrugDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
