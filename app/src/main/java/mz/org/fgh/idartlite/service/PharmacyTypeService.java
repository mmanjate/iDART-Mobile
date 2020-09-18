package mz.org.fgh.idartlite.service;

import android.app.Application;

import java.sql.SQLException;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.PharmacyType;
import mz.org.fgh.idartlite.model.User;

public class PharmacyTypeService extends BaseService {
    public PharmacyTypeService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public void savePharmacyType(PharmacyType pharmacyType) throws SQLException {
        getDataBaseHelper().getPharmacyTypeDao().create(pharmacyType);
    }
}
