package mz.org.fgh.idartlite.model;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import mz.org.fgh.idartlite.base.BaseModel;
import mz.org.fgh.idartlite.dao.GenericDaoImpl;
import mz.org.fgh.idartlite.dao.RegimenDrugDaoImpl;

import java.util.Objects;

@DatabaseTable(tableName = "regimen_drug", daoClass = RegimenDrugDaoImpl.class)
public class RegimenDrug extends BaseModel {

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_THERAPEUTIC_REGIMEN_ID = "therapeutic_regimen_id";
    public static final String COLUMN_DRUG_ID = "drug_id";

    @DatabaseField(columnName = COLUMN_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = COLUMN_THERAPEUTIC_REGIMEN_ID, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private TherapeuticRegimen therapeuticRegimen;

    @DatabaseField(columnName = COLUMN_DRUG_ID, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Drug drug;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TherapeuticRegimen getTherapeuticRegimen() {
        return therapeuticRegimen;
    }

    public void setTherapeuticRegimen(TherapeuticRegimen therapeuticRegimen) {
        this.therapeuticRegimen = therapeuticRegimen;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegimenDrug that = (RegimenDrug) o;
        return id == that.id &&
                therapeuticRegimen.equals(that.therapeuticRegimen) &&
                drug.equals(that.drug);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, therapeuticRegimen, drug);
    }

    @Override
    public String toString() {
        return "RegimenDrug{" +
                "therapeuticRegimen=" + therapeuticRegimen +
                ", drug=" + drug +
                '}';
    }
}
