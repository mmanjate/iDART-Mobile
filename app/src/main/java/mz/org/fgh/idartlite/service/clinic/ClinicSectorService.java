package mz.org.fgh.idartlite.service.clinic;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.ClinicSector;
import mz.org.fgh.idartlite.model.ClinicSectorType;
import mz.org.fgh.idartlite.model.User;

public class ClinicSectorService extends BaseService implements IClinicSectorService {
    public ClinicSectorService(Application application, User currentUser) {
        super(application, currentUser);
    }



    @Override
    public List<ClinicSector> getClinicSectorsByClinic(Clinic clinic) throws SQLException {
        return getDataBaseHelper().getClinicSectorDao().getClinicSectorsByClinic(clinic);
    }

    @Override
    public List<ClinicSector> getClinicSectorsByType(ClinicSectorType clinicSectorType) throws SQLException {
        return getDataBaseHelper().getClinicSectorDao().getClinicSectorsByTYpe(clinicSectorType);
    }

    @Override
    public void saveClinicSector(ClinicSector clinicSector) throws SQLException {

        getDataBaseHelper().getClinicSectorDao().create(clinicSector);
    }

    @Override
    public ClinicSector getClinicSector() throws SQLException {
        return getDataBaseHelper().getClinicSectorDao().getClinicSector();
    }
}
