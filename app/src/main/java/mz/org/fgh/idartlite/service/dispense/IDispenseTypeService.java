package mz.org.fgh.idartlite.service.dispense;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.DispenseType;


public interface IDispenseTypeService extends IBaseService<DispenseType> {

    public void createDispenseType(DispenseType dispenseType) throws SQLException;

    public List<DispenseType> getAll() throws SQLException;

    public DispenseType getDispenseTypeByDescription(String description) throws SQLException;

    public boolean checkDipsenseType(Object dispenseType);

    public void saveDispenseType(Object dispenseType);

}
