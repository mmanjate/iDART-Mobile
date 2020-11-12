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

    public List<Clinic> getCLinic() throws SQLException {
         List<Clinic> list =getDataBaseHelper().getIClinicDao().queryForAll();
        return list;
    }

    public void saveClinic(Clinic clinic) throws SQLException {
        getDataBaseHelper().getIClinicDao().create(clinic);
    }

    public Clinic getClinic(String uuid) throws SQLException {

        List<Clinic> typeList = getDataBaseHelper().getIClinicDao().queryForEq(COLUMN_UUID, uuid);

        if (typeList != null)
            if (!typeList.isEmpty())
                return typeList.get(0);

        return null;
    }

}
