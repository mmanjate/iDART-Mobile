package mz.org.fgh.idartlite.service;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.User;

public class ClinicService extends BaseService {
    public ClinicService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public List<Clinic> getCLinic() throws SQLException {
         List<Clinic> list =getDataBaseHelper().getClinicDao().queryForAll();
        return list;
    }

    public void saveClinic(Clinic clinic) throws SQLException {
        getDataBaseHelper().getClinicDao().create(clinic);
    }
}
