package mz.org.fgh.idartlite.service.clinic;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.ClinicSectorType;
import mz.org.fgh.idartlite.model.User;

public class ClinicSectorTypeService extends BaseService<ClinicSectorType> implements IClinicSectorTypeService {


    public ClinicSectorTypeService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public ClinicSectorTypeService(Application application) {
        super(application);
    }

    @Override
    public List<ClinicSectorType> getAllClinicSectorType() throws SQLException {
        return getDataBaseHelper().getClinicSectorTypeDao().queryForAll();
    }
}
