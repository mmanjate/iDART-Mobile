package mz.org.fgh.idartlite.service.dispense;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.ReturnedDrug;
import mz.org.fgh.idartlite.model.Stock;


public interface IReturnedDrugService extends IBaseService {

    public void createReturnedDrug(ReturnedDrug dispenseDrugs) throws SQLException;


    public void createRemoveDrug(ReturnedDrug dispenseDrugs) throws SQLException;


}
