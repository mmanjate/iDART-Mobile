package mz.org.fgh.idartlite.service.clinic;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.ClinicSectorType;
import mz.org.fgh.idartlite.model.PharmacyType;

public interface IClinicSectorTypeService extends IBaseService {

    public List<ClinicSectorType> getAllClinicSectorType() throws SQLException;

    public void saveClinicSectorType(ClinicSectorType clinicSectorType) throws SQLException;

    public ClinicSectorType getClinicSectorTypeByCode(String code) throws SQLException ;

    public boolean checkClinicSectorType(Object clinicSectorType) ;

    public void saveOnClinicSectorType(Object clinicSectorType);

    public ClinicSectorType getClinicSectorTypeById(String id) throws SQLException;
}
