package mz.org.fgh.idartlite.dao.clinic;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.ClinicSector;

public interface IClinicSectorDao extends IGenericDao<ClinicSector, Integer> {

    public List<ClinicSector> getClinicSectorsByClinic(Clinic clinic) throws SQLException;


    public ClinicSector getClinicSector() throws SQLException ;

}
