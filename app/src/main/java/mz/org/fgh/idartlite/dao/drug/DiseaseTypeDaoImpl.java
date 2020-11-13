package mz.org.fgh.idartlite.dao.drug;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.DiseaseType;

import static mz.org.fgh.idartlite.model.Clinic.COLUMN_CODE;

public class DiseaseTypeDaoImpl extends GenericDaoImpl<DiseaseType, Integer> implements IDiseaseTypeDao {
    public DiseaseTypeDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public DiseaseTypeDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public DiseaseTypeDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public List<DiseaseType> getAllDiseaseTypes() throws SQLException {
        return queryForAll();
    }

    @Override
    public DiseaseType getDiseaseTypeByCode(String code) throws SQLException {
        return queryBuilder().where().eq(COLUMN_CODE, code).queryForFirst();
    }
}
