package mz.org.fgh.idartlite.service.territory;

import android.app.Application;

import com.google.gson.internal.LinkedTreeMap;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.dao.generic.GenericDaoImpl;
import mz.org.fgh.idartlite.model.Country;
import mz.org.fgh.idartlite.model.District;
import mz.org.fgh.idartlite.model.Province;
import mz.org.fgh.idartlite.model.User;

import static java.util.Objects.requireNonNull;

public class DistrictService extends BaseService implements IDistrictService  {

    protected ProvinceService provinceService = new ProvinceService(getApplication(), null);
      public DistrictService(Application application, User currentUser) {
        super(application, currentUser);
    }

    @Override
    public List<District> getDistrictByProvince(Province province) throws SQLException {
        return getDataBaseHelper().getDistrictDao().getDistrictByProvince(province);
    }

    @Override
    public void saveDistrict(District district) throws SQLException {
          getDataBaseHelper().getDistrictDao().create(district);
    }

    @Override
    public void saveOnDistrict(Object district) {
        District localDistrict = new District();

        try {
            LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) (Object) district;

         //   LinkedTreeMap<String, Object> itemSubResult = (LinkedTreeMap<String, Object>) requireNonNull(itemresult.get("province"));

            Province province = provinceService.getProvinceByRestId((int) Float.parseFloat(requireNonNull(itemresult.get("province")).toString()));

            localDistrict.setCode((requireNonNull(itemresult.get("code")).toString()));
            localDistrict.setName((requireNonNull(itemresult.get("name")).toString()));
            localDistrict.setUuid((requireNonNull(itemresult.get("uuid")).toString()));
            localDistrict.setRestId((int) Float.parseFloat(requireNonNull(itemresult.get("id")).toString()));
            localDistrict.setProvince(province);


            saveDistrict(localDistrict);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public District getDistrictByCode(String code) throws SQLException {
        return getDataBaseHelper().getDistrictDao().getDistrictByCode(code);
    }

    @Override
    public boolean checkDistrict(Object district) {
        boolean result = false;

        LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) district;

        try {

            District localDistrict = getDistrictByCode(requireNonNull(itemresult.get("code")).toString());

            if(localDistrict != null)
                result = true;

        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
