package mz.org.fgh.idartlite.dao;

import android.app.Application;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.common.ValorSimples;

public class GenericDaoImpl<T, ID> extends BaseDaoImpl<T, ID> implements GenericDao<T, ID> {

    public GenericDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }

    public GenericDaoImpl(ConnectionSource connectionSource, Class dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public GenericDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    public List<ValorSimples> countDispensesRegimenByPeriod(Date start, Date end) throws SQLException {

        String sql = "select therapeutic_regimen.description, count(*) as qty\n" +
                "from Dispense inner join prescription on dispense.prescription_id = prescription.id\n" +
                "\t\t\t  inner join therapeutic_regimen on therapeutic_regimen.id = prescription.regimen_id\n" +
                "GROUP by therapeutic_regimen.description";

        GenericRawResults<T> rawResults = queryRaw(sql, getRawRowMapper());

        return (List<ValorSimples>) rawResults.getResults();
    }

}
