package mz.org.fgh.idartlite.dao.territory;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.District;
import mz.org.fgh.idartlite.model.Province;

public class DistrictDaoImpl extends GenericDaoImpl<District, Integer> implements IDistrictDao {
    public DistrictDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public DistrictDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public DistrictDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public List<District> getDistrictByProvince(Province province) throws SQLException {
        return queryBuilder().where().eq(District.COLUMN_PROVINCE, province.getId()).query();
    }

    @Override
    public District getDistrictByCode(String code) throws SQLException {
        return queryBuilder().where().eq(District.COLUMN_CODE,code).queryForFirst();
    }
}
