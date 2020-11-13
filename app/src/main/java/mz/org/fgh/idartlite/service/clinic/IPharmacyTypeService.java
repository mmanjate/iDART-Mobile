package mz.org.fgh.idartlite.service.clinic;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.PharmacyType;

import static mz.org.fgh.idartlite.model.PharmacyType.COLUMN_DESCRIPTION;


public interface IPharmacyTypeService extends IBaseService {

    public void savePharmacyType(PharmacyType pharmacyType) throws SQLException;

    public List<PharmacyType> getAllPharmacyType() throws SQLException ;

    public PharmacyType getPharmacyTypeByCode(String code) throws SQLException ;

    public boolean checkPharmacyType(Object pharmacyType) ;

    public void saveOnPharmacyType(Object pharmacyType);
}
