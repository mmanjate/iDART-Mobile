package mz.org.fgh.idartlite.dao.territory;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.Country;
import mz.org.fgh.idartlite.model.Province;

public interface IProvinceDao extends IGenericDao<Province, Integer> {


    public List<Province> getAllProvinces() throws SQLException;

    public List<Province> getProvinceByCountry(Country country) throws SQLException;

    public Province getProvinceByRestId(int restId) throws SQLException;
}
