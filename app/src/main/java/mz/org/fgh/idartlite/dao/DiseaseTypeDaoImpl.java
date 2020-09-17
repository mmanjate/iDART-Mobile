package mz.org.fgh.idartlite.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

import mz.org.fgh.idartlite.model.DiseaseType;

public class DiseaseTypeDaoImpl extends GenericDaoImpl<DiseaseType, Integer> implements DiseaseTypeDao {
    public DiseaseTypeDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public DiseaseTypeDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public DiseaseTypeDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
