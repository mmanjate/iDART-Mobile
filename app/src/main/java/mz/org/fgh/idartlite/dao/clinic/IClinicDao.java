package mz.org.fgh.idartlite.dao.clinic;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.Clinic;

public interface IClinicDao extends IGenericDao<Clinic, Integer> {

    public List<Clinic> getAllClinics() throws SQLException;

    public Clinic getClinicByUuid(String uuid) throws SQLException;

}
