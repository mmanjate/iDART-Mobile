package mz.org.fgh.idartlite.dao.clinic;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.PharmacyType;

import java.sql.SQLException;
import java.util.List;

import static mz.org.fgh.idartlite.model.Clinic.COLUMN_CODE;
import static mz.org.fgh.idartlite.model.Clinic.COLUMN_UUID;

public class PharmacyTypeDaoImpl extends GenericDaoImpl<PharmacyType, Integer> implements IPharmacyTypeDao {

    public PharmacyTypeDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public PharmacyTypeDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public PharmacyTypeDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public List<PharmacyType> getAllPharmacyType() throws SQLException {
        return queryForAll();
    }

    @Override
    public PharmacyType getPharmacyTypeByCode(String code) throws SQLException {
        return queryBuilder().where().eq(COLUMN_CODE, code).queryForFirst();
    }


}
