package mz.org.fgh.idartlite.service.drug;

import android.app.Application;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.DiseaseType;
import mz.org.fgh.idartlite.model.User;

import static mz.org.fgh.idartlite.model.DiseaseType.COLUMN_CODE;

public class DiseaseTypeService extends BaseService implements IDiseaseTypeService{
    public DiseaseTypeService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public void saveDiseaseType(DiseaseType diseaseType) throws SQLException {
        getDataBaseHelper().getIDiseaseTypeDao().create(diseaseType);
    }

    public List<DiseaseType> getAllDiseaseTypes() throws SQLException {
        return getDataBaseHelper().getIDiseaseTypeDao().getAllDiseaseTypes();
    }

    public DiseaseType getDiseaseTypeByCode(String code) throws SQLException {

        return getDataBaseHelper().getIDiseaseTypeDao().getDiseaseTypeByCode(code);
    }

    public boolean checkDisease(Object disease) {

        boolean result = false;

        LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) disease;

        try {

            DiseaseType localDiseaseType = getDiseaseTypeByCode((Objects.requireNonNull(itemresult.get("value")).toString()));

            if(localDiseaseType != null)
                result = true;

        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public void saveDisease(Object disease){

        DiseaseType localDiseaseType = new DiseaseType();
        try {
            LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) disease;

            localDiseaseType.setCode((Objects.requireNonNull(itemresult.get("value")).toString()));
            localDiseaseType.setDescription((Objects.requireNonNull(itemresult.get("value")).toString()));
            saveDiseaseType(localDiseaseType);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
