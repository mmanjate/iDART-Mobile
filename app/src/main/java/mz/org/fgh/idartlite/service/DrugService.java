package mz.org.fgh.idartlite.service;

import android.app.Application;

import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import mz.org.fgh.idartlite.base.BaseService;
import mz.org.fgh.idartlite.model.Drug;
import mz.org.fgh.idartlite.model.RegimenDrug;
import mz.org.fgh.idartlite.model.TherapeuticRegimen;
import mz.org.fgh.idartlite.model.User;

public class DrugService extends BaseService {
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
        regimeDrugQb.where().eq(RegimenDrug.COLUMN_DRUG_ID, therapeuticRegimen.getId());

        QueryBuilder<Drug, Integer> drugQb = getDataBaseHelper().getDrugDao().queryBuilder();
        return drugQb.join(regimeDrugQb).query();
    }

}
