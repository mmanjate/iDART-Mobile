package mz.org.fgh.idartlite.service;

import android.app.Application;

import java.sql.SQLException;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.DispensedDrug;
import mz.org.fgh.idartlite.model.User;

public class DispenseDrugService extends BaseService {

    public DispenseDrugService(Application application, User currUser) {
        super(application, currUser);
    }

    public void createDispensedDrug(DispensedDrug dispenseDrug) throws SQLException {
        getDataBaseHelper().getDispensedDrugDao().create(dispenseDrug);
    }

}
