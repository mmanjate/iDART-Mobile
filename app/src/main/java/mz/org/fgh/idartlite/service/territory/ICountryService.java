package mz.org.fgh.idartlite.service.territory;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.IBaseService;
import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.Clinic;
import mz.org.fgh.idartlite.model.Country;
import mz.org.fgh.idartlite.model.Province;

public interface ICountryService  extends IBaseService {

    public List<Country> getAllCountrys() throws SQLException;

    public void saveCountry(Country country) throws SQLException;

    public void saveOnCountry(Object country);

    public boolean checkCountry(Object country);

    public Country getCountryByRestId(int restId) throws SQLException;
}
