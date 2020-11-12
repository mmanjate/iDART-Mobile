package mz.org.fgh.idartlite.service.dispense;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.service.clinic.IPharmacyTypeService;

public class DispenseDrugService extends BaseService implements IDispenseDrugService {

    public DispenseDrugService(Application application, User currUser) {
        super(application, currUser);
    }

    public void createDispensedDrug(DispensedDrug dispenseDrug) throws SQLException {
        getDataBaseHelper().getDispensedDrugDao().create(dispenseDrug);
    }

    public List<DispensedDrug> findDispensedDrugByDispenseId(int id) throws SQLException {

        return getDataBaseHelper().getDispensedDrugDao().queryForEq(DispensedDrug.COLUMN_DISPENSE, id);
    }

    public void saveOrUpdateDispensedDrug(DispensedDrug dispensedDrug) throws SQLException {
        getDataBaseHelper().getDispensedDrugDao().createOrUpdate(dispensedDrug);
    }

    public boolean checkStockIsDispensedDrug(Stock stock) throws SQLException {
        return getDataBaseHelper().getDispensedDrugDao().checkStockIsDispensedDrug(stock);
    }

}
