package mz.org.fgh.idartlite.dao.territory;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.District;
import mz.org.fgh.idartlite.model.Province;
import mz.org.fgh.idartlite.model.Subdistrict;

public class SubdistrictDaoImpl extends GenericDaoImpl<Subdistrict, Integer> implements ISubdistrictDao {
    public SubdistrictDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public SubdistrictDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public SubdistrictDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }


    @Override
    public List<Subdistrict> getSubdistrictByDistrict(District district) throws SQLException {
        return queryBuilder().where().eq(Subdistrict.COLUMN_DISTRICT, district.getId()).query();
    }
}
