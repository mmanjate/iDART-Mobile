package mz.org.fgh.idartlite.dao.generic;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import mz.org.fgh.idartlite.util.SimpleValue;

public interface IGenericDao<T, ID> extends Dao<T, ID> {

    public List<SimpleValue> countDispensesRegimenByPeriod(Date start, Date end) throws SQLException;
}
