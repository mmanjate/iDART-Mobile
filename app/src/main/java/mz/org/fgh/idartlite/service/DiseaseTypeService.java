package mz.org.fgh.idartlite.service;

import android.app.Application;

import java.sql.SQLException;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.DiseaseType;
import mz.org.fgh.idartlite.model.User;

public class DiseaseTypeService extends BaseService {
    public DiseaseTypeService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public void saveDiseaseType(DiseaseType diseaseType) throws SQLException {
        getDataBaseHelper().getDiseaseTypeDao().create(diseaseType);
    }
}
