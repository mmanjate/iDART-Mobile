package mz.org.fgh.idartlite.service.clinic;

import static mz.org.fgh.idartlite.model.PharmacyType.COLUMN_DESCRIPTION;

import android.app.Application;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.ClinicSectorType;
import mz.org.fgh.idartlite.model.PharmacyType;
import mz.org.fgh.idartlite.model.User;

public class ClinicSectorTypeService extends BaseService implements IClinicSectorTypeService {


    public ClinicSectorTypeService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public ClinicSectorTypeService(Application application) {
        super(application);
    }

    @Override
    public List<ClinicSectorType> getAllClinicSectorType() throws SQLException {
        return getDataBaseHelper().getClinicSectorTypeDao().getAllClinicSectorType();
    }

    @Override
    public void saveClinicSectorType(ClinicSectorType clinicSectorType) throws SQLException {
        getDataBaseHelper().getClinicSectorTypeDao().create(clinicSectorType);
    }

    @Override
    public ClinicSectorType getClinicSectorTypeByCode(String code) throws SQLException {
        List<ClinicSectorType> clinicSectorTypeList = getDataBaseHelper().getClinicSectorTypeDao().queryForEq(COLUMN_DESCRIPTION,code);

        ClinicSectorType clinicSectorType = null;

        if (clinicSectorTypeList != null) {
            if (!clinicSectorTypeList.isEmpty())
                clinicSectorType = clinicSectorTypeList.get(0);
        }

        return clinicSectorType;
    }

    @Override
    public boolean checkClinicSectorType(Object clinicSectorType) {
        boolean result = false;
        LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) clinicSectorType;

        try {
            ClinicSectorType localClinicSectorType = getClinicSectorTypeByCode(Objects.requireNonNull(itemresult.get("code")).toString());
            if(localClinicSectorType != null)
                result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void saveOnClinicSectorType(Object clinicSectorType) {
        ClinicSectorType localClinicSectorType = new ClinicSectorType();
        try {
            LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) clinicSectorType;

            localClinicSectorType.setDescription(Objects.requireNonNull(itemresult.get("code")).toString());
            saveClinicSectorType(localClinicSectorType);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ClinicSectorType getClinicSectorTypeById(String id) throws SQLException {
        ClinicSectorType clinicSectorType  = (ClinicSectorType) getDataBaseHelper().getClinicSectorTypeDao().queryForEq(COLUMN_DESCRIPTION,id);
        return clinicSectorType;
    }
}
