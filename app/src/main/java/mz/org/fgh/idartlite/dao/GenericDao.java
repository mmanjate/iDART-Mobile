package mz.org.fgh.idartlite.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.common.ValorSimples;

public interface GenericDao<T, ID> extends Dao<T, ID> {

    public List<ValorSimples> countDispensesRegimenByPeriod(Date start, Date end) throws SQLException;
}
