package mz.org.fgh.idartlite.service.clinic;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.PharmacyType;


public interface IPharmacyTypeService extends IBaseService<PharmacyType> {

    public void savePharmacyType(PharmacyType pharmacyType) throws SQLException;

    public List<PharmacyType> getAllPharmacyType() throws SQLException ;

    public PharmacyType getPharmacyTypeByCode(String code) throws SQLException ;

    public boolean checkPharmacyType(Object pharmacyType) ;

    public void saveOnPharmacyType(Object pharmacyType);
}
