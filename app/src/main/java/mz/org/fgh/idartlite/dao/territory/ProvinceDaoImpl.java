package mz.org.fgh.idartlite.dao.territory;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.Country;
import mz.org.fgh.idartlite.model.Episode;
import mz.org.fgh.idartlite.model.Province;

public class ProvinceDaoImpl extends GenericDaoImpl<Province, Integer> implements IProvinceDao {
    public ProvinceDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public ProvinceDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public ProvinceDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }


    @Override
    public List<Province> getAllProvinces() throws SQLException {
        return queryForAll();
    }

    @Override
    public List<Province> getProvinceByCountry(Country country) throws SQLException {
        return queryBuilder().where().eq(Province.COLUMN_COUNTRY, country.getId()).query();
    }

    @Override
    public Province getProvinceByRestId(int restId) throws SQLException {
        return queryBuilder().where().eq(Province.COLUMN_REST_ID,restId).queryForFirst();
    }
}
