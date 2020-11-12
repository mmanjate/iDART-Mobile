package mz.org.fgh.idartlite.service.clinic;

import android.app.Application;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.PharmacyType;
import mz.org.fgh.idartlite.model.User;

import static mz.org.fgh.idartlite.model.PharmacyType.COLUMN_DESCRIPTION;

public class PharmacyTypeService extends BaseService implements IPharmacyTypeService {
    public PharmacyTypeService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public void savePharmacyType(PharmacyType pharmacyType) throws SQLException {
        getDataBaseHelper().getPharmacyTypeDao().create(pharmacyType);
    }

    public List<PharmacyType> getAllPharmacyType() throws SQLException {
        return getDataBaseHelper().getPharmacyTypeDao().queryForAll();
    }

    public PharmacyType getPharmacyType(String code) throws SQLException {

        List<PharmacyType> typeList = getDataBaseHelper().getPharmacyTypeDao().queryForEq(COLUMN_DESCRIPTION,code);

        PharmacyType pharmacyType = null;

        if (typeList != null) {
            if (!typeList.isEmpty())
                pharmacyType = typeList.get(0);
        }

        return pharmacyType;
    }

    public boolean checkPharmacyType(Object pharmacyType) {

        boolean result = false;
        LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) pharmacyType;
        try {
            PharmacyType localPharmacyType = getPharmacyType(Objects.requireNonNull(itemresult.get("value")).toString());
            if(localPharmacyType != null)
                result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    public void saveOnPharmacyType(Object pharmacyType){

        PharmacyType localPharmacyType = new PharmacyType();
        try {
            LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) pharmacyType;

            localPharmacyType.setDescription(Objects.requireNonNull(itemresult.get("value")).toString());
            savePharmacyType(localPharmacyType);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
