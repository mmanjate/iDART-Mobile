package mz.org.fgh.idartlite.service;

import android.app.Application;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.Stock;
import mz.org.fgh.idartlite.model.User;

public class DispenseDrugService extends BaseService {

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
