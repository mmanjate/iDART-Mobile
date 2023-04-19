package mz.org.fgh.idartlite.service.territory;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.Country;
import mz.org.fgh.idartlite.model.Province;

public interface IProvinceService extends IBaseService {


    public void saveProvince(Province province) throws SQLException;

    public void saveOnProvince(Object province);

    public List<Province> getAllProvinces() throws SQLException;

    public List<Province> getProvinceByCountry(Country country) throws SQLException;

    public Province getProvinceByRestId(int restId) throws SQLException;

    public boolean checkProvince(Object province);

    public Map<String,Province> getProvincesInMap() throws SQLException;
}
