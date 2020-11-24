package mz.org.fgh.idartlite.dao.dispense;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.Dispense;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.ReturnedDrug;
import mz.org.fgh.idartlite.model.Stock;

public interface IReturnedDrugDao extends IGenericDao<ReturnedDrug, Integer> {

}
