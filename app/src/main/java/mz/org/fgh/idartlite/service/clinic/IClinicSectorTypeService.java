package mz.org.fgh.idartlite.service.clinic;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.ClinicSectorType;

public interface IClinicSectorTypeService extends IBaseService {

    public List<ClinicSectorType> getAllClinicSectorType() throws SQLException;
}
