package mz.org.fgh.idartlite.service.clinic;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.Clinic;


public interface IClinicService  extends IBaseService<Clinic> {

    public List<Clinic> getAllClinics() throws SQLException;

    public void saveClinic(Clinic clinic) throws SQLException;

    public Clinic getClinicByUuid(String uuid) throws SQLException;
}
