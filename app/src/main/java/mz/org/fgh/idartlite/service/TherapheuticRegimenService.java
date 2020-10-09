package mz.org.fgh.idartlite.service;

import android.app.Application;

import com.google.gson.internal.LinkedTreeMap;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static mz.org.fgh.idartlite.model.TherapeuticRegimen.COLUMN_DESCRIPTION;
import static mz.org.fgh.idartlite.model.TherapeuticRegimen.COLUMN_REGIMEN_CODE;

public class TherapheuticRegimenService extends BaseService {

    private RegimenDrugsService regimenDrugsService = new RegimenDrugsService(getApplication(),null);

    public TherapheuticRegimenService(Application application, User currUser) {
        super(application, currUser);
    }

    public void createTherapheuticRegimen(TherapeuticRegimen therapeuticRegimen) throws SQLException {
        getDataBaseHelper().getTherapeuticRegimenDao().create(therapeuticRegimen);
    }

    public List<TherapeuticRegimen> getAll() throws SQLException {
        return getDataBaseHelper().getTherapeuticRegimenDao().queryForAll();
    }

    public TherapeuticRegimen getTherapeuticRegimenFromDescription(String code) throws SQLException {

        List<TherapeuticRegimen> typeList = getDataBaseHelper().getTherapeuticRegimenDao().queryForEq(COLUMN_DESCRIPTION,code);

        if (typeList != null)
            if (!typeList.isEmpty())
                return typeList.get(0);

        return null;
    }

    public TherapeuticRegimen getTherapeuticRegimenFromCode(String code) throws SQLException {

        List<TherapeuticRegimen> typeList = getDataBaseHelper().getTherapeuticRegimenDao().queryForEq(COLUMN_REGIMEN_CODE,code);

        if (typeList != null)
            if (!typeList.isEmpty())
                return typeList.get(0);

        return null;
    }

    public boolean checkRegimen(Object regimen) {

        boolean result = false;

        LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) regimen;

        try {

            TherapeuticRegimen localRegimen = getTherapeuticRegimenFromCode(Objects.requireNonNull(itemresult.get("codigoregime")).toString());

            if(localRegimen != null)
                result = true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public void saveRegimen(Object regimen){

        TherapeuticRegimen localRegimen = new TherapeuticRegimen();
        try {
            LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) regimen;

            localRegimen.setRestId((int) Float.parseFloat(Objects.requireNonNull(itemresult.get("regimeid")).toString()));
            localRegimen.setRegimenCode(Objects.requireNonNull(itemresult.get("codigoregime")).toString());
            localRegimen.setDescription(Objects.requireNonNull(itemresult.get("regimeesquema")).toString());
            createTherapheuticRegimen(localRegimen);

            regimenDrugsService.saveRegimenDrug(localRegimen, (ArrayList) Objects.requireNonNull(itemresult.get("drug")));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
