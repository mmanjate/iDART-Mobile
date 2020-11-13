package mz.org.fgh.idartlite.service.dispense;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.DispenseType;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Stock;

import static mz.org.fgh.idartlite.model.DiseaseType.COLUMN_DESCRIPTION;


public interface IDispenseTypeService extends IBaseService {

    public void createDispenseType(DispenseType dispenseType) throws SQLException;

    public List<DispenseType> getAll() throws SQLException;

    public DispenseType getDispenseTypeByCode(String code) throws SQLException;

    public boolean checkDipsenseType(Object dispenseType);

    public void saveDispenseType(Object dispenseType);

}
