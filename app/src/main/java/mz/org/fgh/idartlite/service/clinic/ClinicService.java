package mz.org.fgh.idartlite.service.clinic;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.User;

import static mz.org.fgh.idartlite.model.Clinic.COLUMN_UUID;

public class ClinicService extends BaseService implements IClinicService {
    public ClinicService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public List<Clinic> getAllClinics() throws SQLException {
      return getDataBaseHelper().getIClinicDao().getAllClinics();
    }

    public void saveClinic(Clinic clinic) throws SQLException {
        getDataBaseHelper().getIClinicDao().create(clinic);
    }

    public Clinic getClinicByUuid(String uuid) throws SQLException {

        return getDataBaseHelper().getIClinicDao().getClinicByUuid(uuid);
    }

}
