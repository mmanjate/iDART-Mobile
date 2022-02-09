package mz.org.fgh.idartlite.service.clinic;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.User;

public class ClinicService extends BaseService<Clinic> implements IClinicService {

    public ClinicService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public ClinicService(Application application) {
        super(application);
    }

    @Override
    public void save(Clinic record) throws SQLException {

    }

    @Override
    public void update(Clinic record) throws SQLException {

    }

    public List<Clinic> getAllClinics() throws SQLException {
        System.out.println("#######################################çççççççççççççççççççç-");
        System.out.println(getDataBaseHelper().getIClinicDao().getAllClinics().size());
      return getDataBaseHelper().getIClinicDao().getAllClinics();
    }

    public void saveClinic(Clinic clinic) throws SQLException {
        getDataBaseHelper().getIClinicDao().create(clinic);
    }

    public Clinic getClinicByUuid(String uuid) throws SQLException {

        return getDataBaseHelper().getIClinicDao().getClinicByUuid(uuid);
    }

}
