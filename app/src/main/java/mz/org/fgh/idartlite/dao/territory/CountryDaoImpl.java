package mz.org.fgh.idartlite.dao.territory;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.Country;
import mz.org.fgh.idartlite.model.RegimenDrug;

public class CountryDaoImpl extends GenericDaoImpl<Country, Integer> implements ICountryDao {
    public CountryDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public CountryDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public CountryDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }




    @Override
    public List<Country> getAllCountrys() throws SQLException {
        return queryForAll();
    }

    @Override
    public Country getCountryByRestId(int restId) throws SQLException {
        return queryBuilder().where().eq(Country.COLUMN_REST_ID,restId).queryForFirst();
    }
}
