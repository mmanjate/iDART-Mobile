package mz.org.fgh.idartlite.service;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.User;

public class DrugService extends BaseService {
    public DrugService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public void saveDrug(Drug drug) throws SQLException {
        getDataBaseHelper().getDrugDao().create(drug);
    }

    public List<Drug> getDrugListAll() throws SQLException {
        return getDataBaseHelper().getDrugDao().queryForAll();
    }
}
