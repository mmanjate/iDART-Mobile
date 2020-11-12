package mz.org.fgh.idartlite.service.drug;

import android.app.Application;

import com.google.gson.internal.LinkedTreeMap;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.DiseaseType;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Form;
import mz.org.fgh.idartlite.model.RegimenDrug;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.model.User;

import static java.util.Objects.*;
import static mz.org.fgh.idartlite.model.Drug.COLUMN_DESCRIPTION;
import static mz.org.fgh.idartlite.model.Drug.COLUMN_FNMCODE;
import static mz.org.fgh.idartlite.model.Drug.COLUMN_REST_ID;

public class DrugService extends BaseService implements IDrugService {

    protected DiseaseTypeService diseaseTypeService = new DiseaseTypeService(getApplication(), null);
    protected FormService formService = new FormService(getApplication(), null);

    public DrugService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public void saveDrug(Drug drug) throws SQLException {
        getDataBaseHelper().getDrugDao().create(drug);
    }

    public List<Drug> getAll() throws SQLException {
        return getDataBaseHelper().getDrugDao().queryForAll();
    }

    public List<Drug> getAllOfTherapeuticRegimen(TherapeuticRegimen therapeuticRegimen) throws SQLException {

        QueryBuilder<RegimenDrug, Integer> regimeDrugQb = getDataBaseHelper().getRegimenDrugDao().queryBuilder();
        regimeDrugQb.where().eq(RegimenDrug.COLUMN_THERAPEUTIC_REGIMEN_ID, therapeuticRegimen.getId());

        QueryBuilder<Drug, Integer> drugQb = getDataBaseHelper().getDrugDao().queryBuilder();
        return drugQb.join(regimeDrugQb).query();
    }

    public Drug getDrug(String code) throws SQLException {

        List<Drug> typeList = getDataBaseHelper().getDrugDao().queryForEq(COLUMN_FNMCODE, code);

        if (typeList != null)
            if (!typeList.isEmpty())
                return typeList.get(0);

        return null;
    }

    public Drug getDrugFromDescription(String description) throws SQLException {

        List<Drug> typeList = getDataBaseHelper().getDrugDao().queryForEq(COLUMN_DESCRIPTION, description);

        if (typeList != null)
            if (!typeList.isEmpty())
                return typeList.get(0);

        return null;
    }

    public Drug getDrugByRestID(int restId) throws SQLException {

        List<Drug> typeList = getDataBaseHelper().getDrugDao().queryForEq(COLUMN_REST_ID, restId);

        if (typeList != null)
            if (!typeList.isEmpty())
                return typeList.get(0);

        return null;
    }

    public boolean checkDrug(Object drug) {

        boolean result = false;
        LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) (Object) drug;
        try {

            Drug localDrug = getDrug(requireNonNull(itemresult.get("atccode_id")).toString());

            if (localDrug != null)
                result = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void saveOnDrug(Object drug) {
        Drug localDrug = new Drug();

        try {
            LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) (Object) drug;

            LinkedTreeMap<String, Object> itemSubResult = (LinkedTreeMap<String, Object>) requireNonNull(itemresult.get("form"));

            DiseaseType diseaseType = diseaseTypeService.getdDiseaseType((requireNonNull(itemresult.get("tipodoenca")).toString()));
            Form form = formService.getForm((requireNonNull(itemSubResult.get("form")).toString()));

            localDrug.setRestId((int) Float.parseFloat(requireNonNull(itemresult.get("id")).toString()));
            localDrug.setDescription((requireNonNull(itemresult.get("name")).toString()));
            localDrug.setDiseaseType(diseaseType);
            localDrug.setFnmcode((requireNonNull(itemresult.get("atccode_id")).toString()));
            localDrug.setForm(form);
            if (itemresult.get("dispensinginstructions1") != null)
                localDrug.setInstruction((requireNonNull(itemresult.get("dispensinginstructions1")).toString()));
            else
                localDrug.setInstruction("");
            localDrug.setPackSize((int) Float.parseFloat(requireNonNull(itemresult.get("packsize")).toString()));

            saveDrug(localDrug);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Drug> getDrugsWithoutRectParanthesis(List<Drug> drugs) throws SQLException {

        List<Drug> newDrugs=new ArrayList<>();
        for (Drug drug:drugs)
        {
            drug.setDescription(drug.getDescription().replace("[","").replace("]",""));
            newDrugs.add(drug);
        }
        return newDrugs;

    }

}
