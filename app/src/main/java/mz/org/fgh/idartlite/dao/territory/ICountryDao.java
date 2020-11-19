package mz.org.fgh.idartlite.dao.territory;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.dao.generic.IGenericDao;
import mz.org.fgh.idartlite.model.Country;
import mz.org.fgh.idartlite.model.DiseaseType;
import mz.org.fgh.idartlite.model.RegimenDrug;

public interface ICountryDao extends IGenericDao<Country, Integer> {





    public List<Country> getAllCountrys() throws SQLException;

    public Country getCountryByRestId(int restId) throws SQLException;
}
