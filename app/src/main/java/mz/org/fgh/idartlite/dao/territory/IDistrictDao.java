package mz.org.fgh.idartlite.dao.territory;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.Country;
import mz.org.fgh.idartlite.model.District;
import mz.org.fgh.idartlite.model.Province;

public interface IDistrictDao extends IGenericDao<District, Integer> {

    public List<District> getDistrictByProvince(Province province) throws SQLException;

    public District getDistrictByCode(String code) throws SQLException;
}
