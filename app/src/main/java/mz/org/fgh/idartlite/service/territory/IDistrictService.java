package mz.org.fgh.idartlite.service.territory;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.District;
import mz.org.fgh.idartlite.model.Province;

public interface IDistrictService  extends IBaseService {

    public List<District> getDistrictByProvince(Province province) throws SQLException;

    public void saveDistrict(District district) throws SQLException;

    public void saveOnDistrict(Object district);

    public District getDistrictByCode(String code) throws SQLException;

    public boolean checkDistrict(Object district);
}
