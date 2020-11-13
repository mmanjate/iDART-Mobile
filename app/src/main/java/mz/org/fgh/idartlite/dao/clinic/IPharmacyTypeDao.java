package mz.org.fgh.idartlite.dao.clinic;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.PharmacyType;

public interface IPharmacyTypeDao extends IGenericDao<PharmacyType, Integer> {


    public List<PharmacyType> getAllPharmacyType() throws SQLException ;

    public PharmacyType getPharmacyTypeByCode(String code) throws SQLException ;

}
