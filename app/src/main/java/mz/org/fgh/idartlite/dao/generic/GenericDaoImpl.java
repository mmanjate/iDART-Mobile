package mz.org.fgh.idartlite.dao.generic;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.util.SimpleValue;

public class GenericDaoImpl<T, ID> extends BaseDaoImpl<T, ID> implements IGenericDao<T, ID> {

    public GenericDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public GenericDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public GenericDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public List<SimpleValue> countDispensesRegimenByPeriod(Date start, Date end) throws SQLException {

        String sql = "select therapeutic_regimen.description, count(*) as qty\n" +
                "from Dispense inner join prescription on dispense.prescription_id = prescription.id\n" +
                "\t\t\t  inner join therapeutic_regimen on therapeutic_regimen.id = prescription.regimen_id\n" +
                "GROUP by therapeutic_regimen.description";

        GenericRawResults<T> rawResults = queryRaw(sql, getRawRowMapper());

        return (List<SimpleValue>) rawResults.getResults();
    }

    @Override
    public List<T> searchRecords(long offset, long limit) throws SQLException {
        return queryBuilder().limit(limit).offset(offset).query();
    }

}
