package mz.org.fgh.idartlite.service.clinic;

import android.app.Application;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.User;


public interface IClinicService  extends IBaseService {

    public List<Clinic> getAllClinics() throws SQLException;

    public void saveClinic(Clinic clinic) throws SQLException;

    public Clinic getClinicByUuid(String uuid) throws SQLException;
}
