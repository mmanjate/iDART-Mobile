package mz.org.fgh.idartlite.service.territory;

import android.app.Application;

import com.google.gson.internal.LinkedTreeMap;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.Country;
import mz.org.fgh.idartlite.model.Form;
import mz.org.fgh.idartlite.model.Province;
import mz.org.fgh.idartlite.model.User;
import mz.org.fgh.idartlite.service.drug.FormService;

import static java.util.Objects.requireNonNull;

public class ProvinceService extends BaseService implements IProvinceService {

    protected CountryService countryService = new CountryService(getApplication(), null);
    public ProvinceService(Application application, User currentUser) {
        super(application, currentUser);
    }

    @Override
    public void saveProvince(Province province) throws SQLException {

        getDataBaseHelper().getProvinceDao().create(province);
    }

    @Override
    public void saveOnProvince(Object province) {

        Province localProvince = new Province();

        try {
            LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) (Object) province;

         //   LinkedTreeMap<String, Object> itemSubResult = (LinkedTreeMap<String, Object>) requireNonNull(itemresult.get("country"));

            Country country = countryService.getCountryByRestId((int) Float.parseFloat(requireNonNull(itemresult.get("country")).toString()));

            localProvince.setCode((requireNonNull(itemresult.get("code")).toString()));
            localProvince.setName((requireNonNull(itemresult.get("name")).toString()));
            localProvince.setUuid((requireNonNull(itemresult.get("uuid")).toString()));
            localProvince.setRestId((int) Float.parseFloat(requireNonNull(itemresult.get("id")).toString()));
            localProvince.setCountry(country);



            saveProvince(localProvince);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Province> getAllProvinces() throws SQLException {
        return getDataBaseHelper().getProvinceDao().getAllProvinces();
    }

    @Override
    public List<Province> getProvinceByCountry(Country country) throws SQLException {
        return getDataBaseHelper().getProvinceDao().getProvinceByCountry(country);
    }

    @Override
    public Province getProvinceByRestId(int restId) throws SQLException {
        return getDataBaseHelper().getProvinceDao().getProvinceByRestId(restId);
    }

    @Override
    public boolean checkProvince(Object province) {

        boolean result = false;

        LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) province;

        try {

            Province localProvince = getProvinceByRestId((int) Float.parseFloat(requireNonNull(itemresult.get("id")).toString()));

            if(localProvince != null)
                result = true;

        }catch (Exception e){
            e.printStackTrace();
        }

         return result;
    }

    @Override
    public Map<String, Province> getProvincesInMap() throws SQLException {
       List<Province> provinces= this.getAllProvinces();

        Map<String, Province> provinceMap=new HashMap<>();
       for (Province province:provinces){
           provinceMap.put(province.getDescription(),province);
       }

       return provinceMap;
    }
}
