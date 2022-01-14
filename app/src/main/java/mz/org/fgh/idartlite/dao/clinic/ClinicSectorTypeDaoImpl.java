package mz.org.fgh.idartlite.dao.clinic;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.ClinicSectorType;

public class ClinicSectorTypeDaoImpl extends GenericDaoImpl<ClinicSectorType, Integer> implements IClinicSectorTypeDao {
    public ClinicSectorTypeDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public ClinicSectorTypeDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public ClinicSectorTypeDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public List<ClinicSectorType> getAllClinicSectorType() throws SQLException {
        return queryForAll();
    }
}
