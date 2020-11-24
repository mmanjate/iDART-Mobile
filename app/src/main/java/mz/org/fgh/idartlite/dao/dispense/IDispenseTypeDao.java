package mz.org.fgh.idartlite.dao.dispense;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.DispenseType;

public interface IDispenseTypeDao extends IGenericDao<DispenseType, Integer> {


    public List<DispenseType> getAll() throws SQLException;

    public DispenseType getDispenseTypeByDescription(String description) throws SQLException;
}
