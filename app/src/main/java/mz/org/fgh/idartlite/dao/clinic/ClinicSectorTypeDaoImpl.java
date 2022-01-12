package mz.org.fgh.idartlite.dao.clinic;

import java.sql.SQLException;

import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.ClinicSectorType;

public class ClinicSectorTypeDaoImpl extends GenericDaoImpl<ClinicSectorType, Integer> implements IClinicSectorTypeDao {
    public ClinicSectorTypeDaoImpl(Class dataClass) throws SQLException {
        super(dataClass);
    }
}
