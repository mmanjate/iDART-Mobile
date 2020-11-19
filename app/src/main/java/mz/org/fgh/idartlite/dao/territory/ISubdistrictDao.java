package mz.org.fgh.idartlite.dao.territory;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.District;
import mz.org.fgh.idartlite.model.Province;
import mz.org.fgh.idartlite.model.Subdistrict;

public interface ISubdistrictDao extends IGenericDao<Subdistrict, Integer> {

    public List<Subdistrict> getSubdistrictByDistrict(District district) throws SQLException;
}
