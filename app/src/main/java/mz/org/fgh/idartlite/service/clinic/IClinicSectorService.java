package mz.org.fgh.idartlite.service.clinic;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.ClinicSector;
import mz.org.fgh.idartlite.model.ClinicSectorType;


public interface IClinicSectorService extends IBaseService {

    public List<ClinicSector> getClinicSectorsByClinic(Clinic clinic) throws SQLException;


    public List<ClinicSector> getClinicSectorsByType(ClinicSectorType clinicSectorType) throws SQLException;


    public void saveClinicSector(ClinicSector clinicSector) throws SQLException;

   // public Clinic getClinicByUuid(String uuid) throws SQLException;

    public ClinicSector getClinicSector() throws SQLException ;
}
