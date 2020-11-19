package mz.org.fgh.idartlite.service.territory;

import android.app.Application;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.Country;
import mz.org.fgh.idartlite.model.DiseaseType;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Form;
import mz.org.fgh.idartlite.model.User;

import static java.util.Objects.requireNonNull;

public class CountryService extends BaseService implements ICountryService {


    public CountryService(Application application, User currentUser) {
        super(application, currentUser);
    }

    @Override
    public List<Country> getAllCountrys() throws SQLException {
        return getDataBaseHelper().getCountryDao().getAllCountrys();
    }

    public void saveCountry(Country country) throws SQLException {
        getDataBaseHelper().getCountryDao().create(country);
    }


    public void saveOnCountry(Object country) {
        Country localCountry = new Country();

        try {
            LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) (Object) country;

            localCountry.setCode((requireNonNull(itemresult.get("code")).toString()));
            localCountry.setName((requireNonNull(itemresult.get("name")).toString()));
            localCountry.setUuid((requireNonNull(itemresult.get("uuid")).toString()));
            localCountry.setRestId((int) Float.parseFloat(requireNonNull(itemresult.get("id")).toString()));

            saveCountry(localCountry);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkCountry(Object country) {


            boolean result = false;

            LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) country;

            try {

                Country locaCountry = getCountryByRestId((int) Float.parseFloat(requireNonNull(itemresult.get("id")).toString()));

                if(locaCountry != null)
                    result = true;

            }catch (Exception e){
                e.printStackTrace();
            }

            return result;


    }

    @Override
    public Country getCountryByRestId(int restId) throws SQLException {
        return getDataBaseHelper().getCountryDao().getCountryByRestId(restId);
    }


}
