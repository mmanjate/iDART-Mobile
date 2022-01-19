package mz.org.fgh.idartlite.dao.clinic;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.ClinicSectorType;
import mz.org.fgh.idartlite.model.Province;

public interface IClinicSectorTypeDao extends IGenericDao<ClinicSectorType, Integer> {

    public List<ClinicSectorType> getAllClinicSectorType() throws SQLException;

}
