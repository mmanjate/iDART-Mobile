package mz.org.fgh.idartlite.service.drug;

import android.app.Application;

import com.google.gson.internal.LinkedTreeMap;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.base.service.BaseService;
import mz.org.fgh.idartlite.model.DiseaseType;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.Form;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.model.User;

import static java.util.Objects.requireNonNull;

public class DrugService extends BaseService<Drug> implements IDrugService {

    protected DiseaseTypeService diseaseTypeService = new DiseaseTypeService(getApplication(), null);
    protected FormService formService = new FormService(getApplication(), null);

    public DrugService(Application application, User currentUser) {
        super(application, currentUser);
    }

    public DrugService(Application application) {
        super(application);
    }

    @Override
    public void save(Drug record) throws SQLException {

    }

    @Override
    public void update(Drug relatedRecord) throws SQLException {

    }

    public void saveDrug(Drug drug) throws SQLException {
        getDataBaseHelper().getDrugDao().create(drug);
    }

    public List<Drug> getAll() throws SQLException {
        return getDataBaseHelper().getDrugDao().queryForAll();
    }

    public List<Drug> getAllByTherapeuticRegimen(TherapeuticRegimen therapeuticRegimen) throws SQLException {

        return  getDataBaseHelper().getDrugDao().getAllByTherapeuticRegimen(getApplication(),therapeuticRegimen);
    }

    public Drug getDrugByFNMCode(String code) throws SQLException {

        Drug typeList = getDataBaseHelper().getDrugDao().getDrugByFNMCode(code);

        if (typeList != null) return typeList;

        return null;
    }

    public Drug getDrugByDescription(String description) throws SQLException {

        Drug typeList = getDataBaseHelper().getDrugDao().getDrugByDescription(description);

        if (typeList != null) return typeList;

        return null;
    }

    public Drug getDrugByRestID(int restId) throws SQLException {

        Drug typeList = getDataBaseHelper().getDrugDao().getDrugByRestID(restId);

        if (typeList != null) return typeList;

        return null;
    }

    public boolean checkDrug(Object drug) {

        boolean result = false;
        LinkedTreeMap<String, Object> itemresult = (LinkedTreeMap<String, Object>) (Object) drug;
        try {

            Drug localDrug = getDrugByFNMCode(requireNonNull(itemresult.get("atccode_id")).toString());

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

            DiseaseType diseaseType = diseaseTypeService.getDiseaseTypeByCode((requireNonNull(itemresult.get("tipodoenca")).toString()));
            Form form = formService.getFormByDescription((requireNonNull(itemSubResult.get("form")).toString()));

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

    public List<Drug> getAllWithLote() throws SQLException{
        return getDataBaseHelper().getDrugDao().getAllWithLote(getApplication());
    }
}
